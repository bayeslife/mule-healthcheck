package  bayeslife.healthcheck.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/*  */
@XmlRootElement(namespace = "http://bayeslife.healthcheck.model")
@XmlType(namespace = "http://bayeslife.healthcheck.model")
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class  HealthReport  {

    /* @param host
      The host name for the service reporting its health
    */
     @JsonProperty("host")
     private String host = null;
    /* @param apiIdentifier
      The identifier for the API used to associated policies to the API.
    */
     @JsonProperty("apiIdentifier")
     private String apiIdentifier = null;
    /* @param timestamp
      A datetime represented as a formatted string. 
    */

     @JsonProperty("timestamp")
     private String timestamp = null;
    /* @param apiVersion
      The version number of the API.
    */
     @JsonProperty("apiVersion")
     private String apiVersion = null;
    /* @param dependencies
      
    */
     @JsonProperty("dependencies")
     private List<Dependency> dependencies = new ArrayList<Dependency>();
    /* @param status
      
    */
     @JsonProperty("status")
     private Status status = null;

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getHost(){
        return host;
      }

      public void setHost( String thost ) {
        this.host = thost;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getApiIdentifier(){
        return apiIdentifier;
      }

      public void setApiIdentifier( String tapiIdentifier ) {
        this.apiIdentifier = tapiIdentifier;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getTimestamp(){
        return timestamp;
      }

      public void setTimestamp( String ttimestamp ) {
        this.timestamp = ttimestamp;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getApiVersion(){
        return apiVersion;
      }

      public void setApiVersion( String tapiVersion ) {
        this.apiVersion = tapiVersion;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public List<Dependency> getDependencies(){
        return dependencies;
      }

      public void setDependencies( List<Dependency> tdependencies ) {
        this.dependencies = tdependencies;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public Status getStatus(){
        return status;
      }

      public void setStatus( Status tstatus ) {
        this.status = tstatus;
      }

  }
