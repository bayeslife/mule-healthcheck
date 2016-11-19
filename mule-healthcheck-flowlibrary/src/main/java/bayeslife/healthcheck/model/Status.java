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
@XmlType(namespace = "http://bayeslife.healthcheck.model")
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public enum Status  {
    ACTIVE("ACTIVE"),
      FAILED("FAILED"),
  ;

	private final String value;
	private final static Map<String, Status> CONSTANTS = new HashMap<String, Status>();

	static {
        for (Status c: values()) {
            CONSTANTS.put(c.value, c);
        }
	}

	private Status(String value) {
	   this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

    @JsonCreator
    public static Status fromValue(String value) {
        Status constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

 }
