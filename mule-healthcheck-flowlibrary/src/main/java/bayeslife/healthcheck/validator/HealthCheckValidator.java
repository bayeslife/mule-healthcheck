package bayeslife.healthcheck.validator;

import bayeslife.healthcheck.model.HealthReport;
import org.mule.api.MuleContext;

/**
 * Interface for classes which validate specific aspects of a mule application
 */
public interface HealthCheckValidator {

    public void validate(MuleContext context, HealthReport report);

    public void initialize(MuleContext context);
}
