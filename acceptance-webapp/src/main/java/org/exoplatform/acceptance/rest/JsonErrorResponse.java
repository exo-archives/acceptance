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
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class JsonErrorResponse {
  private final List<FieldError> fieldErrors = new ArrayList<>();
  private final List<GlobalError> globalErrors = new ArrayList<>();

  /**
   * <p>Constructor for JsonErrorResponse.</p>
   */
  public JsonErrorResponse() {
  }

  /**
   * <p>Constructor for JsonErrorResponse.</p>
   *
   * @param t a {@link java.lang.Throwable} object.
   */
  public JsonErrorResponse(@NotNull Throwable t) {
    globalErrors.add(new GlobalError(t.getClass().getName(), t.getMessage()));
  }

  /**
   * <p>Constructor for JsonErrorResponse.</p>
   *
   * @param objectName a {@link java.lang.String} object.
   * @param message    a {@link java.lang.String} object.
   */
  public JsonErrorResponse(@NotNull String objectName, @NotNull String message) {
    globalErrors.add(new GlobalError(objectName, message));
  }

  /**
   * <p>Getter for the field <code>fieldErrors</code>.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<FieldError> getFieldErrors() {
    return fieldErrors;
  }

  /**
   * <p>Getter for the field <code>globalErrors</code>.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<GlobalError> getGlobalErrors() {
    return globalErrors;
  }

  /**
   * <p>addFieldError.</p>
   *
   * @param field   a {@link java.lang.String} object.
   * @param message a {@link java.lang.String} object.
   */
  public void addFieldError(@NotNull String field, @NotNull String message) {
    fieldErrors.add(new FieldError(field, message));
  }

  /**
   * <p>addGlobalError.</p>
   *
   * @param objectName a {@link java.lang.String} object.
   * @param message    a {@link java.lang.String} object.
   */
  public void addGlobalError(@NotNull String objectName, @NotNull String message) {
    globalErrors.add(new GlobalError(objectName, message));
  }

  /**
   * <p>addGlobalError.</p>
   *
   * @param origin  a {@link java.lang.Object} object.
   * @param message a {@link java.lang.String} object.
   */
  public void addGlobalError(@NotNull Object origin, @NotNull String message) {
    globalErrors.add(new GlobalError(origin.getClass().getName(), message));
  }

  public static class FieldError {

    private final String field;
    private final String message;

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

    private final String objectName;
    private final String message;

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
