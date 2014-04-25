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

import org.exoplatform.acceptance.model.StorableObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Authentication credentials
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "_jsonType"
)
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = KeyPairCredential.class),
        @JsonSubTypes.Type(value = NoCredential.class),
        @JsonSubTypes.Type(value = TokenCredential.class),
        @JsonSubTypes.Type(value = UsernamePasswordCredential.class)
    })
@Document(collection = "credentials")
public abstract class Credential extends StorableObject {

  /**
   * Constant <code>NONE</code>
   */
  public static final Credential NONE = new NoCredential();
  /**
   * The type of credential
   */
  @NotNull
  private Type type;

  /**
   * <p>Constructor for Credential.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   */
  protected Credential(@NotNull Type type) {
    this.type = type;
  }

  /**
   * <p>Constructor for Credential.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   */
  protected Credential(@NotNull String name, @NotNull Type type) {
    super(name);
    this.type = type;
  }

  /**
   * <p>Constructor for Credential.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param id   a {@link java.lang.String} object.
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   */
  protected Credential(@NotNull String name, @NotNull String id, @NotNull Type type) {
    super(name, id);
    this.type = type;
  }

  /**
   * <p>Constructor for Credential.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   * @param name a {@link java.lang.String} object.
   */
  public Credential(@NotNull Type type, @NotNull String name) {
    super(name);
    this.type = type;
  }

  /**
   * <p>Getter for the field <code>type</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   */
  public Type getType() {
    return type;
  }

  /**
   * <p>Setter for the field <code>type</code>.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   */
  public void setType(@NotNull Type type) {
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", getId()).add("type", getType()).add("name", getName()).toString();
  }

  /**
   * Credential types
   */
  public enum Type {
    NONE, PASSWORD, TOKEN, KEY_PAIR
  }
}
