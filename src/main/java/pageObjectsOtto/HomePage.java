package pageObjectsOtto;

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
import testRunner.CucumberRunnerCilgro;

public class HomePage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	JavaScriptHelper jsHelper = new JavaScriptHelper();
	DataHandlers dataReader=new DataHandlers();
	Config config=new Config();
	LoginPage login=new LoginPage();
	private Logger log = Logger.getLogger(HomePage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public HomePage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	//@FindBy(xpath = "//input[@id='searchbox' or @type ='search']")
	//private WebElement txtSearchBar;
	
	@FindBy(xpath = "//input[@id='searchbox' ]  |  //form[@id='head_search']/input[@class='ui-autocomplete-input']")
	private WebElement txtSearchBar;
	
	
	@FindBy(xpath = "//button[@class='btn no-icon btn-search']")
	private WebElement btnSearch;
	
	@FindBy(xpath = "//a[@id='logoutLink']")
	private WebElement lnkLogout;
	
	@FindBy(xpath = "//span[@class='user-name font-darkest']")
	private WebElement lblCustomerName;
	
	@FindBy(xpath = "//div[@class='top-dropdown login']")
	private WebElement iconMyAccount;	
	
	@FindBy(xpath = "//span[@class='suggest-title-id']")
	private WebElement searchSuggestion;
	
	
	/**
	 * WebElement declaration ends here
	 **/

	public void verifyLogin() {
		Assert.assertTrue(genericHelper.isElementPresnt(lblCustomerName));
		log.info("login is successfull");
	}
	
	public void enterItemCode(int row) {
		// System.out.println("======== enterItem code method invoked");
		try {			
			 int width = config.getCdProductLength();
			  char fill = '0';
			  int length = dataReader.getIntegerCellValue(config.getExcelSheetName(),row,config.getcdProduct()).length();
			  if(length!=width && config.getPrefixZeroesToItemCode().equalsIgnoreCase("true"))
			  {
			  String toPad = dataReader.getIntegerCellValue(config.getExcelSheetName(),row,config.getcdProduct());
			  String itemcode = new String(new char[width - toPad.length()]).replace('\0', fill) + toPad;
			  System.out.println("itemcde**"+ itemcode);
			 // int  itemcodeint = Integer.parseInt(itemcode);
			  //System.out.println("itemcdeint**"+ itemcodeint);
			  
			  commonMethods.clearAndSendKeys(this.txtSearchBar,itemcode);
					//dataReader.getIntegerCellValue(config.getExcelSheetName(), row, itemcodeint));
			  log.info("item code entered as : " +itemcode);
			  }
			  else 
			  {commonMethods.clearAndSendKeys(this.txtSearchBar,
						dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getcdProduct()));
			  log.info("item code entered as : "
						+ dataReader.getIntegerCellValue(config.getExcelSheetName(), row,  config.getcdProduct()));
			  }
			  		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Item code is entered");
	}
	
	public void clickSearchButton() {
		commonMethods.click(btnSearch);
		log.info("search button clicked");
	}
	
	public boolean clickSearchSuggestion() {
		boolean searchFlag = false;
		waitHelper.staticWait(1000);
		try {
			if(searchSuggestion.isDisplayed()) {
				System.out.println(">>>>>>>>>>");
				searchFlag=true;
				commonMethods.click(searchSuggestion);
				log.info("search suggestion link clicked");
			} 
		} catch(NoSuchElementException e) {
			log.info("No search suggestion!!");
		}
		
		
		return searchFlag;
	}
	
	public void clickLogoutLink() {
		commonMethods.mouseHover(iconMyAccount);
		commonMethods.click(lnkLogout);
		log.info("logout link clicked");
		waitHelper.staticWait(2000);
		if(genericHelper.isDisplayed(login.getLoginButton())) {
			log.info("Logout is successful");
		} else {
			log.info("Logout is not successful");
		}
	}
}
