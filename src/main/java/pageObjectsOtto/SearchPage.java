package pageObjectsOtto;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import base.Config;
import commonHelper.CommonMethods;
import commonHelper.GenericHelper;
import commonHelper.JavaScriptHelper;
import commonHelper.WaitHelper;
import fileReader.DataHandlers;
import testRunner.CucumberRunnerCilgro;

public class SearchPage extends CucumberRunnerCilgro {
	/**
	 * Class object declaration here
	 **/
	CommonMethods commonMethods = new CommonMethods();
	GenericHelper genericHelper = new GenericHelper();
	WaitHelper waitHelper = new WaitHelper();
	JavaScriptHelper jsHelper = new JavaScriptHelper();
	DataHandlers dataReader=new DataHandlers();
	Config config=new Config();
	HomePage homePage=new HomePage();
	private Logger log = Logger.getLogger(SearchPage.class.getName());

	/**
	 * Constructor to initialize page objects
	 **/
	public SearchPage() {
		PageFactory.initElements(browserFactory.getDriver(), this);
	}

	/**
	 * WebElement declaration starts here
	 **/
	@FindBy(xpath = "//a[@role='button'][1]")
	private WebElement btnIncreaseQty;
	
	@FindBy(xpath = "//a[@role='button'][2]")
	private WebElement btnDecreaseQty;
	
	@FindBy(xpath = "//span[@class='lbl-uom']")
	private WebElement lblMOQ;
	
	/*
	 * @FindBy(xpath = "//button[@id='btnQuickOrderBottom']") private WebElement
	 * btnAddToCart;
	 */
	
	@FindBy(xpath = "//input[@name='product_quantity']")
	private WebElement txtItemQty;
	
	@FindBy(xpath = "//div[@class='msg-block']")
	private WebElement msgNoProducts;
	
	@FindBy(xpath = "//div[@id='basketModalPopup']")
	private WebElement divBasketPopup;
	
	@FindBy(xpath = "//span[@class='lbl-price']")
	private List<WebElement> lblPrice;	
	
	@FindBy(xpath = "//form[@id='product-form']//input[@name='quantity']")
	private WebElement txtPdpItemQty;	
	
	@FindBy(xpath = "//button[@type='submit' and @class='btn btn-big btn-action btn-add-to-basket ellipsis' or @id='btnQuickOrderBottom']")
	private WebElement btnPdpAddToCart;
	
	@FindBy(xpath = "//form[@id='product-form']")
	private WebElement frmProductForm;	

	/**
	 * WebElement declaration ends here
	 **/

	public void clickIncreaseQtyButton() {

		try {
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboQty(), config.getRoboQtyHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, config.getRoboLog(), config.getRoboLogHeader());
			dataReader.setCellValue(config.getExcelSheetName(), 0, 12, "RoboPriceWeb");
			
			for (int row = 1; row <= dataReader.rowCount(config.getExcelSheetName()); row++) {
				homePage.enterItemCode(row);
				homePage.clickSearchButton();
				String prijs= null;
				int intAANTAL = 0;
				Double expSalePrice = 0.00;
				

					
				if (commonMethods.isElementPresent(txtItemQty)) {
					String discountPrice = this.getDiscountPrice();
					log.info("Discount price is " + discountPrice);
					dataReader.setCellValue(config.getExcelSheetName(), row, 12, discountPrice);
					//waitHelper.staticWait(200);
					prijs = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, 4);
					double doublePrijs = Double.parseDouble(prijs);
					doublePrijs = Double.parseDouble(new DecimalFormat("##.##").format(doublePrijs));
					String discountPerc = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, 5);
					if(Double.parseDouble(discountPerc) == 0.00)
					{
						prijs = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, 4);
					} else {
						Double originalPrice = Double.parseDouble(getoriginalPrice().replace("€ ", "").replace(",", "."));
						expSalePrice = originalPrice*(Double.parseDouble(discountPerc)/100);
						expSalePrice = Double.parseDouble(new DecimalFormat("##.##").format(expSalePrice));
					}
					discountPrice = discountPrice.replace("€ ", "").replace(",", ".");
					System.out.println("Discount price " +discountPrice);
					System.out.println("***********excel value******" + prijs);
					//waitHelper.staticWait(1000);
					double numericDiscountPrice = Double.parseDouble(discountPrice); 
					System.out.println("Expected sale price>>>>>" +expSalePrice);
					System.out.println("Numeric Disc price from app >>>>> "+numericDiscountPrice);
					System.out.println("Prijs from app >>>>> "+prijs);
					System.out.println("Disc price from app >>>>> "+discountPrice);
					if(prijs.equalsIgnoreCase(discountPrice) && commonMethods.isElementPresent(lblPrice.get(0)) || numericDiscountPrice == expSalePrice && commonMethods.isElementPresent(lblPrice.get(1)) )
						
