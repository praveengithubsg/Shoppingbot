package stepdefinitionBol;

import org.apache.log4j.Logger;

import base.Config;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import testRunner.CucumberRunnerBol;
import testRunner.CucumberRunnerCilgro;
import io.cucumber.java.*;

public class Hooks extends CucumberRunnerBol{
	
	private Logger log = Logger.getLogger(Hooks.class.getName());
	Config config=new Config();
	
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
		if(config.getCloseBrowserCondition().equalsIgnoreCase("true")) {
			quit();
		}
		
	}
}
