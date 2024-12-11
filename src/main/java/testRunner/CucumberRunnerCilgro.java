package testRunner;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import base.Config;
import commonHelper.BrowserFactory;
import commonHelper.ReportHelper;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import utilities.DateTimeHelper;

@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features/CilgroShoppingBot.feature", glue = "stepdefinitionCilgro", format = {
		"pretty", "json:target/cucumber.json" }, tags = { "@Cilgro" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-extent-reports/extent-report.html" }, dryRun = false)

public class CucumberRunnerCilgro extends CucumberBase {

}
