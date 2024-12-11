package pageObjectsCilgro;

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

public class CilgroSearchPage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	DataHandlers dataReader = new DataHandlers();
	Config config = new Config();
	CilgroHomePage homePage = new CilgroHomePage();
	private Logger log = Logger.getLogger(CilgroSearchPage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public CilgroSearchPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	@FindBy(xpath = "//a[@role='button'][1]")
	private WebElement btnIncreaseQty;

	@FindBy(xpath = "//a[@role='button'][2]")
	private WebElement btnDecreaseQty;

	@FindBy(xpath = "//div[@class='number_input clearfix']/input")
	private WebElement txtItemQty;

	@FindBy(xpath = "//div[@class='availability unavailable']")
	private WebElement msgProductUnavailable;

	@FindBy(xpath = "//div[@class='availability available']")
	private WebElement msgProductAvailable;

	@FindBy(xpath = "//div[@id='current_filters']")
	private WebElement divActiveFilter;

	@FindBy(xpath = "//span[@class='default_price']")
	private WebElement lblPrice;

	@FindBy(xpath = "//button[@class='add_to_shopping_cart']")
	private WebElement btnAddToCart;

	@FindBy(xpath = "//div[@class='product_inner']")
	private WebElement divProductTile;

	/**
	 * WebElement declaration ends here
	 **/

	public void enterRequiredQuantity() {
		String defaultPrice = "";

		try {
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboQty(), config.getRoboQtyHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboLog(), config.getRoboLogHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, 12, "RoboPriceWeb");

			for (int row = 1; row <= dataReader.rowCount(config.getExcelSheetName()); row++) {
				homePage.enterItemCode(row);
				homePage.clickSearchButton();
				int intAANTAL = 0;

				waitHelper.staticWait(2000);
				if (commonMethods.isElementPresent(divProductTile)) {
					if (commonMethods.isElementPresent(msgProductAvailable)) {
						defaultPrice = this.getDefaultPrice();
						log.info("Default price is " + defaultPrice);
						String AANTAL = null;
						AANTAL = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getAantal());
						intAANTAL = Integer.parseInt(AANTAL);
						System.out.println("Numeric AANTAL value: " + intAANTAL);
						waitHelper.staticWait(500);
						commonMethods.click(txtItemQty);
						commonMethods.keyPressSelectAll();

						waitHelper.staticWait(500);
						commonMethods.clearAndSendKeys(txtItemQty, AANTAL);
						waitHelper.staticWait(500);
						commonMethods.click(lblPrice);
						waitHelper.staticWait(500);
						String actQuantity = genericHelper.readValueFromInput(txtItemQty);
						log.info("Actula quantity available is: " + actQuantity);
						if (AANTAL.equals(actQuantity)) {
							log.info("Qty enterd to text box as: " + AANTAL);
							this.clickAddToCartButton();
							dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), AANTAL);
							dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "OK");
							dataReader.setCellValue(config.getExcelSheetName(), row, 12, defaultPrice);
						} else {
							log.info("Qty enterd to text box as: " + actQuantity);
							this.clickAddToCartButton();
							dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), actQuantity);
							dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(),
									"ATTENTION - LESS THAN REQUIRED");
							dataReader.setCellValue(config.getExcelSheetName(), row, 12, defaultPrice);
						}

						// this.clickAddToCartButton();

					} else {
						defaultPrice = this.getDefaultPrice();
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

				} else {
					log.info("No result for the entered item code");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), "0");
					dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(),
							"FAILED - ITEM NOT FOUND");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			log.info("End of rows");
		}
	}

	public void clickAddToCartButton() {
		waitHelper.staticWait(500);
		commonMethods.click(btnAddToCart);
		log.info("Add to cart button clicked");
		waitHelper.staticWait(1000);
	}

	/*
	 * public String getDiscountPrice() { String discountPrice =
	 * commonMethods.getText(lblPrice.get(0));
	 * log.info("Returning discount price value as:" + discountPrice); return
	 * discountPrice; }
	 */

	public String getDefaultPrice() {
		waitHelper.staticWait(2000);
		String defaultPrice = commonMethods.getText(lblPrice);
		log.info("Returning Orignal price value as:" + defaultPrice);
		return defaultPrice;
	}

}
