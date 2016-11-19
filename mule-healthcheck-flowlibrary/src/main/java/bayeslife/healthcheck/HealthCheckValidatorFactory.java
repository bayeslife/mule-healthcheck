package bayeslife.healthcheck;

import bayeslife.healthcheck.validator.APIValidator;
import bayeslife.healthcheck.validator.HealthCheckValidator;
import bayeslife.healthcheck.validator.HttpRequesterValidator;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class HealthCheckValidatorFactory {

    public List<HealthCheckValidator> getValidators(){
        List<HealthCheckValidator> validators = new ArrayList<HealthCheckValidator>();
        validators.add(new HttpRequesterValidator());
        validators.add(new APIValidator());
        return validators;
    }
}
