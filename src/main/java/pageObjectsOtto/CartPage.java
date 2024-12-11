package pageObjectsOtto;

import java.util.List;

import org.apache.log4j.Logger;
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

public class CartPage extends CucumberRunnerCilgro {
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
	private Logger log = Logger.getLogger(CartPage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public CartPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	@FindBy(xpath = "//header/div[@id='a']/div[2]/div[3]/div[1]/a[1]")
	private WebElement btnCartBag;

	@FindBy(xpath = "//div[@class='action-links']/a[2]")
	private List<WebElement> lnkDelete;

	@FindBy(xpath = "//div[@class='msg-block msg-empty-basket']")
	private WebElement msgEmptyBasket;
	
	@FindBy(xpath = "//div[@id='basketPage']")
	private WebElement lblShoppingCart;

	/**
	 * WebElement declaration ends here
	 **/

	public void clearsCart() {
		try {
			
		
		//waitHelper.staticWait(1000);

		if (commonMethods.isElementPresent(btnCartBag)) {
			// commonMethods.click(iconCart);
			commonMethods.moveToElementAndClick(btnCartBag);
			//waitHelper.staticWait(1000);
			waitHelper.waitForElementVisible(lblShoppingCart);
			if (commonMethods.isElementPresent(msgEmptyBasket)) {
				log.info("Bag is Empty");
			} else {
				
				int deliconCount = lnkDelete.size();
				System.out.println("Before For - Delete count: "+deliconCount);
				for (int i = 0; i < deliconCount-3; i++) {
					waitHelper.staticWait(2500);
					waitHelper.waitForElementToBeClickable(lnkDelete.get(0));
					commonMethods.click(lnkDelete.get(0));
					log.info("Delete icon clicked");
					System.out.println("i= "+i);
					System.out.println("Delete count: "+deliconCount);

					//waitHelper.staticWait(500);
				}
			}
		}

	}
	catch(IndexOutOfBoundsException e)
	{
		log.info("All cart items are removed sucessfully");
	}
	}
}
