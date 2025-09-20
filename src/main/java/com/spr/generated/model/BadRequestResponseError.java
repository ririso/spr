package com.spr.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.spr.generated.model.BadRequestResponseErrorDetailInner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BadRequestResponseError
 */

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@JsonTypeName("bad_request_response_error")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.8.0")
public class BadRequestResponseError implements Serializable {

  private static final long serialVersionUID = 1L;

  private String type;

  private String message;

  @Valid
  private List<@Valid BadRequestResponseErrorDetailInner> detail = new ArrayList<>();

  public BadRequestResponseError type(String type) {
    this.type = type;
    return this;
  }

  /**
   * エラータイプ
   * @return type
   */
  
  @Schema(name = "type", example = "invalid_parameter", description = "エラータイプ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BadRequestResponseError message(String message) {
    this.message = message;
    return this;
  }

  /**
   * エラーメッセージ
   * @return message
   */
  
  @Schema(name = "message", example = "不正なパラメータでリクエストされました。", description = "エラーメッセージ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BadRequestResponseError detail(List<@Valid BadRequestResponseErrorDetailInner> detail) {
    this.detail = detail;
    return this;
  }

  public BadRequestResponseError addDetailItem(BadRequestResponseErrorDetailInner detailItem) {
    if (this.detail == null) {
      this.detail = new ArrayList<>();
    }
    this.detail.add(detailItem);
    return this;
  }

  /**
   * Get detail
   * @return detail
   */
  @Valid 
  @Schema(name = "detail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("detail")
  public List<@Valid BadRequestResponseErrorDetailInner> getDetail() {
    return detail;
  }

  public void setDetail(List<@Valid BadRequestResponseErrorDetailInner> detail) {
    this.detail = detail;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BadRequestResponseError badRequestResponseError = (BadRequestResponseError) o;
    return Objects.equals(this.type, badRequestResponseError.type) &&
        Objects.equals(this.message, badRequestResponseError.message) &&
        Objects.equals(this.detail, badRequestResponseError.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, message, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BadRequestResponseError {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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