						log.info("Sale Price after the calculation from excel: "+ expSalePrice + "\n discount% specified  in the excel: "+discountPerc+ "\n Sale price from application: " +discountPrice);
					else
						log.info("Price not matched or product is not displayed");
					//////////////////
					String MOQ = this.getMOQValue();
					String AANTAL = null;
					MOQ = MOQ.substring(MOQ.length() - 1);
					int intMOQ = Integer.parseInt(MOQ);
					System.out.println("MOQ Digit: " + intMOQ);
					AANTAL = dataReader.getIntegerCellValue(config.getExcelSheetName(), row, config.getAantal());
					intAANTAL = Integer.parseInt(AANTAL);
					System.out.println("Numeric AANTAL value: " + intAANTAL);

					int quotient = intAANTAL / intMOQ;
					int remainder = intAANTAL % intMOQ;
					if (quotient == 0 && intAANTAL <= intMOQ) {
						commonMethods.click(btnIncreaseQty);
						log.info("Increase qty button clicked only once");
						waitHelper.staticWait(200);
						dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), Integer.toString(intMOQ));
						dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "Attention Required as qty is added extra");
					}
					/*** To add the items multiple times ***/
					if (intMOQ != 1) {
						
						for (int j = 1; j <= quotient; j++) {
							if(remainder==0) {
								commonMethods.clearAndSendKeys(txtItemQty, AANTAL);
								log.info("Qty enterd directly to text box when MOQ modulus AANTAL is 0");
								dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), AANTAL);
								dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "OK");
							} else {
								commonMethods.click(btnIncreaseQty);
								log.info("Increase qty button clicked: " + j);
								waitHelper.staticWait(200);
								
								int roboqty = quotient*intMOQ;
								dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), Integer.toString(roboqty));
								if(intAANTAL==roboqty)
									dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "OK");
								else
									dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "Attention Required as qty is added less");								
							}
							
								
						}
					} else if (intMOQ == 1) {
						commonMethods.clearAndSendKeys(txtItemQty, AANTAL);
						log.info("Qty enterd directly to text box when MOQ is 1");
						dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(), AANTAL);
						dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboLog(), "OK");
					}

					this.clickAddToCartButton();
				
				} else  {
				
				  log.info("No result for the entered item code");
				  dataReader.setCellValue(config.getExcelSheetName(), row, config.getRoboQty(),
				  "0"); dataReader.setCellValue(config.getExcelSheetName(), row,
				  config.getRoboLog(), "No result for the entered item code");
				  dataReader.setCellValue(config.getExcelSheetName(), row, 12, "No result");
				  
				  waitHelper.staticWait(200);
				  Reporter.addStepLog("URL for the prodcut not found >> \n" +
				  browserFactory.getDriver().getCurrentUrl()); }
				 

			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		catch (NullPointerException e) {
			log.info("End of rows");
		}

	}
	
	public void clickDecreaseQtyButton() {
		commonMethods.click(btnIncreaseQty);
		log.info("Decrease qty button clicked");
	}	
	
	public String getMOQValue(){
		String MOQ=commonMethods.getText(lblMOQ);
		log.info("Returning MOQ value as:"+MOQ);
		return MOQ;
	}
	
	public void clickAddToCartButton() {
		commonMethods.click(btnPdpAddToCart);
		log.info("Add to cart button clicked");
		try {
			waitHelper.waitForElementVisible(divBasketPopup);
			log.info("Mini cart is displayed");
			/*
			 * waitHelper.waitForElementInVisiblity(divBasketPopup);
			 * log.info("Mini cart is dis-appeared");
			 */
		} catch(Exception e) {
			log.info("Mini cart is not displayed");
		}
		
	}
	
	public String getDiscountPrice() {
		String discountPrice = commonMethods.getText(lblPrice.get(0));
		log.info("Returning discount price value as:" + discountPrice);
		return discountPrice;
	}
	
	public String getoriginalPrice() {
		String originalPrice = commonMethods.getText(lblPrice.get(1));
		log.info("Returning Orignal price value as:" + originalPrice);
		return originalPrice;
	}

	
}
