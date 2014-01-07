/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
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
package org.exoplatform.acceptance.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * Errors details returned by REST Services when something goes wrong
 */
public class JsonErrorResponse {
  private List<FieldError> fieldErrors = new ArrayList<>();
  private List<GlobalError> globalErrors = new ArrayList<>();

  public JsonErrorResponse() {
  }

  public JsonErrorResponse(@NotNull Throwable t) {
    addGlobalError(t, t.getMessage());
  }

  public JsonErrorResponse(@NotNull String objectName, @NotNull String message) {
    addGlobalError(objectName, message);
  }

  public List<FieldError> getFieldErrors() {
    return fieldErrors;
  }

  public List<GlobalError> getGlobalErrors() {
    return globalErrors;
  }

  public void addFieldError(@NotNull String field, @NotNull String message) {
    fieldErrors.add(new FieldError(field, message));
  }

  public void addGlobalError(@NotNull String objectName, @NotNull String message) {
    globalErrors.add(new GlobalError(objectName, message));
  }

  public void addGlobalError(@NotNull Object origin, @NotNull String message) {
    globalErrors.add(new GlobalError(origin.getClass().getName(), message));
  }

  public static class FieldError {

    private String field;
    private String message;

    public FieldError(String field, String message) {
      this.field = field;
      this.message = message;
    }

    public String getField() {
      return field;
    }

    public String getMessage() {
      return message;
    }

  }

  public static class GlobalError {

    private String objectName;
    private String message;

    public GlobalError(String objectName, String message) {
      this.objectName = objectName;
      this.message = message;
    }

    public String getObjectName() {
      return objectName;
    }

    public String getMessage() {
      return message;
    }

  }
}
