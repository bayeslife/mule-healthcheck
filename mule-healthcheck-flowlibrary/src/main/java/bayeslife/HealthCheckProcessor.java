package bayeslife;

import bayeslife.healthcheck.HealthCheckValidatorFactory;
import bayeslife.healthcheck.model.HealthReport;
import bayeslife.healthcheck.validator.HealthCheckValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.registry.Registry;
import org.mule.api.transport.Connector;
import org.mule.module.http.internal.request.DefaultHttpRequester;
import org.mule.module.http.internal.request.DefaultHttpRequesterConfig;
import org.mule.registry.MuleRegistryHelper;
import org.mule.transport.ssl.api.TlsContextFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * This class will implement all the health checks by delegating to class which support various types of health checks
 */
public class HealthCheckProcessor implements Callable {

    public Object onCall(MuleEventContext eventContext) throws Exception {

        MuleMessage m = eventContext.getMessage();

        MuleContext muleContext = eventContext.getMuleContext();

        HealthCheckValidatorFactory factory = new HealthCheckValidatorFactory();//This should be a bean lookup

        HealthReport report = new HealthReport();

        List<HealthCheckValidator> validators = factory.getValidators();
        for(HealthCheckValidator validator : validators){
            validator.validate(muleContext,report);
        }

        ObjectMapper mapper = new ObjectMapper();

        eventContext.getMessage().setPayload(mapper.writeValueAsString(report));

        return eventContext.getMessage();
    }
}
