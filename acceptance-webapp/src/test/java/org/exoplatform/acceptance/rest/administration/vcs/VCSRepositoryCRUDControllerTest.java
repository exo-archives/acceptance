/*
 * Copyright (C) 2011-2013 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.acceptance.rest.administration.vcs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.rest.JsonErrorHandler;
import org.exoplatform.acceptance.service.EntityNotFoundException;
import org.exoplatform.acceptance.service.vcs.VCSRepositoryService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:/org/exoplatform/acceptance/rest/spring-test.xml"})
@ActiveProfiles("test")
public class VCSRepositoryCRUDControllerTest {

  private MockMvc mockMvc;
  @InjectMocks
  private VCSRepositoryCRUDController controllerUnderTest;
  @Mock
  private VCSRepositoryService vcsRepositoryServiceMock;
  @Inject
  private JsonErrorHandler jsonErrorHandler;

  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }

  @Before
  public void setUp() {
    // this must be called for the @Mock annotations above to be processed
    // and for the mock service to be injected into the controller under
    // test.
    MockitoAnnotations.initMocks(this);

    // We initialize the MVC Mock for our controller
    mockMvc = standaloneSetup(controllerUnderTest).setHandlerExceptionResolvers(createExceptionResolver()).build();
  }

  private ExceptionHandlerExceptionResolver createExceptionResolver() {
    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
      protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        Method method = new ExceptionHandlerMethodResolver(JsonErrorHandler.class).resolveMethod(exception);
        return new ServletInvocableHandlerMethod(jsonErrorHandler, method);
      }
    };
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    messageConverters.add(new FormHttpMessageConverter());
    messageConverters.add(new StringHttpMessageConverter());
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    exceptionResolver.setMessageConverters(messageConverters);
    exceptionResolver.afterPropertiesSet();
    return exceptionResolver;
  }

  @Test
  public void findAll_VCSRepositoriesFound_ShouldReturnFoundVCSRepositoryEntriesWithDataFiltered() throws Exception {
    VCSRepository first_repository = new VCSRepository("first_repository", "1");
    VCSRepository second_repository = new VCSRepository("second_repository", "2");

    when(vcsRepositoryServiceMock.findAll()).thenReturn(
        Arrays.asList(first_repository, second_repository));
    mockMvc.perform(get("/admin/vcs/repository"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(first_repository.getId())))
        .andExpect(jsonPath("$[0].name", is(first_repository.getName())))
        .andExpect(jsonPath("$[0].type", is(first_repository.getType().toString())))
        .andExpect(jsonPath("$[1].id", is(second_repository.getId())))
        .andExpect(jsonPath("$[1].name", is(second_repository.getName())))
        .andExpect(jsonPath("$[1].type", is(second_repository.getType().toString())));
    verify(vcsRepositoryServiceMock, times(1)).findAll();
    verifyNoMoreInteractions(vcsRepositoryServiceMock);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findById_VCSRepositoryEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    when(vcsRepositoryServiceMock.findOne("1")).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/admin/vcs/repository/{id}", "1"))
        .andExpect(status().isNotFound());

    verify(vcsRepositoryServiceMock, times(1)).findOne("1");
    verifyNoMoreInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void findById_VCSRepositoryEntryFound_ShouldReturnFoundVCSRepositoryEntry() throws Exception {
    VCSRepository first_repository = new VCSRepository("first_repository", "1");

    when(vcsRepositoryServiceMock.findOne(first_repository.getId())).thenReturn(first_repository);

    mockMvc.perform(get("/admin/vcs/repository/{id}", first_repository.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.type", is(first_repository.getType().toString())))
        .andExpect(jsonPath("$.name", is(first_repository.getName())))
        .andExpect(jsonPath("$.id", is(first_repository.getId())));
    verify(vcsRepositoryServiceMock, times(1)).findOne(first_repository.getId());
    verifyNoMoreInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void add_NameIsTooLong_ShouldReturnValidationErrorForName() throws Exception {
    VCSRepository invalidObject = new VCSRepository(Strings.padEnd("a", 65, 'a'));

    mockMvc.perform(post("/admin/vcs/repository")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(invalidObject)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
        .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name")))
        .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("size must be between 3 and 64")));
    verifyZeroInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void add_NameAlreadyExists_ShouldReturnValidationError() throws Exception {
    VCSRepository objectToCreate = new VCSRepository(Strings.padEnd("a", 10, 'a'));
    String exMsg = "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"err\" : \"E11000 duplicate key error index: acceptance" +
        ".credentials.$name  dup key: { : \\\"" + objectToCreate.getName() + "\\\" }\" , \"code\" : 11000 , \"n\" : 0 , \"connectionId\" : 5 , \"ok\" : 1.0}";
    String errorMsg = "E11000 duplicate key error index: acceptance.credentials.$name  dup key: { : \"" + objectToCreate.getName() + "\" }";
    when(vcsRepositoryServiceMock.updateOrCreate(any(VCSRepository.class))).thenThrow(
        new DuplicateKeyException("", new Exception(exMsg)));
    mockMvc.perform(post("/admin/vcs/repository")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(objectToCreate)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.globalErrors", hasSize(1)))
        .andExpect(jsonPath("$.globalErrors[*].objectName", containsInAnyOrder("Duplicated Key Error")))
        .andExpect(jsonPath("$.globalErrors[*].message", containsInAnyOrder(errorMsg)));
    ArgumentCaptor<VCSRepository> dtoCaptor = ArgumentCaptor.forClass(VCSRepository.class);
    verify(vcsRepositoryServiceMock, times(1)).updateOrCreate(dtoCaptor.capture());
    verifyNoMoreInteractions(vcsRepositoryServiceMock);

    VCSRepository dtoArgument = dtoCaptor.getValue();
    assertNull(dtoArgument.getId());
    assertThat(dtoArgument.getName(), is(objectToCreate.getName()));
    assertThat(dtoArgument.getType(), is(objectToCreate.getType()));
  }

  @Test
  public void add_NewVCSRepositoryEntry_ShouldAddVCSRepositoryEntryAndReturnAddedEntry() throws Exception {
    VCSRepository objectToCreate = new VCSRepository(Strings.padEnd("a", 10, 'a'));
    VCSRepository objectCreated = new VCSRepository(Strings.padEnd("a", 10, 'a'), "1");

    when(vcsRepositoryServiceMock.updateOrCreate(any(VCSRepository.class))).thenReturn(objectCreated);

    mockMvc.perform(post("/admin/vcs/repository")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(objectToCreate))
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(objectCreated.getId())))
        .andExpect(jsonPath("$.name", is(objectCreated.getName())))
        .andExpect(jsonPath("$.type", is(objectCreated.getType().toString())));

    ArgumentCaptor<VCSRepository> dtoCaptor = ArgumentCaptor.forClass(VCSRepository.class);
    verify(vcsRepositoryServiceMock, times(1)).updateOrCreate(dtoCaptor.capture());
    verifyNoMoreInteractions(vcsRepositoryServiceMock);

    VCSRepository dtoArgument = dtoCaptor.getValue();
    assertNull(dtoArgument.getId());
    assertThat(dtoArgument.getName(), is(objectToCreate.getName()));
    assertThat(dtoArgument.getType(), is(objectToCreate.getType()));
  }

  @Test
  public void delete_VCSRepositoryIsNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    doThrow(new EntityNotFoundException("foo")).when(vcsRepositoryServiceMock).delete("foo");

    mockMvc.perform(delete("/admin/vcs/repository/{id}", "foo"))
        .andExpect(status().isNotFound());

    verify(vcsRepositoryServiceMock, times(1)).delete("foo");
    verifyNoMoreInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void update_EmptyVCSRepositoryEntry_ShouldReturnValidationErrorForName() throws Exception {
    VCSRepository invalidVCSRepository = new VCSRepository("", "1");

    mockMvc.perform(put("/admin/vcs/repository/{id}", invalidVCSRepository.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(invalidVCSRepository))
    )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
        .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name")))
        .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("size must be between 3 and 64")));

    verifyZeroInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void update_NameIsTooLong_ShouldReturnValidationErrorsForName() throws Exception {
    VCSRepository invalidObject = new VCSRepository(Strings.padEnd("a", 65, 'a'), "foo");

    mockMvc.perform(put("/admin/vcs/repository/{id}", invalidObject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(invalidObject))
    )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
        .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name")))
        .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
            "size must be between 3 and 64"
        )));

    verifyZeroInteractions(vcsRepositoryServiceMock);
  }

  @Test
  public void update_VCSRepositoryEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    VCSRepository absentObject = new VCSRepository(Strings.padEnd("a", 10, 'a'), "unavailable");

    when(vcsRepositoryServiceMock.update(any(VCSRepository.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc.perform(put("/admin/vcs/repository/{id}", absentObject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(absentObject))
    )
        .andExpect(status().isNotFound());

    ArgumentCaptor<VCSRepository> dtoCaptor = ArgumentCaptor.forClass(VCSRepository.class);
    verify(vcsRepositoryServiceMock, times(1)).update(dtoCaptor.capture());
    verifyNoMoreInteractions(vcsRepositoryServiceMock);

    VCSRepository dtoArgument = dtoCaptor.getValue();
    assertThat(dtoArgument.getId(), is(absentObject.getId()));
    assertThat(dtoArgument.getType(), is(absentObject.getType()));
    assertThat(dtoArgument.getName(), is(absentObject.getName()));
  }

  @Test
  public void update_VCSRepositoryEntryFound_ShouldUpdateVCSRepositoryEntryAndReturnIt() throws Exception {
    VCSRepository objectSent = new VCSRepository(Strings.padEnd("a", 10, 'a'), "foo");
    VCSRepository objectReceived = new VCSRepository(Strings.padEnd("c", 10, 'c'), "foo");

    when(vcsRepositoryServiceMock.update(any(VCSRepository.class))).thenReturn(objectReceived);

    mockMvc.perform(put("/admin/vcs/repository/{id}", objectSent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(objectSent))
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(objectReceived.getId())))
        .andExpect(jsonPath("$.name", is(objectReceived.getName())))
        .andExpect(jsonPath("$.type", is(objectReceived.getType().toString())));

    ArgumentCaptor<VCSRepository> dtoCaptor = ArgumentCaptor.forClass(VCSRepository.class);
    verify(vcsRepositoryServiceMock, times(1)).update(dtoCaptor.capture());
    verifyNoMoreInteractions(vcsRepositoryServiceMock);

    VCSRepository dtoArgument = dtoCaptor.getValue();
    assertThat(dtoArgument.getId(), is(objectSent.getId()));
    assertThat(dtoArgument.getType(), is(objectSent.getType()));
    assertThat(dtoArgument.getName(), is(objectSent.getName()));
  }

  @Test
  public void delete_VCSRepositoryEntryFound_ShouldDeleteVCSRepositoryEntryAndReturnHttpStatusCode204() throws Exception {

    doNothing().when(vcsRepositoryServiceMock).delete("1");
    mockMvc.perform(delete("/admin/vcs/repository/{id}", "1"))
        .andExpect(status().isNoContent());

    verify(vcsRepositoryServiceMock, times(1)).delete("1");
    verifyNoMoreInteractions(vcsRepositoryServiceMock);
  }

}
