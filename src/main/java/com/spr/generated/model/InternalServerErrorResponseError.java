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
 * InternalServerErrorResponseError
 */

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@JsonTypeName("internal_server_error_response_error")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.8.0")
public class InternalServerErrorResponseError implements Serializable {

  private static final long serialVersionUID = 1L;

  private String type;

  private String message;

  public InternalServerErrorResponseError type(String type) {
    this.type = type;
    return this;
  }

  /**
   * エラータイプ
   * @return type
   */
  
  @Schema(name = "type", example = "internal_server_error", description = "エラータイプ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public InternalServerErrorResponseError message(String message) {
    this.message = message;
    return this;
  }

  /**
   * エラーメッセージ
   * @return message
   */
  
  @Schema(name = "message", example = "予期しないエラーが発生しました。", description = "エラーメッセージ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InternalServerErrorResponseError internalServerErrorResponseError = (InternalServerErrorResponseError) o;
    return Objects.equals(this.type, internalServerErrorResponseError.type) &&
        Objects.equals(this.message, internalServerErrorResponseError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InternalServerErrorResponseError {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

