package commonHelper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import base.Config;
import testRunner.CucumberRunnerCilgro;

public class GenericHelper extends CucumberRunnerCilgro {

	private Logger log = Logger.getLogger(GenericHelper.class.getName());

	/**
	 * Get the visible (i.e. not hidden by CSS) text of this element, including
	 * sub-elements.
	 **/
	public String readValueFromElement(WebElement element) {

		if (null == element) {
			log.info("weblement is null");
			return null;
		}

		boolean displayed = false;
		try {
			displayed = isDisplayed(element);
		} catch (Exception e) {
			log.error(e.toString());
			Reporter.log(e.fillInStackTrace().toString());
			return null;
		}

		if (!displayed) {
			return null;
		}
		String text = element.getText();
		log.info("weblement valus is.. " + text);
		return text;
	}

	/** Get the value of the given attribute of the element **/
	public String readValueFromInput(WebElement element) {
		if (null == element) {
			return null;
		}
		if (!isDisplayed(element)) {
			return null;
		}
		String value = element.getAttribute("value");
		log.info("weblement value is.." + value);
		return value;
	}

	/** returns Whether or not the element is displayed **/
	public boolean isDisplayed(WebElement element) {
		try {
			new WaitHelper().waitForElementVisible(element);
			element.isDisplayed();
			log.info("element is displayed.." + element);
			return true;
		} catch (Exception e) {
			log.info(e.toString());
			Reporter.log(e.fillInStackTrace().toString());
			return false;
		}
	}

