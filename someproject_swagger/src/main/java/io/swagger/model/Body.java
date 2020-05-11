package io.swagger.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Body
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-24T08:54:41.386Z[GMT]")
public class Body   {
  @JsonProperty("pluginName")
  private String pluginName = null;

  @JsonProperty("fileknobs")
  @Valid
  private List<FileKnob> fileknobs = null;

  @JsonProperty("knobs")
  @Valid
  private Map<String, Object> knobs = null;

  public Body pluginName(String pluginName) {
    this.pluginName = pluginName;
    return this;
  }

  /**
   * Get pluginName
   * @return pluginName
  **/
  @ApiModelProperty(value = "")
  
    public String getPluginName() {
    return pluginName;
  }

  public void setPluginName(String pluginName) {
    this.pluginName = pluginName;
  }

  public Body fileknobs(List<FileKnob> fileknobs) {
    this.fileknobs = fileknobs;
    return this;
  }

  public Body addFileknobsItem(FileKnob fileknobsItem) {
    if (this.fileknobs == null) {
      this.fileknobs = new ArrayList<FileKnob>();
    }
    this.fileknobs.add(fileknobsItem);
    return this;
  }

  /**
   * Get fileknobs
   * @return fileknobs
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<FileKnob> getFileknobs() {
    return fileknobs;
  }

  public void setFileknobs(List<FileKnob> fileknobs) {
    this.fileknobs = fileknobs;
  }

  public Body knobs(Map<String, Object> knobs) {
    this.knobs = knobs;
    return this;
  }

  public Body putKnobsItem(String key, Object knobsItem) {
    if (this.knobs == null) {
      this.knobs = new HashMap<String, Object>();
    }
    this.knobs.put(key, knobsItem);
    return this;
  }

  /**
   * Get knobs
   * @return knobs
  **/
  @ApiModelProperty(value = "")
  
    public Map<String, Object> getKnobs() {
    return knobs;
  }

  public void setKnobs(Map<String, Object> knobs) {
    this.knobs = knobs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body body = (Body) o;
    return Objects.equals(this.pluginName, body.pluginName) &&
        Objects.equals(this.fileknobs, body.fileknobs) &&
        Objects.equals(this.knobs, body.knobs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pluginName, fileknobs, knobs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");
    
    sb.append("    pluginName: ").append(toIndentedString(pluginName)).append("\n");
    sb.append("    fileknobs: ").append(toIndentedString(fileknobs)).append("\n");
    sb.append("    knobs: ").append(toIndentedString(knobs)).append("\n");
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
