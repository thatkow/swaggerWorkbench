package io.swagger.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * FileKnob
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-24T08:54:41.386Z[GMT]")
public class FileKnob   {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("filename")
  private String filename = null;

  @JsonProperty("dataBase64")
  private String dataBase64 = null;

  public FileKnob key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
  **/
  @ApiModelProperty(value = "")
  
    public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public FileKnob filename(String filename) {
    this.filename = filename;
    return this;
  }

  /**
   * Get filename
   * @return filename
  **/
  @ApiModelProperty(value = "")
  
    public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public FileKnob dataBase64(String dataBase64) {
    this.dataBase64 = dataBase64;
    return this;
  }

  /**
   * Get dataBase64
   * @return dataBase64
  **/
  @ApiModelProperty(value = "")
  
    public String getDataBase64() {
    return dataBase64;
  }

  public void setDataBase64(String dataBase64) {
    this.dataBase64 = dataBase64;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileKnob fileKnob = (FileKnob) o;
    return Objects.equals(this.key, fileKnob.key) &&
        Objects.equals(this.filename, fileKnob.filename) &&
        Objects.equals(this.dataBase64, fileKnob.dataBase64);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, filename, dataBase64);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileKnob {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    dataBase64: ").append(toIndentedString(dataBase64)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
