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
package org.exoplatform.acceptance.service;

/**
 *
 */
public class AcceptanceException extends Exception {
  /**
   * Constructs a new exception with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  public AcceptanceException() {
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public AcceptanceException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * {@code cause} is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval
   *                by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the
   *                {@link #getCause()} method).  (A <tt>null</tt> value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   * @since 1.4
   */
  public AcceptanceException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>).
   * This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link
   * java.security.PrivilegedActionException}).
   *
   * @param cause the cause (which is saved for later retrieval by the
   *              {@link #getCause()} method).  (A <tt>null</tt> value is
   *              permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   * @since 1.4
   */
  public AcceptanceException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new exception with the specified detail message,
   * cause, suppression enabled or disabled, and writable stack
   * trace enabled or disabled.
   *
   * @param message            the detail message.
   * @param cause              the cause.  (A {@code null} value is permitted,
   *                           and indicates that the cause is nonexistent or unknown.)
   * @param enableSuppression  whether or not suppression is enabled
   *                           or disabled
   * @param writableStackTrace whether or not the stack trace should
   *                           be writable
   * @since 1.7
   */
  public AcceptanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
