package io.swagger.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

/**
 * AnalysisJob
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-24T08:54:41.386Z[GMT]")
public class AnalysisJob   {
  @JsonProperty("pluginName")
  private String pluginName = null;

  /**
   * Gets or Sets runState
   */
  public enum RunStateEnum {
    NEW("NEW"),
    
    RUNNING("RUNNING"),
    
    PAUSED("PAUSED"),
    
    COMPLETED("COMPLETED"),
    
    FAILED("FAILED"),
    
    CANCELLED("CANCELLED"),
    
    ERASED("ERASED"),
    
    UNKNOWN("UNKNOWN");

    private String value;

    RunStateEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RunStateEnum fromValue(String text) {
      for (RunStateEnum b : RunStateEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("runState")
  private RunStateEnum runState = null;

  @JsonProperty("start")
  private OffsetDateTime start = null;

  @JsonProperty("end")
  private OffsetDateTime end = null;

  @JsonProperty("files")
  @Valid
  private List<String> files = null;

  public AnalysisJob pluginName(String pluginName) {
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

  public AnalysisJob runState(RunStateEnum runState) {
    this.runState = runState;
    return this;
  }

  /**
   * Get runState
   * @return runState
  **/
  @ApiModelProperty(value = "")
  
    public RunStateEnum getRunState() {
    return runState;
  }

  public void setRunState(RunStateEnum runState) {
    this.runState = runState;
  }

  public AnalysisJob start(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  /**
   * Get start
   * @return start
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public OffsetDateTime getStart() {
    return start;
  }

  public void setStart(OffsetDateTime start) {
    this.start = start;
  }

  public AnalysisJob end(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  /**
   * Get end
   * @return end
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public OffsetDateTime getEnd() {
    return end;
  }

  public void setEnd(OffsetDateTime end) {
    this.end = end;
  }

  public AnalysisJob files(List<String> files) {
    this.files = files;
    return this;
  }

  public AnalysisJob addFilesItem(String filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<String>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * An array of job-local filespaths which can be retreived using the download call
   * @return files
  **/
  @ApiModelProperty(value = "An array of job-local filespaths which can be retreived using the download call")
  
    public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnalysisJob analysisJob = (AnalysisJob) o;
    return Objects.equals(this.pluginName, analysisJob.pluginName) &&
        Objects.equals(this.runState, analysisJob.runState) &&
        Objects.equals(this.start, analysisJob.start) &&
        Objects.equals(this.end, analysisJob.end) &&
        Objects.equals(this.files, analysisJob.files);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pluginName, runState, start, end, files);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalysisJob {\n");
    
    sb.append("    pluginName: ").append(toIndentedString(pluginName)).append("\n");
    sb.append("    runState: ").append(toIndentedString(runState)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
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
