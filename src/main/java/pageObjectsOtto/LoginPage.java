package pageObjectsOtto;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Config;
import commonHelper.CommonMethods;
import commonHelper.GenericHelper;
import commonHelper.WaitHelper;
import fileReader.DataHandlers;
import fileReader.JsonReader;
import testRunner.CucumberRunnerCilgro;

public class LoginPage extends CucumberRunnerCilgro {

	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	JsonReader jsonReader = new JsonReader();
	DataHandlers dataReader=new DataHandlers();
	private Logger log = Logger.getLogger(LoginPage.class.getName());
	Config config=new Config();


	/**
	 * Constructor to initialize page objects
	 **/
	public LoginPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	
	@FindBy(xpath = "//input[@id='UserName' or @name='debnr']")
	private WebElement txtUserName;

	@FindBy(xpath = "//input[@id='Password' or @name='password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//button[@class='btn btn-medium btn-login' or @name='login_submit']")
	private WebElement btnLogin;
	
	@FindBy(xpath = "//button[@title='Close']")
	private WebElement btnClose;


	/**
	 * WebElement declaration ends here
	 **/

	public void inputUserName(String userType) {
		commonMethods.clearAndSendKeys(this.txtUserName, jsonReader.getUserName(userType));
		log.info("entered user email");
	}

	public void inputPassword(String userType) {
		commonMethods.clearAndSendKeys(this.txtPassword, jsonReader.getPassword(userType));
		log.info("entered user password");
	}

	public void clickLoginButton() {
		commonMethods.click(btnLogin);
		log.info("login button clicked");
	}
	
	public void enterLoginDetails() {
		
		String cdCred = config.getCdCredentials();
		this.inputUserName(cdCred);
		this.inputPassword(cdCred);
		log.info("Entered user credentials");
	}

	public void loginDetails(String username, String password) {
		this.enterLoginDetails();
		this.clickLoginButton();
		log.info("login submitted");
	}

	public WebElement getLoginButton() {
		return this.btnLogin;		
	}
	
	public void clickCloseButton() {
		if(commonMethods.isElementPresent(btnClose)) {
		commonMethods.click(btnClose);
		log.info("Close button clicked");
		}
	}
}

