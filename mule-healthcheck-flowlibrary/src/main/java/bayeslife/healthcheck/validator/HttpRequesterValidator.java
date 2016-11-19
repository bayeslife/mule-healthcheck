package bayeslife.healthcheck.validator;

import bayeslife.healthcheck.model.Dependency;
import bayeslife.healthcheck.model.HealthReport;
import bayeslife.healthcheck.model.Status;
import bayeslife.healthcheck.validator.HealthCheckValidator;
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
public class HttpRequesterValidator implements HealthCheckValidator {

    final public static String HTTPBEANS="healthcheck_httprequester";

    public void initialize(MuleContext context){
        MuleRegistryHelper reghelper = (MuleRegistryHelper)context.getRegistry();

		List<Registry> regs = (List)reghelper.getRegistries();
		registerHttpRequesters(context,regs);
    }

    private void registerHttpRequesters(MuleContext context, List<Registry> registries) {
        try {
            for (Registry registry : registries) {

                //SpringRegistry sr = (SpringRegistry)registries.get(0);

                Map<String, DefaultHttpRequester> m = registry.lookupByType(DefaultHttpRequester.class);

                context.getRegistry().registerObject(HTTPBEANS, m);
            }

        } catch (RegistrationException ex) {
            //Log this
            System.out.println(ex);
        }
    }

    public void validate(MuleContext context, HealthReport report){

        MuleRegistry registry = context.getRegistry();
        //MuleRegistryHelper reghelper = (MuleRegistryHelper)reg;

//        Collection<Connector> cs = reghelper.getConnectors();
//
//        Collection<ImmutableEndpoint> endpoints = reghelper.getEndpoints();

        //List<Registry> regs = (List)reghelper.getRegistries();

        StringBuffer sb = new StringBuffer();

            Map<String, DefaultHttpRequester> ms = registry.lookupByType(DefaultHttpRequester.class);

            for (DefaultHttpRequester requester : ms.values()) {

                DefaultHttpRequesterConfig config = requester.getConfig();

                String port = requester.getPort();
                if (port == null)
                    port = config.getPort();
                TlsContextFactory tls = config.getTlsContext();
                String host = requester.getHost();
                if (host == null)
                    host = config.getHost();
                String path = requester.getPath();
                String basePath = config.getBasePath();
                String url = requester.getUrl();
                String target = requester.getTarget();
                String method = requester.getMethod();

                Dependency httpDependency = new Dependency();
                httpDependency.setService("HTTP/HTTPS");
                if(report.getDependencies()==null)
                    report.setDependencies(new ArrayList<Dependency>());
                report.getDependencies().add(httpDependency);

                String connectionUrl = "http://" + host + ":" + port + basePath + path;
                if(tls!=null)
                    connectionUrl = "https://" + host + ":" + port + basePath + path;

                httpDependency.setConnectionUrl(url);

                try {

                    URL connectonUrl = new URL(connectionUrl);
                    URLConnection connection = (HttpURLConnection) connectonUrl.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.connect();
                    httpDependency.setStatus(Status.ACTIVE);

                }catch(Exception ex){
                    httpDependency.setStatus(Status.FAILED);
                    httpDependency.setFailureDescription(ex.getMessage());
                }

            }
    }

}
