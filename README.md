
# Mule Application Health Checks As A  Separate Concern

See this blog which describes this project.
https://xceptionale.wordpress.com/2016/11/19/mule-application-health-checks-as-a-separate-concern/

If your development teams develop and deploy Mulesoft applications you may have the problem that you need to continuously validate the version of any deployed application and you may want to continuously validate that the mule application can successfully communicate to its dependencies.  For any number of reasons you may want to monitor these things and you might want to think of implementing a health check API for your mule applications.

The obvious way to achieve this is to create a new mule flow in each mule application that provides the health check functionality. However this approach will not scale very well and if you need to justify it to a number of product owners across different teams and time , it is likely to be difficult to get the health check provisioned consistently never mind the difficulty in getting a health check API that is reasonable consistent across all the mule applications both historical, in development and future implementations.

This post will present a way to achieve a consistent health check across population of mule applications.

As a short introduction to the approach, it involves injecting a health check flow and a dependency library into a mule application via minor changes to the maven pom.xml file.

The inject flow has an http listener which will provide a simple REST GET endpoint to serve the health check information.
```
<flow name=”HealthCheck”>
<http:listener config-ref=”Health_Listener_Configuration” doc:description=”This endpoint receives an HTTP message.” doc:name=”Receive HTTP request” path=”health”/>
<component class=”bayeslife.HealthCheckProcessor”/>
</flow>
```
The flow will also declare a spring bean which will form the model for the health report.
```
<spring:beans>
<spring:bean name=”API.Version” class=”bayeslife.healthcheck.model.HealthReport”>
<spring:property name=”apiIdentifier” value=”${apiId}”/>
<spring:property name=”apiVersion” value=”${apiVersion}”/>
</spring:bean>
</spring:beans>
```
The HealthCheckProcessor component in the HealthCheck flow is a java class whose onCall method will instantiate the HealthReport and then ask a validator factory to produce validators and ask all the validators to run and in doing add test results into the HealthReport.

```
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
```
What the validators can do is retrieve the bean registry from the mule context and lookup beans of particular types.  In the example below DefaultHttpRequest beans (which correspond to  http.request stanzas in mule context files) are looked up and the metadata for the http request can be extracted.
```
 MuleRegistry registry = context.getRegistry();

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
```
The above code can extract the request information corresponding to the following mule flow items.
```
<http:request-config name=”HTTP_Request_Configuration” host=”localhost” port=”${provider.port}” basePath=”/api/” doc:name=”HTTP Request Configuration” protocol=”HTTP”/>
<http:request config-ref=”HTTP_Request_Configuration” path=”/api/test” method=”GET” doc:name=”HTTP”/>
```
The validator could then establish an http connection to the url if successful update the health report.
```
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
```

The health check flow can be published to an artefact repository along with a jar library containing all the java classes used by the health check.

Both these artefacts and be declared in the pom file of a mule application that you want the health check to be available in.

First we define a maven dependency plugin which will pull the published flow.zip into the mule application into the default src/main/app directory.  Each time the build is run this will happen.
```
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-dependency-plugin</artifactId>
<version>2.10</version>
<executions>
<execution>
<id>unpack</id>mule-healthcheck-app/src/test/java/bayeslife/AppUnitTest.java
<phase>initialize</phase>
<goals>
<goal>unpack</goal>
</goals>
<configuration>
<artifactItems>
<artifactItem>
<groupId>bayeslife</groupId>
<artifactId>mule-healthcheck-flowlibrary</artifactId>
<version>1.0.0-SNAPSHOT</version>
<type>zip</type>
<overWrite>true</overWrite>
<outputDirectory>${basedir}/src/main/app</outputDirectory>
</artifactItem>
</artifactItems>
</configuration>
</execution>
</executions>
</plugin>
```
Secondly a simple maven plugin can retrieve the health check jar file into the mule application.
```
 <dependency>
   <groupId>bayeslife</groupId>
   <artifactId>mule-healthcheck-flowlibrary</artifactId>
   <classifier>library</classifier>
   <version>1.0.0-SNAPSHOT</version>
</dependency>
```

If you build both projects and then start mule-healthcheck-app with mule you will be able to hit the health check api.
```
 curl http://localhost:8384/health
```
and receive the response
```
{“apiIdentifier”:”API1″,”apiVersion”:”APIVersion1″,

“dependencies”:[

{“service”:”HTTP/HTTPS”,”failureDescription”:”Connection refused (Connection refused)”,”status”:”FAILED”},{“service”:”HTTP/HTTPS”,”failureDescription”:”Connection refused (Connection refused)”,”status”:”FAILED”}]}
```
The health check library project has a unit test which uses  mockserver netty to set up a provider for a positive test, as well as a negative test when the mock is not set up.

The idea is to expand the set of available validators to include tests for

connectivity to jms providers
connectivity to database providers
validation of credentials by querying an identity provider
