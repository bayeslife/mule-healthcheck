//Note this is a generated class and should not be edited
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
@XmlType(namespace = "http://bayeslife.healthcheck.model0")
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class  Dependency  {

    /* @param connectionUrl
      
    */
     @JsonProperty("connectionUrl")
     private String connectionUrl = null;
    /* @param responseTime
      The response time for the service in milliseconds
    */
     @JsonProperty("responseTime")
     private Double responseTime = null;
    /* @param service
      A name for the service being consumed
    */
     @JsonProperty("service")
     private String service = null;
    /* @param failureDescription
      
    */
     @JsonProperty("failureDescription")
     private String failureDescription = null;
    /* @param status
      
    */
     @JsonProperty("status")
     private Status status = null;

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getConnectionUrl(){
        return connectionUrl;
      }

      public void setConnectionUrl( String tconnectionUrl ) {
        this.connectionUrl = tconnectionUrl;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public Double getResponseTime(){
        return responseTime;
      }

      public void setResponseTime( Double tresponseTime ) {
        this.responseTime = tresponseTime;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getService(){
        return service;
      }

      public void setService( String tservice ) {
        this.service = tservice;
      }

      @XmlElement(required = false)
      @JsonProperty(required = false)
      public String getFailureDescription(){
        return failureDescription;
      }

      public void setFailureDescription( String tfailureDescription ) {
        this.failureDescription = tfailureDescription;
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
