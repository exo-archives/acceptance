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
package org.exoplatform.acceptance.rest.administration.credential;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.rest.administration.CRUDController;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.CredentialService;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to manage REST services for credentials
 */
@Controller
@RequestMapping(value = "admin/credential", produces = MediaType.APPLICATION_JSON_VALUE)
public class CredentialCRUDController extends CRUDController<Credential, String> {

  @Inject
  private CredentialService credentialService;

  static private Credential createListItemDTO(Credential credential) {
    CredentialListItemDTO dto = new CredentialListItemDTO(credential.getType(), credential.getName());
    dto.setId(credential.getId());
    return dto;
  }

  static private Iterable<Credential> createListItemDTOs(Iterable<Credential> credentials) {
    List<Credential> dtos = new ArrayList<>();
    for (Credential credential : credentials) {
      dtos.add(createListItemDTO(credential));
    }
    return dtos;
  }

  @Override
  protected CRUDService<Credential> getCRUDService() {
    return credentialService;
  }

  void setCredentialService(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  /**
   * Get a (potentially paginated) list of objects
   *
   * @param offset the page number to get (0 by default is the first page)
   * @param limit  the maximum number of entries in the page of results ( > 0 to activate pagination, -1 thus everything by default )
   * @return a list of objects
   */
  @Override
  public Iterable<Credential> getObjects(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(
      value = "limit", defaultValue = "-1") int limit) {
    // We filter list result to not expose a list of all our credentials
    return createListItemDTOs(super.getObjects(offset, limit));
  }

  public static class CredentialListItemDTO extends Credential {

    private CredentialListItemDTO(@NotNull Type type, @NotNull String name) {
      super(type, name);
    }

  }
}
