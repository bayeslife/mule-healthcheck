package bayeslife;

import bayeslife.healthcheck.HealthCheckValidatorFactory;
import bayeslife.healthcheck.validator.HealthCheckValidator;
import org.mule.api.MuleContext;
import org.mule.api.context.notification.ServerNotification;
import org.mule.api.registry.MuleRegistry;
import org.mule.context.notification.MuleContextNotification;

import java.util.List;
;

public class MuleContextIntrospectionListener implements org.mule.api.context.notification.MuleContextNotificationListener{


	public void onNotification(ServerNotification notification) {

		MuleContextNotification n = (MuleContextNotification)notification;

		MuleContext context = n.getMuleContext();

		MuleRegistry reg = n.getMuleContext().getRegistry();
//        MuleRegistryHelper reghelper = (MuleRegistryHelper)reg;
//
//
//		List<Registry> regs = (List)reghelper.getRegistries();
//		registerHttpRequesters(context,regs);

		HealthCheckValidatorFactory factory = new HealthCheckValidatorFactory();//This should be a bean lookup

		List<HealthCheckValidator> validators = factory.getValidators();
		for(HealthCheckValidator validator : validators){
			validator.initialize(context);
		}


	}

}