	/** returns false if the element is displayed **/
	protected boolean isNotDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("element is displayed.." + element);
			return false;
		} catch (Exception e) {
			log.error(e.toString());
			Reporter.log(e.fillInStackTrace().toString());
			return true;
		}
	}

	/**
	 * Get the visible (i.e. not hidden by CSS) text of this element, including
	 * sub-elements.
	 **/
	protected String getDisplayText(WebElement element) {
		if (null == element) {
			return null;
		}
		if (!isDisplayed(element)) {
			return null;
		}
		log.info("displayed text is :: " + element.getText());
		return element.getText();
	}

	/**
	 * Get the visible (i.e. not hidden by CSS) text of this element, including
	 * sub-elements.
	 **/
	public synchronized String getElementText(WebElement element) {
		if (null == element) {
			log.info("weblement is null");
			return null;
		}
		String elementText = null;
		try {
			elementText = element.getText();
		} catch (Exception ex) {
			log.info("Element not found " + ex);
			Reporter.log(ex.fillInStackTrace().toString());
		}
		log.info("element text is :: " + elementText);
		return elementText;
	}

	/** Get the hidden text of this element **/
	public String getTextInsideElement(WebElement webElement) {
		if (webElement.isDisplayed()) {
			return webElement.getText();
		} else {
			// have to use JavaScript because HtmlUnit will return empty string for a hidden
			// element's text
			String text = (String) executeScript("return arguments[0].innerHTML", webElement);
			text = text.replace("<BR></BR>", "\n"); // scary
			return text;
		}

	}

	/**
	 * Executes JavaScript in the context of the currently selected frame or window.
	 **/
	protected java.lang.Object executeScript(String script) {
		JavascriptExecutor js = (JavascriptExecutor) browserFactory.getDriver();
		return js.executeScript(script);
	}

	/**
	 * Executes JavaScript in the context of the currently selected frame or window.
	 **/
	protected java.lang.Object executeScript(String script, java.lang.Object... arg1) {
		JavascriptExecutor js = (JavascriptExecutor) browserFactory.getDriver();
		return js.executeScript(script, arg1);
	}

	/** throws user defined exception **/
	public void throwUserException(String message) {
		log.info("User defined execption is ...");
		try {
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** closes all windows except parent **/
	public void closeAllchildWindows() {
		log.info("Closing all child windows");
		String mainwindow = browserFactory.getDriver().getWindowHandle();
		Set<String> handle = browserFactory.getDriver().getWindowHandles();
		handle.remove(mainwindow);
		Iterator<String> it = handle.iterator();
		while (it.hasNext()) {
			browserFactory.getDriver().switchTo().window(it.next());
			browserFactory.getDriver().close();
			browserFactory.getDriver().switchTo().window(mainwindow);
		}
	}

	/** returns boolean value for element presence **/
	public boolean isElementPresent(WebElement webElement) {
		log.info("checking whether element is present");
		try {
			return isDisplayed(webElement);
		} catch (Exception e) {
			return false;
		}
	}

	/** switched to frame mentioned **/
	public void switchToFrame(WebElement webElement) {
		browserFactory.getDriver().switchTo().frame(webElement);
		log.info("switched to frame" + webElement.toString());
	}

	public boolean isSelected(WebElement element) {
		try {
			element.isSelected();
			log.info("element is selected.." + element);
			return true;
		} catch (Exception e) {
			log.info(e.toString());
			Reporter.log(e.fillInStackTrace().toString());
			return false;
		}
	}

	/** this method returns parent window **/
	public String getWindowHandle() {
		log.info("getting parent window " + browserFactory.getDriver().getWindowHandle());
		return browserFactory.getDriver().getWindowHandle();
	}

	/** this method returns all active windows **/
	public Set<String> getWindowHandles() {
		log.info("getting all active windows " + browserFactory.getDriver().getWindowHandles());
		return browserFactory.getDriver().getWindowHandles();
	}

	/** this method switches to window **/
	public void switchToWindow(String window) {
		log.info("Switching to window " + window);
		browserFactory.getDriver().switchTo().window(window);
	}

	/** this method switches to default Content **/
	public void switchToDefaulContent() {
		log.info("Switching to default window ");
		browserFactory.getDriver().switchTo().defaultContent();
	}

	/** Verifying whether element is present **/
	public boolean isElementPresnt(WebElement ele) {
		int waitTime = 10;
		Boolean s = false;
		log.info("Verifying element is present in dom");
		for (int i = 0; i < new WaitHelper().explicitTimeout; i++) {
			try {
				ele.isDisplayed();
				s = true;
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(waitTime);

				} catch (InterruptedException e1) {
					log.info("Waiting for element to appear on DOM");
				}
			}
		}
		return s;
	}

	/** returning current url **/
	public String getCurrentUrl() {
		log.info("getting current page url");
		return browserFactory.getDriver().getCurrentUrl();
	}

	/** uploading file **/
	public void fileUpload(String path) {
		StringSelection strSelection = new StringSelection(path);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(strSelection, null);
		Robot robot;
		try {
			robot = new Robot();
			robot.delay(300);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_ENTER);
			log.info("file uploaded successfully");
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public WebElement getElement(String locator) {
		return browserFactory.getDriver().findElement(By.xpath(locator));
	}

	public boolean isEnabled(WebElement element) {
		log.info("returning state of web element");
		return element.isEnabled();
	}

	public void closeAllWindowsExceptParent() {
		log.info("close All Windows Except Parent");
		String baseurl = new Config().getBaseUrl();
		Set<String> handle = browserFactory.getDriver().getWindowHandles();
		String mainwindow = this.getWindowOfUrl(baseurl);
		handle.remove(mainwindow);
		Iterator<String> it = handle.iterator();
		while (it.hasNext()) {
			browserFactory.getDriver().switchTo().window(it.next());
			browserFactory.getDriver().close();
			browserFactory.getDriver().switchTo().window(mainwindow);
		}
	}

	public String getWindowOfUrl(String url) {
		log.info("Returning Parent");
		String window = "";
		Set<String> handle = browserFactory.getDriver().getWindowHandles();
		Iterator<String> it = handle.iterator();
		while (it.hasNext()) {
			browserFactory.getDriver().switchTo().window(it.next());
			String currenturl = browserFactory.getDriver().getCurrentUrl();
			boolean baseUrlPresent = currenturl.contains(url);
			boolean adminUrlPresent = currenturl.contains("admin");
			if (baseUrlPresent && !adminUrlPresent) {
				window = browserFactory.getDriver().getWindowHandle();
				break;
			} else if (adminUrlPresent) {
				window = browserFactory.getDriver().getWindowHandle();
				break;
			} else {
				window = browserFactory.getDriver().getWindowHandle();
			}
		}
		return window;
	}
	
	public void switchToBaseWindow() {
		log.info("close All Windows Except Parent");
		String baseurl = new Config().getBaseUrl();
		String mainwindow = this.getWindowOfUrl(baseurl);
		browserFactory.getDriver().switchTo().window(mainwindow);
	}
}