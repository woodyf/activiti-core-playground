package tw.com.cybersoft.fsd.logic;

import static tw.com.cybersoft.fsd.logic.Utils.nowStr;

import org.activiti.api.process.runtime.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class Connectors {

	private static final Logger log = LoggerFactory.getLogger(Connectors.class);

	// GroupDemo
	@Bean
	public Connector testConnector() {
		return integrationContext -> {
			log.info("testConnector executed");
			integrationContext.addOutBoundVariable(nowStr(), "testConnectorOutput");
			return integrationContext;
		};
	}

	// TimerDemo
	@Bean
	public Connector timerAfterConnector() {
		return integrationContext -> {
			log.info("timerAfterConnector executed");
			integrationContext.addOutBoundVariable(nowStr(), "timerAfterOutput");
			return integrationContext;
		};
	}

	// JsonVarDemo
	@Bean
	public Connector updateVarConnector() {
		return integrationContext -> {
			log.info("updateVarConnector executed");
			DemoVariables demoVariables = integrationContext.getInBoundVariable("jsonVar", DemoVariables.class);
			demoVariables.setVar2("processedByServiceTask");
			integrationContext.addOutBoundVariable("jsonVar", demoVariables);
			return integrationContext;
		};
	}
}
