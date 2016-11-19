/**
 * MuleSoft Examples
 * Copyright 2014 MuleSoft, Inc.
 *
 * This product includes software developed at
 * MuleSoft, Inc. (http://www.mulesoft.com/).
 */

package bayeslife;

import static org.junit.Assert.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.tck.junit4.rule.SystemProperty;
import org.mule.transport.NullPayload;

public class HealthCheckUnitTest extends FunctionalTestCase
{

    private int PROVIDER_PORT =1080;
    private int healthcheckport=1081;

    private String API_ID="API1";
    private String API_VERSION="V1";

    protected ClientAndServer mockServer;

    @Rule
    public DynamicPort port = new DynamicPort("http.port");


    @Rule
    public SystemProperty port2 = new SystemProperty("healthcheck.port",String.valueOf(healthcheckport));

    @Rule
    public SystemProperty port3 = new SystemProperty("provider.port",String.valueOf(PROVIDER_PORT));

    @Rule
    public SystemProperty apiId = new SystemProperty("apiId",String.valueOf(API_ID));

    @Rule
    public SystemProperty apiVersion = new SystemProperty("apiVersion",String.valueOf(API_VERSION));


    public void setup() throws Exception
    {
        mockServer = org.mockserver.integration.ClientAndServer.startClientAndServer(PROVIDER_PORT);

        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/api/*")
        ).respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8")
                                )
                                .withBody("{ message: 'success' }")
                );
    }



    @After
    public void teardown()  {
        if(mockServer!=null)
            mockServer.stop();
    }


    @Override
    protected String getConfigResources()
    {
        return "HelloWorld.xml, HealthCheck.xml";
    }

    @Test
    public void healthcheckActive() throws Exception
    {
        setup();
        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://0.0.0.0:" + healthcheckport + "/health", "", props);
        assertNotNull(result);
        assertFalse(result.getPayload() instanceof NullPayload);
        String p = result.getPayloadAsString();
        assertFalse(result.getPayloadAsString().contains("FAILED"));
        assertTrue(result.getPayloadAsString().contains(API_ID));
        assertTrue(result.getPayloadAsString().contains(API_VERSION));
    }
    @Test
    public void healthcheckFailure() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://0.0.0.0:" + healthcheckport + "/health", "", props);
        assertNotNull(result);
        assertFalse(result.getPayload() instanceof NullPayload);
        String p = result.getPayloadAsString();
        assertTrue(result.getPayloadAsString().contains("FAILED"));
        assertTrue(result.getPayloadAsString().contains(API_ID));
        assertTrue(result.getPayloadAsString().contains(API_VERSION));
    }


}

























