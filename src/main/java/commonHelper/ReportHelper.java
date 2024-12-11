
package commonHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cucumber.listener.Reporter;

import base.Config;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/*** class to generate cucumber report ***/
public class ReportHelper {
	private Logger log = Logger.getLogger(ReportHelper.class.getName());
	Config config=new Config();

	/**This methods generates html cucumber reports at end of execution**/
	public void generateCucumberReport(String browser) {
		log.info("Generating Cucumber Reports");
		File reportOutputDirectory = new File("target");
		ArrayList<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("target/cucumber.json");

		String projectName = "testng-cucumber";

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.addClassifications("Platform", System.getProperty("os.name"));
		configuration.addClassifications("Browser", browser);
		configuration.addClassifications("ExcelFileName", config.getExcelFileName());
		//configuration.addClassifications("Branch", "release/1.0");

		// optionally add metadata presented on main page via properties file
		/*
		 * List<String> classificationFiles = new ArrayList<String>();
		 * classificationFiles.add("src/main/resources/config/config.properties");
		 * configuration.addClassificationFiles(classificationFiles);
		 */
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();

	}

	/**This methods generates html extent reports at end of execution**/
	public void configureExtentReport(String browser) {
		log.info("configuring extent reports");
		String reportConfigPath = "src/test/resources/configuration/extent-config.xml";
		Reporter.loadXMLConfig(new File(reportConfigPath));
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("browser", browser);
		Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
	}
}