package bayeslife.healthcheck.validator;

import bayeslife.healthcheck.model.Dependency;
import bayeslife.healthcheck.model.HealthReport;
import bayeslife.healthcheck.model.Status;
import org.mule.api.MuleContext;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.registry.RegistrationException;
import org.mule.api.registry.Registry;
import org.mule.module.http.internal.request.DefaultHttpRequester;
import org.mule.module.http.internal.request.DefaultHttpRequesterConfig;
import org.mule.registry.MuleRegistryHelper;
import org.mule.transport.ssl.api.TlsContextFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class APIValidator implements HealthCheckValidator {

    //final public static String HTTPBEANS="healthcheck_httprequester";

    public void initialize(MuleContext context){

    }

    public void validate(MuleContext context, HealthReport report){



            HealthReport hr = (HealthReport)context.getRegistry().lookupObject("API.Version");

            report.setApiVersion(hr.getApiVersion());
            report.setApiIdentifier(hr.getApiIdentifier());




    }

}
