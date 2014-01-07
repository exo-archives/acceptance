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


import org.exoplatform.acceptance.service.EntityNotFoundException;

import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Errors handler for REST services to convert an exception in a standardized JSON message and the related HTTP status code.
 */
@ControllerAdvice
public class JsonErrorHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(JsonErrorHandler.class);

  /**
   * Catch HttpMessageNotReadableException to log it (helps to diagnose errors and attacks on REST services).
   *
   * @param ex The exception trapped
   * @return A standardized {@link JsonErrorResponse}
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public JsonErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) throws IOException {
    LOGGER.warn("Http Message Not Readable : {}", ex.getMessage());
    return new JsonErrorResponse(ex);
  }

  /**
   * Catch DuplicateKeyException when an entity creation or update doesn't a constraint of uniqueness in the mongo repository.
   *
   * @param ex The exception trapped
   * @return A standardized {@link JsonErrorResponse}
   */
  @ExceptionHandler(DuplicateKeyException.class)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public JsonErrorResponse handleDuplicateKeyException(DuplicateKeyException ex) throws IOException {
    LOGGER.warn("Duplicated Key : {}", ex.getRootCause().getMessage());
    return new JsonErrorResponse("Duplicated Key Error", JsonPath.read(ex.getRootCause().getMessage(), "$.err").toString());
  }

  /**
   * Catch MethodArgumentNotValidException when a Bean Validation error occurs.
   *
   * @param ex The exception trapped
   * @return A standardized {@link JsonErrorResponse}
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public JsonErrorResponse processValidationError(MethodArgumentNotValidException ex) throws IOException {
    LOGGER.warn("Validation error : {}", ex.getMessage());
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
    JsonErrorResponse errors = new JsonErrorResponse();
    for (FieldError fieldError : fieldErrors) {
      errors.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    for (ObjectError globalError : globalErrors) {
      errors.addGlobalError(globalError.getObjectName(), globalError.getDefaultMessage());
    }
    return errors;
  }

  /**
   * Catch EntityNotFoundException to return a 404 error.
   *
   * @param ex The exception trapped
   * @return A standardized {@link JsonErrorResponse}
   */
  @ExceptionHandler(EntityNotFoundException.class)
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public JsonErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) throws IOException {
    return new JsonErrorResponse(ex);
  }

}
