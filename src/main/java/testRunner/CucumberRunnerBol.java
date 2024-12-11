package testRunner;


import cucumber.api.CucumberOptions;


@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features/BolShoppingBot.feature", glue = "stepdefinitionBol", format = {
		"pretty", "json:target/cucumber.json" }, tags = { "@Bol" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-extent-reports/extent-report.html" }, dryRun = false)

public class CucumberRunnerBol extends CucumberBase {

}
