package pageObjectsBol;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Config;
import commonHelper.CommonMethods;
import commonHelper.GenericHelper;
import commonHelper.JavaScriptHelper;
import commonHelper.WaitHelper;
import fileReader.DataHandlers;
import pageObjectsOtto.LoginPage;
import testRunner.CucumberRunnerCilgro;

public class BolHomePage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	JavaScriptHelper jsHelper = new JavaScriptHelper();
	DataHandlers dataReader = new DataHandlers();
	Config config = new Config();
	private Logger log = Logger.getLogger(BolHomePage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public BolHomePage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	@FindBy(xpath = "//button[@class='js-confirm-button']")
	private WebElement btnAccept;

	@FindBy(xpath = "//input[@id='searchfor']")
	private WebElement txtSearchBar;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement btnSearch;

	/**
	 * WebElement declaration ends here
	 **/

	public void clickAccept() {
		if (btnAccept.isDisplayed())
			commonMethods.click(btnAccept);
		log.info("HomePge is displayed successfully");
		waitHelper.staticWait(500);;
	}

	public String prefixZeros(int row) {
		String itemcode = null;
		try {
			int width = config.getCdProductLength();
			char fill = '0';
			int length = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getcdProduct())
					.length();

			if (length != width && config.getPrefixZeroesToItemCode().equalsIgnoreCase("true")) {
				String toPad = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getcdProduct());
				itemcode = new String(new char[width - toPad.length()]).replace('\0', fill) + toPad;
			} else {
				itemcode = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getcdProduct());
			}
		} catch (Exception e) {

		}
		return itemcode;

	}

	public void enterItemCode(int row) {

		String itemCode = this.prefixZeros(row);
		commonMethods.clearAndSendKeys(this.txtSearchBar, itemCode);

		log.info("item code entered as : " + itemCode);
		log.info("Item code is entered");
	}

	public void clickSearchButton() {
		commonMethods.click(btnSearch);
		log.info("search button clicked");
	}

}
