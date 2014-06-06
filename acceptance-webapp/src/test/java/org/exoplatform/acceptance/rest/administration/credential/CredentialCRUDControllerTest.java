/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.rest.administration.credential;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.model.credential.KeyPairCredential;
import org.exoplatform.acceptance.model.credential.TokenCredential;
import org.exoplatform.acceptance.model.credential.UsernamePasswordCredential;
import org.exoplatform.acceptance.rest.JsonErrorHandler;
import org.exoplatform.acceptance.service.EntityNotFoundException;
import org.exoplatform.acceptance.service.credential.CredentialService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.junit.After;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CredentialCRUDControllerTest {

  private final UsernamePasswordCredential a_username_password = new UsernamePasswordCredential("A username/password",
                                                                                                "a_username",
                                                                                                "a_password");
  private final TokenCredential a_token = new TokenCredential("A token", "a_token");
  private final KeyPairCredential a_key_pair = new KeyPairCredential("A key pair", "a private key", "a public key");
  private MockMvc mockMvc;
  @InjectMocks
  private CredentialCRUDController controllerUnderTest;
  @Mock
  private CredentialService credentialServiceMock;
  @Inject
  private JsonErrorHandler jsonErrorHandler;

  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }

  @Before
  public void setUp() {
    Credential.NONE.setId("0");
    a_username_password.setId("1");
    a_token.setId("2");
    a_key_pair.setId("3");


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
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    messageConverters.add(new FormHttpMessageConverter());
    messageConverters.add(new StringHttpMessageConverter());
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    exceptionResolver.setMessageConverters(messageConverters);
    exceptionResolver.afterPropertiesSet();
    return exceptionResolver;
  }

  @After
  public void teardown() {
    SecurityContextHolder.clearContext();
  }

  @Test
  public void findAll_CredentialsFound_ShouldReturnFoundCredentialEntriesWithDataFiltered() throws Exception {

    when(credentialServiceMock.findAll()).thenReturn(Arrays.asList(a_username_password, a_token, a_key_pair, Credential.NONE));
    mockMvc.perform(get("/admin/credential"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].id", is(a_username_password.getId())))
        .andExpect(jsonPath("$[0].name", is(a_username_password.getName())))
        .andExpect(jsonPath("$[0].type", is(a_username_password.getType().toString())))
            // username shouldn't be retrieved with findAll
        .andExpect(jsonPath("$[0].username").doesNotExist())
            // password shouldn't be retrieved with findAll
        .andExpect(jsonPath("$[0].password").doesNotExist())
        .andExpect(jsonPath("$[1].id", is(a_token.getId())))
        .andExpect(jsonPath("$[1].name", is(a_token.getName())))
            // token shouldn't be retrieved with findAll
        .andExpect(jsonPath("$[1].token").doesNotExist())
        .andExpect(jsonPath("$[1].type", is(a_token.getType().toString())))
        .andExpect(jsonPath("$[2].id", is(a_key_pair.getId())))
        .andExpect(jsonPath("$[2].name", is(a_key_pair.getName())))
            // privateKey shouldn't be retrieved with findAll
        .andExpect(jsonPath("$[2].privateKey").doesNotExist())
            // publicKey shouldn't be retrieved with findAll
        .andExpect(jsonPath("$[2].publicKey").doesNotExist())
        .andExpect(jsonPath("$[3].id", is(Credential.NONE.getId())))
        .andExpect(jsonPath("$[3].name", is(Credential.NONE.getName())));
    verify(credentialServiceMock, times(1)).findAll();
    verifyNoMoreInteractions(credentialServiceMock);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findById_CredentialEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    when(credentialServiceMock.findOne("1")).thenThrow(EntityNotFoundException.class);

    mockMvc.perform(get("/admin/credential/{id}", "1"))
        .andExpect(status().isNotFound());

    verify(credentialServiceMock, times(1)).findOne("1");
    verifyNoMoreInteractions(credentialServiceMock);
  }

  @Test
  public void findById_CredentialEntryFound_ShouldReturnFoundCredentialEntry() throws Exception {
    when(credentialServiceMock.findOne("1")).thenReturn(a_username_password);

    mockMvc.perform(get("/admin/credential/{id}", "1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.type", is(a_username_password.getType().toString())))
        .andExpect(jsonPath("$.name", is(a_username_password.getName())))
        .andExpect(jsonPath("$.username", is(a_username_password.getUsername())))
        .andExpect(jsonPath("$.password", is(a_username_password.getPassword())))
        .andExpect(jsonPath("$.id", is("1")));
    verify(credentialServiceMock, times(1)).findOne("1");
    verifyNoMoreInteractions(credentialServiceMock);
    a_username_password.setId(null);
  }

  @Test
  public void add_NameIsTooLong_ShouldReturnValidationErrorForName() throws Exception {
    TokenCredential invalidObject = new TokenCredential(Strings.padEnd("a", 65, 'a'), Strings.padEnd("b", 10, 'b'));

    mockMvc.perform(post("/admin/credential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(invalidObject)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
        .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name")))
        .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("size must be between 3 and 64")));
    verifyZeroInteractions(credentialServiceMock);
  }

  @Test
  public void add_NameAlreadyExists_ShouldReturnValidationError() throws Exception {
    TokenCredential objectToCreate = new TokenCredential(Strings.padEnd("a", 10, 'a'), Strings.padEnd("b", 10, 'b'));
    String exMsg = "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"err\" : \"E11000 duplicate key error index: acceptance" +
        ".credentials.$name  dup key: { : \\\"" + objectToCreate.getName() + "\\\" }\" , \"code\" : 11000 , \"n\" : 0 , \"connectionId\" : 5 , \"ok\" : 1.0}";
    String errorMsg = "E11000 duplicate key error index: acceptance.credentials.$name  dup key: { : \"" + objectToCreate.getName() + "\" }";
    when(credentialServiceMock.updateOrCreate(any(Credential.class))).thenThrow(
        new DuplicateKeyException("", new Exception(exMsg)));
    mockMvc.perform(post("/admin/credential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(objectToCreate)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.globalErrors", hasSize(1)))
        .andExpect(jsonPath("$.globalErrors[*].objectName", containsInAnyOrder("Duplicated Key Error")))
        .andExpect(jsonPath("$.globalErrors[*].message", containsInAnyOrder(errorMsg)));
    ArgumentCaptor<Credential> dtoCaptor = ArgumentCaptor.forClass(Credential.class);
    verify(credentialServiceMock, times(1)).updateOrCreate(dtoCaptor.capture());
    verifyNoMoreInteractions(credentialServiceMock);

    Credential dtoArgument = dtoCaptor.getValue();
    assertNull(dtoArgument.getId());
    assertThat(dtoArgument.getName(), is(objectToCreate.getName()));
    assertThat(dtoArgument.getType(), is(objectToCreate.getType()));
  }

  @Test
  public void add_NewCredentialEntry_ShouldAddCredentialEntryAndReturnAddedEntry() throws Exception {
    TokenCredential objectToCreate = new TokenCredential(Strings.padEnd("a", 10, 'a'), Strings.padEnd("b", 10, 'b'));
    TokenCredential objectCreated = new TokenCredential(Strings.padEnd("a", 10, 'a'), Strings.padEnd("b", 10, 'b'));
    objectCreated.setId("1");

    when(credentialServiceMock.updateOrCreate(any(Credential.class))).thenReturn(objectCreated);

    mockMvc.perform(post("/admin/credential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(objectToCreate))
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(objectCreated.getId())))
        .andExpect(jsonPath("$.name", is(objectCreated.getName())))
        .andExpect(jsonPath("$.type", is(objectCreated.getType().toString())))
        .andExpect(jsonPath("$.token", is(objectCreated.getToken())));

    ArgumentCaptor<Credential> dtoCaptor = ArgumentCaptor.forClass(Credential.class);
    verify(credentialServiceMock, times(1)).updateOrCreate(dtoCaptor.capture());
    verifyNoMoreInteractions(credentialServiceMock);

    Credential dtoArgument = dtoCaptor.getValue();
    assertNull(dtoArgument.getId());
    assertThat(dtoArgument.getName(), is(objectToCreate.getName()));
    assertThat(dtoArgument.getType(), is(objectToCreate.getType()));
  }

  @Test
  public void delete_CredentialIsNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    doThrow(new EntityNotFoundException("foo")).when(credentialServiceMock).delete("foo");

    mockMvc.perform(delete("/admin/credential/{id}", "foo"))
        .andExpect(status().isNotFound());

    verify(credentialServiceMock, times(1)).delete("foo");
    verifyNoMoreInteractions(credentialServiceMock);
  }

  @Test
  public void update_EmptyCredentialEntry_ShouldReturnValidationErrorForName() throws Exception {
    TokenCredential invalidTokenCredential = new TokenCredential("", null);
    invalidTokenCredential.setId("5");

    mockMvc.perform(put("/admin/credential/{id}", "5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(invalidTokenCredential))
    )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
        .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "token")))
        .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("may not be null", "size must be between 3 and 64")));

    verifyZeroInteractions(credentialServiceMock);
  }

  @Test
  public void update_NameIsTooLong_ShouldReturnValidationErrorsForName() throws Exception {
    TokenCredential invalidObject = new TokenCredential(Strings.padEnd("a", 65, 'a'), Strings.padEnd("b", 10, 'b'));
    invalidObject.setId("foo");

    mockMvc.perform(put("/admin/credential/{id}", "foo")
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

    verifyZeroInteractions(credentialServiceMock);
  }

  @Test
  public void update_CredentialEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
    TokenCredential absentObject = new TokenCredential(Strings.padEnd("a", 10, 'a'), Strings.padEnd("b", 10, 'b'));
    absentObject.setId("unavailable");

    when(credentialServiceMock.update(any(Credential.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc.perform(put("/admin/credential/{id}", "unavailable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(absentObject))
    )
        .andExpect(status().isNotFound());

    ArgumentCaptor<TokenCredential> dtoCaptor = ArgumentCaptor.forClass(TokenCredential.class);
    verify(credentialServiceMock, times(1)).update(dtoCaptor.capture());
    verifyNoMoreInteractions(credentialServiceMock);

    TokenCredential dtoArgument = dtoCaptor.getValue();
    assertThat(dtoArgument.getId(), is(absentObject.getId()));
    assertThat(dtoArgument.getType(), is(absentObject.getType()));
    assertThat(dtoArgument.getName(), is(absentObject.getName()));
    assertThat(dtoArgument.getToken(), is(absentObject.getToken()));
  }

  @Test
  public void update_CredentialEntryFound_ShouldUpdateCredentialEntryAndReturnIt() throws Exception {
    TokenCredential credentialSent = new TokenCredential(Strings.padEnd("a", 10, 'a'), Strings.padEnd("b", 10, 'b'));
    credentialSent.setId("foo");

    TokenCredential credentialReceived = new TokenCredential(Strings.padEnd("c", 10, 'c'), Strings.padEnd("d", 10, 'd'));
    credentialReceived.setId("foo");

    when(credentialServiceMock.update(any(Credential.class))).thenReturn(credentialReceived);

    mockMvc.perform(put("/admin/credential/{id}", credentialSent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(credentialSent))
    )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(credentialReceived.getId())))
        .andExpect(jsonPath("$.name", is(credentialReceived.getName())))
        .andExpect(jsonPath("$.type", is(credentialReceived.getType().toString())))
        .andExpect(jsonPath("$.token", is(credentialReceived.getToken())));

    ArgumentCaptor<TokenCredential> dtoCaptor = ArgumentCaptor.forClass(TokenCredential.class);
    verify(credentialServiceMock, times(1)).update(dtoCaptor.capture());
    verifyNoMoreInteractions(credentialServiceMock);

    TokenCredential dtoArgument = dtoCaptor.getValue();
    assertThat(dtoArgument.getId(), is(credentialSent.getId()));
    assertThat(dtoArgument.getType(), is(credentialSent.getType()));
    assertThat(dtoArgument.getName(), is(credentialSent.getName()));
    assertThat(dtoArgument.getToken(), is(credentialSent.getToken()));
  }

  @Test
  public void delete_CredentialEntryFound_ShouldDeleteCredentialEntryAndReturnHttpStatusCode204() throws Exception {

    doNothing().when(credentialServiceMock).delete("1");
    mockMvc.perform(delete("/admin/credential/{id}", "1"))
        .andExpect(status().isNoContent());

    verify(credentialServiceMock, times(1)).delete("1");
    verifyNoMoreInteractions(credentialServiceMock);
  }

}
