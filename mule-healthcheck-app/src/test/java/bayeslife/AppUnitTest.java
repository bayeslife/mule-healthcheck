/**
 * MuleSoft Examples
 * Copyright 2014 MuleSoft, Inc.
 *
 * This product includes software developed at
 * MuleSoft, Inc. (http://www.mulesoft.com/).
 */

package bayeslife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.tck.junit4.rule.SystemProperty;
import org.mule.transport.NullPayload;

public class AppUnitTest extends FunctionalTestCase
{
    private int healthcheckport=7777;

	@Rule
	public DynamicPort port = new DynamicPort("http.port");


    @Rule
    public SystemProperty port2 = new SystemProperty("healthcheck.port",String.valueOf(healthcheckport));

    @Rule
    public SystemProperty port3 = new SystemProperty("client.port","1080");

    @Override
    protected String getConfigResources()
    {
        return "HelloWorld.xml, HealthCheck.xml";
    }

    @Test
    public void healthcheck() throws Exception
    {
//        MuleClient client = new MuleClient(muleContext);
//        Map<String, Object> props = new HashMap<String, Object>();
//        props.put("http.method", "GET");
//        MuleMessage result = client.send("http://0.0.0.0:" + healthcheckport + "/health", "", props);
//        assertNotNull(result);
//        assertFalse(result.getPayload() instanceof NullPayload);
//        String p = result.getPayloadAsString();
//        assertEquals("Hello World", result.getPayloadAsString());
    }

}

























