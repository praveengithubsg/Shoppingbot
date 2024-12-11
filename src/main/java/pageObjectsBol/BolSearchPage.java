package pageObjectsBol;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import base.Config;
import commonHelper.CommonMethods;
import commonHelper.GenericHelper;
import commonHelper.WaitHelper;
import fileReader.DataHandlers;
import testRunner.CucumberRunnerCilgro;

public class BolSearchPage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	DataHandlers dataReader = new DataHandlers();
	Config config = new Config();
	BolHomePage homePage = new BolHomePage();
	private Logger log = Logger.getLogger(BolSearchPage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public BolSearchPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	@FindBy(xpath = "//select[@name='quantity']")
	private WebElement drpQty;
	
	@FindBy(xpath = "//div[@class='h-bottom--xs']/a[@data-test='add-to-basket']")
	private WebElement btnAddToCart;
	
	@FindBy(xpath = "//div[@data-test='btn-continue-shopping'] | //div[@class='c-btn-tertiary sb-button sb-chevron-next h-btn--full-medium']")
	private WebElement btnContOrder;
	
	@FindBy(xpath = "//div[@data-test='modal-window-close']")
	private WebElement btnClosePopUp;
	
	@FindBy(xpath = "//div[@class='product-prices']/span")
	private WebElement lblOutofStock;
	
	@FindBy(xpath = "//div[@data-test='no-result-content']")
	private WebElement lblNoResult;
	
	@FindBy(xpath = "(//select[@name='quantity'])[last()]")
	private WebElement drpCartQtyUpdate;
	
	@FindBy(xpath = "//input[@type='tel']")
	private WebElement txtQty;
	
	@FindBy(xpath = "//div[@class='c-btn-primary--large']")
	private WebElement btnOk;
	
	@FindBy(xpath = "//*[@id='js_items_content']//section/div/div//meta")
	private WebElement lblPrice;
	
	@FindBy(xpath = "//a[@data-list-page-product-click-location='show and buy button']")
	private WebElement btnShowandBuy;

	
	@FindBy(xpath = "//a[@data-buy-block-type='BuyBlock']")
	private WebElement btnBuyBack;
	
	
	@FindBy(xpath = "//div[@class='c-btn-tertiary sb-button sb-chevron-next h-btn--full-medium']")
	private WebElement popUpToOrder;
	
	@FindBy(xpath = "//a[@class='js_quantity_overlay_ok']")
	private WebElement btnOK;
	
	@FindBy(xpath = "//*[@id='mainContent']/div[2]/ul")
	private WebElement msgNotification;
	
	@FindBy(xpath = "//*[@id='mainContent']/div[2]/ul//b[2]")
	private WebElement txtMaxValue;
	
	
	
	
	
	/**
	 * WebElement declaration ends here
	 **/

	public void enterRequiredQuantity() {
		String defaultPrice = "";
		String AANTAL = null;
		String drpQtyVal= null;

		try {
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboQty(), config.getRoboQtyHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboLog(), config.getRoboLogHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, 12, "RoboPriceWeb");
			
			for (int row = 1; row <= dataReader.rowCount(config.getExcelSheetName()); row++) {
				homePage.enterItemCode(row);
				homePage.clickSearchButton();
				int intAANTAL = 0;

				AANTAL = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getAantal());

				intAANTAL = Integer.parseInt(AANTAL);
				waitHelper.staticWait(2000);
				defaultPrice = this.getDefaultPrice();
				if (commonMethods.isElementPresent(drpQty)) {

					if (intAANTAL <= 10) {
						System.out.println("valued"+intAANTAL+ "aatanl"+AANTAL);
						commonMethods.SelectUsingValue(drpQty, AANTAL);
						log.info("Selected quantity is:" + AANTAL);
						this.clickAddToCartButton();
						waitHelper.staticWait(300);
						this.closePopUp();
						this.aantalOK(row, commonMethods.getSelectedValue(drpQty), defaultPrice);
						
						
					} else if(intAANTAL>10) {
						System.out.println("valued greater than 10"+intAANTAL+ "aatanl"+AANTAL);
						this.clickAddToCartButton();
						drpQtyVal = this.addToCartandAdjustInventory(AANTAL);
						log.info("drop qty value4"+drpQtyVal);
						log.info("message for 24 qty4");
						if(!(this.substring(AANTAL, drpQtyVal)))
							this.aantalnotOK(row, drpQtyVal, defaultPrice);
						else
							this.aantalOK(row, drpQtyVal, defaultPrice);
						continue;
					}
					
					}
				else if (!(commonMethods.isElementPresent(drpQty)) && commonMethods.isElementPresent(btnAddToCart))
				{
					this.clickAddToCartButton();
					
					drpQtyVal= this.addToCartandAdjustInventory(AANTAL);
					log.info("drop qty value"+drpQtyVal);
					log.info("message for 24 qty3");
					if(!(this.substring(AANTAL, drpQtyVal)))
						this.aantalnotOK(row, drpQtyVal, defaultPrice);
					else
						this.aantalOK(row, drpQtyVal, defaultPrice);
					continue;
				}
				
				else if (commonMethods.isElementPresent(btnShowandBuy))
				{
					commonMethods.click(btnShowandBuy);
					waitHelper.waitForElementVisible(btnBuyBack);
					commonMethods.click(btnBuyBack);
					drpQtyVal = this.addToCartandAdjustInventory(AANTAL);
					if(!(this.substring(AANTAL,drpQtyVal)))
						this.aantalnotOK(row, drpQtyVal, defaultPrice);
					else
						this.aantalOK(row, drpQtyVal, defaultPrice);
					continue;
				}
				
				else if (commonMethods.isElementPresent(lblOutofStock))
				{
					log.info("Default price is " + defaultPrice);

					log.info("No result for the entered item code");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), "0");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(),
							"FAILED - ITEM IS OUT OF STOCK");
					dataReader.setCellValue(config.getExcelSheetName(), row, 12, defaultPrice);
					waitHelper.staticWait(200);
					Reporter.addStepLog(
							"URL for the prodcut not found >> \n" + browserFactory.getDriver().getCurrentUrl());
				}
				else if(commonMethods.isElementPresent(lblNoResult))
						
				{
					log.info("No result for the entered item code");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), "0");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(),
							"FAILED - ITEM NOT FOUND");
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		catch (NullPointerException e) {
			log.info("End of rows");
		}

	}

	public void clickAddToCartButton() {
		waitHelper.staticWait(500);
		commonMethods.click(btnAddToCart);
		log.info("Add to cart button clicked");
		waitHelper.staticWait(1000);
	}
	
	
	public String addToCartandAdjustInventory(String aantal) {
		int intAANTAL = 0;
		String firstSelValue = null;
		intAANTAL = Integer.parseInt(aantal);
		waitHelper.staticWait(2000);
		if(genericHelper.isDisplayed(btnContOrder))
			commonMethods.click(btnContOrder);
		waitHelper.waitForElementVisible(drpCartQtyUpdate);
		String lastOption = commonMethods.getAllDropDownValues(drpCartQtyUpdate).get(commonMethods.getSelOptionsSize(drpCartQtyUpdate)-1);
		//lastOption.chars().allMatch( Character::isDigit );		
		
		System.out.println(lastOption);
		//if((commonMethods.getSelOptionsSize(drpCartQtyUpdate)-1)<10)
		if(isNumeric(lastOption))
		{
			{
				if(commonMethods.getSelectedValue(drpCartQtyUpdate).equalsIgnoreCase(aantal))
				{
					//commonMethods.SelectUsingValue(drpCartQtyUpdate, aantal);
					firstSelValue = commonMethods.getSelectedValue(drpCartQtyUpdate);
				}
				else if ((commonMethods.getSelOptionsSize(drpCartQtyUpdate)-1)<intAANTAL)
				{
					commonMethods.SelectUsingIndex(drpCartQtyUpdate, commonMethods.getSelOptionsSize(drpCartQtyUpdate)-1);
					firstSelValue = commonMethods.getSelectedValue(drpCartQtyUpdate);
				}
				else {
					commonMethods.SelectUsingValue(drpCartQtyUpdate, aantal);
					
				}
			}
			
		//System.out.println("selected numeric value");
		}
		else
		{
			commonMethods.SelectUsingIndex(drpCartQtyUpdate, commonMethods.getSelOptionsSize(drpCartQtyUpdate)-1);
			waitHelper.waitForElementVisible(txtQty);
			if(intAANTAL<=10)
			{
				commonMethods.SelectUsingValue(drpCartQtyUpdate, aantal);
				firstSelValue = commonMethods.getSelectedValue(drpCartQtyUpdate);
			}
			else
			{
			commonMethods.clearAndSendKeys(txtQty, aantal);
			waitHelper.staticWait(500);
			commonMethods.click(btnOK);
			waitHelper.staticWait(200);
			firstSelValue = commonMethods.getSelectedValue(drpCartQtyUpdate);
			log.info("message for 24 qty1");
			}
		}
		waitHelper.staticWait(1000);
		log.info("message for 24 qty2");
		return firstSelValue;
		
	}
	

	public void closePopUp() {
		waitHelper.staticWait(500);
		commonMethods.click(btnClosePopUp);
		log.info("Close button clicked");
		waitHelper.staticWait(500);
	}

	public String getDefaultPrice() {
		waitHelper.staticWait(2000);
		String defaultPrice = commonMethods.getAttribute(lblPrice, "content");
		log.info("Returning Orignal price value as:" + defaultPrice);
		return defaultPrice;
	}

	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public boolean substring(String aantal, String selqty)
	{
		String selqtyarr [] = selqty.split(" ");
	
		//selqty = selqty.substring(0, 3).trim();
		if(aantal.equalsIgnoreCase(selqtyarr[0]))
		{
			System.out.println("true");
			return true;
		}
		System.out.println("false");
		return false;
	}
	
	public void aantalOK(int row, String AANTAL, String defaultPrice)
	{
		dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), AANTAL);
		dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "OK");
		dataReader.setCellValue(config.getExcelSheetName(), row, 12, defaultPrice);
	}
	
	public void aantalnotOK(int row, String AANTAL, String defaultPrice)
	{
		dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), AANTAL);
		dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "Attention required as quantity added less then OK");
		dataReader.setCellValue(config.getExcelSheetName(), row, 12, defaultPrice);
	}
}
