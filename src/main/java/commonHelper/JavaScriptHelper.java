
package commonHelper;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import testRunner.CucumberRunnerCilgro;

public class JavaScriptHelper extends CucumberRunnerCilgro {

	private Logger log = Logger.getLogger(JavaScriptHelper.class.getName());

	/**
	 * Executes JavaScript in the context of the currently selected frame or window.
	 **/
	public Object executeScript(String script) {
		JavascriptExecutor exe = (JavascriptExecutor) browserFactory.getDriver();
		log.info(script);
		return exe.executeScript(script);

	}

	/**
	 * Executes JavaScript in the context of the currently selected frame or window.
	 * args The arguments to the script. May be empty
	 **/
	public Object executeScript(String script, Object... args) {
		JavascriptExecutor exe = (JavascriptExecutor) browserFactory.getDriver();
		log.info(script);
		return exe.executeScript(script, args);
	}

	/**
	 * focus the cursor position to the webelement's location in a page based on its
	 * x and y coordinates location
	 **/
	public void scrollToElement(WebElement element) {
		executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
		log.info("Scrolling to : " + element);
	}

	/**
	 * focus the cursor position to the webelement's location in a page based on its
	 * x and y coordinates location and click
	 **/
	public void scrollToElementAndClick(WebElement element) {
		scrollToElement(element);
		element.click();
		log.info("Scrolling and clicking to " + element.toString());
	}

	/** focus cursor position to the webelement **/
	public void scrollIntoView(WebElement element) {
		executeScript("arguments[0].scrollIntoView()", element);
		log.info("Scrolling into element view : " + element.toString());
	}

	/** focus cursor position to the webelement and click **/
	public void scrollIntoViewAndClick(WebElement element) {
		scrollIntoView(element);
		element.click();
		log.info("Scrolling into view and click " + element.toString());
	}

	/** Scroll down to scroll height **/
	public void scrollDownVertically() {
		log.info("Scrolling to window vertical height");
		executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/** Scroll horizontal to scroll width **/
	public void scrollUpVertically() {
		log.info("Scrolling to window horizontal height");
		executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	/** Scroll down to scroll height bt 1500px down **/
	public void scrollDownByPixel() {
		log.info("Scrolling to window vertical by 1500 px down");
		executeScript("window.scrollBy(0,1500)");
	}

	/** Scroll down to scroll height bt 1500px up **/
	public void scrollUpByPixel() {
		log.info("Scrolling to window vertical by 1500 px up");
		executeScript("window.scrollBy(0,-1500)");
	}

	/** Zoom Page By 40% **/
	public void ZoomInBypercentage() {
		log.info("Zoom Page By 40%");
		executeScript("document.body.style.zoom='40%'");
	}

	/** Zoom Page By 100% **/
	public void ZoomBy100percentage() {
		log.info("Zoom Page By 100%");
		executeScript("document.body.style.zoom='100%'");
	}

	/** disabling read only attribute from DOM **/
	public void disableReadOnly(WebElement element) {
		log.info("disabling read only attribute from DOM");
		JavascriptExecutor js = (JavascriptExecutor) browserFactory.getDriver();
		js.executeScript("arguments[0].removeAttribute('readonly',0);", element);
	}

	/** Enabling read only attribute from DOM **/
	public void enableReadOnly(WebElement element) {
		log.info("Enabling read only attribute from DOM");
		JavascriptExecutor js = (JavascriptExecutor) browserFactory.getDriver();
		js.executeScript("arguments[0].setAttribute('readonly', true);", element);
	}

	/** Get Scroll height of current window **/
	public int getScrollHeight() {
		JavascriptExecutor exe = (JavascriptExecutor) browserFactory.getDriver();
		log.info("Getting Scroll height of active window");
		return Integer.parseInt(exe.executeScript("return document.body.scrollHeight").toString());
	}

}