package stepdefinitionOtto;

import org.apache.log4j.Logger;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import testRunner.CucumberRunnerCilgro;
import io.cucumber.java.*;

public class Hooks extends CucumberRunnerCilgro{
	
	private Logger log = Logger.getLogger(Hooks.class.getName());
	
	/**This methods sets up browser opening configuration before each scenario**/
	@Before
	public void beforeMethod() {
		log.info("Before Scenario Execution");
		openBrowser();
	}	

	@AfterStep
    public void beforeEachStep(Scenario scenario){
       // takeScreenshot(scenario);
        attachScreen(scenario);
    }
	
	/**This methods sets up tear down configuration before each scenario**/
	@After
	public void afterMethod(Scenario scenario) {
		log.info("After Scenario Execution");
		attachScreen(scenario);
		if (scenario.isFailed()) {
		takeScreenShot(scenario);
		}
		quit();
	}
}
