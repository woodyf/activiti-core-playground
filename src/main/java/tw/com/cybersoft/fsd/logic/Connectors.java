package tw.com.cybersoft.fsd.logic;

import org.activiti.api.process.runtime.connector.Connector;
import org.joda.time.Instant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class Connectors {

	@Bean
	public Connector testConnector() {
		return integrationContext -> {
			integrationContext.addOutBoundVariable("testConnectorOutput", Instant.now().toString());
			return integrationContext;
		};
	}
}
