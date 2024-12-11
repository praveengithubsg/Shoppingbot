package pageObjectsBol;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commonHelper.CommonMethods;
import commonHelper.WaitHelper;
import testRunner.CucumberRunnerCilgro;

public class BolCartPage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	WaitHelper waitHelper = new WaitHelper();
	private Logger log = Logger.getLogger(BolCartPage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public BolCartPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	@FindBy(xpath = "//div[@id='head_user']//a[@id='shopping_cart']")
	private WebElement btnCartBag;

	@FindBy(xpath = "//a[@class='delete-order-row right mt20']")
	private List<WebElement> lnkDelete;

	@FindBy(xpath = "//a[@class='btn mt20']/../p")
	private WebElement msgEmptyBasket;
	
	@FindBy(xpath = "//a[@class='right btn bordered blue transparent mr20 no_ajax no_default']")
	private WebElement btnEmptyShoppingCart;	
	
	@FindBy(xpath = "//button[@class='btn']")
	private WebElement btnConfirmEmptyShoppingCart;
	
	@FindBy(xpath = "//a[@class='mr15']")
	private WebElement btnCancelmEmptyShoppingCart;
	
	@FindBy(xpath = "//div[@class='confirm_fancybox fancybox-content']")
	private WebElement popupEmptyShoppingCart;

	/**
	 * WebElement declaration ends here
	 **/

	public void clearsCart() {
		try {

			waitHelper.staticWait(3000);			
			if (commonMethods.isElementPresent(btnCartBag)) {
				
				commonMethods.moveToElementAndClick(btnCartBag);
				log.info("Clicked on cart icon ");
				
				if (commonMethods.isElementPresent(msgEmptyBasket)) {
					log.info("Bag is Empty");
				} else {
					waitHelper.waitForElementVisible(btnEmptyShoppingCart);
					commonMethods.moveToElementAndClick(btnEmptyShoppingCart);
					log.info("Empty Shopping cart button is clicked");
					waitHelper.waitForElementVisible(popupEmptyShoppingCart);
					commonMethods.moveToElementAndClick(btnConfirmEmptyShoppingCart);
					waitHelper.staticWait(500);
					log.info("Confirm Empty Shopping cart button is clicked");
					if(commonMethods.isElementPresent(msgEmptyBasket)) {
						log.info("Cart clearing is successful! ");
					} else {
						log.info("Cart clearing is not successful!!! ");
					}
				}
			}
		} catch (Exception e) {
			log.info("All cart items are removed sucessfully");
		}

	}
	
}

