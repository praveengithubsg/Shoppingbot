package pageObjectsCilgro;

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

public class CilgroHomePage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	JavaScriptHelper jsHelper = new JavaScriptHelper();
	DataHandlers dataReader=new DataHandlers();
	Config config=new Config();
	CilgroLoginPage cilgroLogin=new CilgroLoginPage();
	private Logger log = Logger.getLogger(CilgroHomePage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public CilgroHomePage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	//@FindBy(xpath = "//input[@id='searchbox' or @type ='search']")
	//private WebElement txtSearchBar;
	/* Cigro app elements */
	
	@FindBy(xpath = "//form[@id='head_search']/input[@class='ui-autocomplete-input']")
	private WebElement txtSearchBar;	
	
	@FindBy(xpath = "//form[@id='head_search']/button")
	private WebElement btnSearch;	
	
	@FindBy(xpath = "//div[@class='fancybox-content']/button[@title='Close']")
	private WebElement btnCloseShipmentPopup;
	
	@FindBy(xpath = "//a[@class='btn no_ajax orange transparent bordered rounded']")
	private WebElement btnLogout;
	
	@FindBy(xpath = "//div[@class='availability unavailable']")
	private WebElement divOutOfStock;	
	
	@FindBy(xpath = "//div[@class='number_input clearfix']/input[@name='order_row_amount']")
	private WebElement txtItemQuantity;
	
	@FindBy(xpath = "//button[@class='add_to_shopping_cart']")
	private WebElement btnAddToCart;
	
	@FindBy(xpath = "//font[contains(text(),'Added')]")
	private WebElement lblAddedToCart;
	
	
	/**
	 * WebElement declaration ends here
	 **/

	public void verifyLogin() {
		Assert.assertTrue(genericHelper.isElementPresnt(btnLogout));
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
	
	/*
	 * public boolean clickSearchSuggestion() { boolean searchFlag = false;
	 * waitHelper.staticWait(1000); try { if(searchSuggestion.isDisplayed()) {
	 * System.out.println(">>>>>>>>>>"); searchFlag=true;
	 * commonMethods.click(searchSuggestion);
	 * log.info("search suggestion link clicked"); } } catch(NoSuchElementException
	 * e) { log.info("No search suggestion!!"); }
	 * 
	 * 
	 * return searchFlag; }
	 */
	
	public void clickLogoutLink() {
		//commonMethods.mouseHover(iconMyAccount);
		commonMethods.click(btnLogout);
		log.info("logout link clicked");
		waitHelper.staticWait(2000);
		if(genericHelper.isDisplayed(cilgroLogin.getLoginButton())) {
			log.info("Logout is successful");
		} else {
			log.info("Logout is not successful");
		}
	}
}
