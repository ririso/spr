package com.spr.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BadRequestResponseErrorDetailInner
 */

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@JsonTypeName("bad_request_response_error_detail_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.8.0")
public class BadRequestResponseErrorDetailInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private String field;

  private String invalidType;

  public BadRequestResponseErrorDetailInner field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   */
  
  @Schema(name = "field", example = "user_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public BadRequestResponseErrorDetailInner invalidType(String invalidType) {
    this.invalidType = invalidType;
    return this;
  }

  /**
   * Get invalidType
   * @return invalidType
   */
  
  @Schema(name = "invalid_type", example = "必須項目が空", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("invalid_type")
  public String getInvalidType() {
    return invalidType;
  }

  public void setInvalidType(String invalidType) {
    this.invalidType = invalidType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadRequestResponseErrorDetailInner badRequestResponseErrorDetailInner = (BadRequestResponseErrorDetailInner) o;
    return Objects.equals(this.field, badRequestResponseErrorDetailInner.field) &&
        Objects.equals(this.invalidType, badRequestResponseErrorDetailInner.invalidType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, invalidType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadRequestResponseErrorDetailInner {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    invalidType: ").append(toIndentedString(invalidType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

