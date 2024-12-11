
package commonHelper;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Config;
import testRunner.CucumberRunnerCilgro;

public class CommonMethods extends CucumberRunnerCilgro {

	/** Constructor to initialize driver **/
	public CommonMethods() {
		super();
	}

	Config config = new Config();
	/** Reading / Setting Timeouts from config.properties file **/
	private int time = config.getExplicitTimeoutInSec();
	private Logger log = Logger.getLogger(CommonMethods.class.getName());

	/** Click this element **/
	public void click(WebElement element) {
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		log.info("Clicked on Webelement : " + element);
	}

	/** Click this element using javascript executor **/
	public void clickUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) browserFactory.getDriver();
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		js.executeScript("arguments[0].click();", element);
		log.info("Clicked on Webelement : " + element);
	}

	/**
	 * Switches to the currently active modal dialog for this particular driver
	 * instance.
	 **/
	public Alert getAlert() {
		log.info("switch to alert");
		return browserFactory.getDriver().switchTo().alert();
	}

	/** Accepts the alert **/
	public void AcceptAlert() {
		log.info("Accept alert");
		getAlert().accept();
	}

	/** Dismiss the alert **/
	public void DismissAlert() {
		log.info("Dismiss alert");
		getAlert().dismiss();
	}

	/** Returns alert text **/
	public String getAlertText() {
		String text = getAlert().getText();
		log.info("The alert text is : " + text);
		return text;
	}

	/** returns boolean about alert presence **/
	public boolean isAlertPresent() {
		try {
			new WebDriverWait(browserFactory.getDriver(), 5).until(ExpectedConditions.alertIsPresent());
			log.info("true - Alert Present");
			return true;
		} catch (Exception e) {
			log.info("false - No Alert");
			return false;
		}
	}

	/** Accepts the alert **/
	public void AcceptAlertIfPresent() {
		if (!isAlertPresent())
			return;
		AcceptAlert();
		log.info("Alert Accepted");
	}

	/** Dismiss the alert **/
	public void DismissAlertIfPresent() {

		if (!isAlertPresent())
			return;
		DismissAlert();
		log.info("Alert dismissed");
	}

	/** Accepts the alert with sending some values **/
	public void AcceptPrompt(String text) {
		log.info("Accecting prompt");
		if (!isAlertPresent())
			return;

		Alert alert = getAlert();
		alert.sendKeys(text);
		alert.accept();
		log.info(text);
	}

	/** This method scrolls page to end **/
	public void goEndOfPage() {
		Actions action = new Actions(browserFactory.getDriver());
		action.sendKeys(Keys.END).perform();
		log.info("scrols from Key Board to end of the page");
	}

	/** This method Moves the mouse to the middle of the element **/
	public void mouseHoverOn(WebElement webElement) {
		Actions action = new Actions(browserFactory.getDriver());
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		action.moveToElement(webElement).build().perform();
		log.info("cursor movement to specified element");
	}

	/**
	 * This method Moves the mouse to an offset from the top-left corner of the
	 * element
	 **/
	public void mouseHover(WebElement webElement) {
		Actions action = new Actions(browserFactory.getDriver());
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		action.moveToElement(webElement, 0, 0).build().perform();
		log.info("cursor movement to specified element");
	}

	/** Performs a double-click at middle of the given element **/
	public void doubleClick(WebElement webElement) {
		Actions action = new Actions(browserFactory.getDriver());
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		action.doubleClick(webElement).build().perform();
		log.info("double clicked on specified element");
	}

	/**
	 * method that performs click-and-hold at the location of the source
	 * element,moves to the location of the target element, then releases the mouse.
	 **/
	public void dragAndDrop(WebElement source, WebElement target) {
		Actions action = new Actions(browserFactory.getDriver());
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(source));
		wait.until(ExpectedConditions.elementToBeClickable(target));
		action.dragAndDrop(source, target).build().perform();
		log.info("double click on specified element");
	}

	/** Clicks (without releasing) in the middle of the given element **/
	public void clickAndHold(WebElement target) {
		Actions action = new Actions(browserFactory.getDriver());
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.elementToBeClickable(target));
		action.clickAndHold(target).build().perform();
		log.info("click and hold on target element");
	}

	/** Sends keys to the active element **/
	public void Enter() {
		Actions action = new Actions(browserFactory.getDriver());
		action.sendKeys(Keys.ENTER).build().perform();
		log.info("Pressed enter key");
	}

	/** This method with press enter key of Keyboard **/
	public void keyPressEnter() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			log.info("Pressed enter key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** This method will press down arrow key of Keyboard **/

	public void keyPressDownArrow() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			log.info("Pressed down arrow key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** This method will press up arrow key of Keyboard **/
	public void keyPressUpArrow() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_UP);
			log.info("Pressed up arrow key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** This method will release down arrow key of Keyboard **/

	public void keyReleaseDownArrow() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyRelease(KeyEvent.VK_DOWN);
			log.info("released down arrow key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** TThis method will release up arrow key of Keyboard **/

	public void keyReleaseUpArrow() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyRelease(KeyEvent.VK_UP);
			log.info("released up arrow key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** This method will press the right click of your mouse. **/
	public void mousePressRightClick() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			log.info("right click of mouse");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** This method will press the left click of your mouse. **/
	@SuppressWarnings("deprecation")
	public void mousePressLeftClick() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_MASK);
			log.info("left click of mouse");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** True if the element is currently selected or checked, false otherwise **/
	public boolean isSelected(WebElement element) {
		boolean flag = element.isSelected();
		log.info("Is checkbox selected? :: " + flag);
		return flag;
	}

	/** selects the checkbox/radio button **/
	public void selectCheckBox(WebElement element) {

		if (!isSelected(element)) {
			element.click();
			log.info(element + " :: selected");
		} else {

			if (!isSelected(element)) {
				element.click();
				log.info(element + " :: selected");
			} else {

				log.info("checkbox/radio button already selected");
			}
		}
	}

	/** deselects the checkbox/radio button **/
	public void deSelectCheckBox(WebElement element) {
		if (isSelected(element)) {
			element.click();
			log.info(element + " :: deselected");
		} else {

			log.info("checkbox/radio button already deselected");
		}
	}

	/** Get the value of the given attribute of the element **/
	public String getHyperLink(WebElement element) {
		String link = element.getAttribute("href");
		log.info("Element : " + element + " Value : " + link);
		return link;
	}

	/** method to simulate typing into an element, which may set its value **/

	public void clearAndSendKeys(WebElement element, String value) {
		log.info("WebElement : " + element + " Value : " + value);
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * get the visible (i.e. not hidden by CSS) text of this element, including
	 * sub-elements.
	 **/
	public String getText(WebElement element) {
		log.info("the text is :: " + element.getText());
		return element.getText();
	}

	/** verifies the text of the element **/

	public synchronized boolean verifyTextEquals(WebElement element, String expectedText) {
		boolean flag = false;
		try {
			String actualText = element.getText();
			if (actualText.equals(expectedText)) {
				log.info("actualText is : " + actualText + " expected text is : " + expectedText);
				return flag = true;
			} else {
				log.error("actualText is : " + actualText + " expected text is : " + expectedText);
				return flag;
			}
		} catch (Exception ex) {
			log.error("actualText is : " + element.getText() + " expected text is : " + expectedText);

			log.info("exception thrown : " + ex);
			return flag;
		}
	}

	/** Removes html special characters used for symbols **/
	private String escapeHtml(String text) {
		log.info("Removing Symbol Special Characters");
		return text.replace("<", "&lt;").replace(">", "&gt;");
	}

	/** Verifies whether the html pagesource contains required text **/
	public boolean isTextExists(String text) {
		String pageSource = browserFactory.getDriver().getPageSource();
		log.info("Verifying whether the html pagesource contains required text");
		if (pageSource.contains(escapeHtml(text))) {
			return true;
		}
		return false;
	}

	/** Asserts whether the html pagesource contains required text **/
	public void found(String pageSource, String text) {
		if (!pageSource.contains(escapeHtml(text))) {
			fail("Text: '" + text + "' not found in page '" + pageSource + "'");
		}
	}

	/*** -------- dropDown methods ------------- ***/
	/** Select all options that display text matching the argument **/
	public void SelectUsingVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
		log.info("Locator : " + element + " Value : " + text);
	}

	/** Select all options that have a value matching the argument **/
	public void SelectUsingValue(WebElement element, String visibleValue) {

		Select select = new Select(element);
		select.selectByValue(visibleValue);
		log.info("Locator : " + element + " Value : " + visibleValue);
	}

	/**
	 * @ The first selected option in this select tag (or the currently selected
	 * option in a normal select)
	 */
	public String getSelectedValue(WebElement element) {
		String value = new Select(element).getFirstSelectedOption().getText();
		log.info("WebELement : " + element + " Value : " + value);
		return value;
	}

	/** Select the option at the given index **/
	public void SelectUsingIndex(WebElement element, int index) {

		Select select = new Select(element);
		select.selectByIndex(index);
		log.info("Locator : " + element + " Value : " + index);
	}

	/** returns a list containing all options belonging to this select tag **/
	public List<String> getAllDropDownValues(WebElement locator) {
		Select select = new Select(locator);
		List<WebElement> elementList = select.getOptions();
		List<String> valueList = new LinkedList<String>();

		for (WebElement element : elementList) {
			log.info(element.getText());
			valueList.add(element.getText());
		}
		return valueList;
	}

	public String getAttribute(WebElement element, String attributeName) {
		String attributeValue = element.getAttribute(attributeName);
		log.info("Returning attribute value for " + attributeName + " with value " + attributeValue);
		return attributeValue;
	}

	/** A By which locates A elements by the exact text it displays and clicks **/
	public void clickLink(String linkText) {
		log.info("Clicking linktext : " + linkText);
		getElement(By.linkText(linkText));
	}

	/** a By which locates elements that contain the given link text. and clicks **/
	public void clickPartialLink(String partialLinkText) {
		log.info("Clicking partial linktext : " + partialLinkText);
		getElement(By.partialLinkText(partialLinkText)).click();
		log.info("clicked on link text which contains :: " + partialLinkText);
	}

	/** Find the first WebElement using the given method **/
	protected WebElement getElement(By locator) {
		log.info(locator.toString());
		return browserFactory.getDriver().findElement(locator);
	}

	/** This method hits escape from KeyBoard **/
	public void pressEscape() {
		Actions action = new Actions(browserFactory.getDriver());
		action.sendKeys(Keys.ESCAPE).perform();
		log.info("Hits escpae key from Key Board");
	}

	public void waitSec() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ScrollPageDown() {
		Actions action = new Actions(browserFactory.getDriver());
		action.sendKeys(Keys.PAGE_DOWN).perform();
		log.info("scrolled to page down");
	}

	/** This method moves to element and clicks **/
	public void moveToElementAndClick(WebElement element) {
		log.info("Move to element and clicking " + element);
		Actions action = new Actions(browserFactory.getDriver());
		action.moveToElement(element).click().build().perform();
	}

	/** This method returns substring given between indexess **/
	public String subString(String value, int beginIndex, int endIndex) {
		log.info("Retuning substring between index " + beginIndex + " and " + endIndex);
		return value.substring(beginIndex, endIndex);
	}

	/** This method moves to element and clicks **/
	public void moveToElementAndEnter(WebElement element) {
		log.info("Move to element and clicking " + element);
		Actions action = new Actions(browserFactory.getDriver());
		action.moveToElement(element).sendKeys(Keys.ENTER).build().perform();
	}

	/** returns boolean value for element presence **/
	public boolean isElementPresent(WebElement webElement) {
		log.info("checking whether element is present");
		try {
			if(webElement.isDisplayed()) {
				log.info("Found element:::--" + webElement);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/** this methods scrolls to top of page **/
	public void ScrollPageUp() {
		Actions action = new Actions(browserFactory.getDriver());
		action.sendKeys(Keys.PAGE_UP).perform();
		log.info("scrolled to top of page");
	}

	/** this method clears text in field **/
	public void clear(WebElement element) {
		log.info("Clearing Text in Field " + element);
		element.clear();
	}

	/** method to simulate keys into an element, which may set its value **/
	public void sendKeys(WebElement element, Keys key) {
		log.info("WebElement : " + element);
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(key);
	}

	/** method to sends keys into an element, which may set its value **/
	public void sendKeys(WebElement element, String key) {
		log.info("WebElement : " + element);
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(key);
	}

	/** method to simulate typing into an element, along with jeyAction **/

	public void clearAndSendKeys(WebElement element, String value, Keys key) {
		log.info("WebElement : " + element + " Value : " + value + " :key " + key);
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), time);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(value, key);
	}

	/** This method with press enter key of Keyboard **/
	public void keyPressSelectAll() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_DELETE);
			
			robot.keyRelease(KeyEvent.VK_DELETE);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			// Thread.sleep(2000);
			log.info("Pressed control key");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/** driver navigation to the URL **/
	public void navigate(String value) {
		browserFactory.getDriver().navigate().to(value);
	}
	
	/** returns a size all options belonging to this select tag **/
	public int getSelOptionsSize(WebElement locator) {
		Select select = new Select(locator);
		List<WebElement> elementList = select.getOptions();
		
		
		return elementList.size();
	}

}
