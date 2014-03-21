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
package org.exoplatform.acceptance.model.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.NotNull;

/**
 * <p>KeyPairCredential class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@JsonTypeName("KEY_PAIR")
public class KeyPairCredential extends Credential {
  @NotNull
  private String privateKey;
  @NotNull
  private String publicKey;

  /**
   * <p>Constructor for KeyPairCredential.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param privateKey a {@link java.lang.String} object.
   * @param publicKey a {@link java.lang.String} object.
   */
  @JsonCreator
  public KeyPairCredential(
      @NotNull @JsonProperty("name") String name,
      @NotNull @JsonProperty("privateKey") String privateKey,
      @NotNull @JsonProperty("publicKey") String publicKey) {
    super(Type.KEY_PAIR, name);
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  /**
   * <p>Getter for the field <code>privateKey</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPrivateKey() {
    return privateKey;
  }

  /**
   * <p>Setter for the field <code>privateKey</code>.</p>
   *
   * @param privateKey a {@link java.lang.String} object.
   */
  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * <p>Getter for the field <code>publicKey</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPublicKey() {
    return publicKey;
  }

  /**
   * <p>Setter for the field <code>publicKey</code>.</p>
   *
   * @param publicKey a {@link java.lang.String} object.
   */
  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

}
