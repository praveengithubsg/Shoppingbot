
package commonHelper;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Config;
import testRunner.CucumberRunnerCilgro;

public class WaitHelper extends CucumberRunnerCilgro {

	int explicitTimeout = new Config().getExplicitTimeoutInSec();

	private Logger log = Logger.getLogger(WaitHelper.class.getName());

	/**
	 * Wait will ignore instances of NotFoundException that are encountered (thrown)
	 * by default inthe 'until' condition, and immediately propagate all others.
	 *
	 * @param timeOutInSeconds
	 * 
	 * @return
	 */

	/** Creating webdriver wait object **/
	private WebDriverWait getWait() {
		log.debug("webdriver fluent wait");
		WebDriverWait wait = new WebDriverWait(browserFactory.getDriver(), explicitTimeout);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		wait.ignoring(ElementNotInteractableException.class);
		wait.ignoring(ElementNotSelectableException.class);
		return wait;
	}

	/**
	 * An expectation for checking that an element, known to be present on the DOM
	 * of a page, isvisible.
	 **/
	public void waitForElementVisible(WebElement element) {
		log.info("fluentwaitForvisibilityOfElement" + element);
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("element found..." + element.getText());
	}

	/** An expectation for checking the element to be invisible **/
	public void waitForElementInVisiblity(WebElement element) {
		log.info("waitForInvisibilityOfElement" + element);
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 **/
	public void waitForElementToBeClickable(WebElement element) {
		log.info("waitForelementToBeClickable" + element);
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/** An expectation for checking an alert is present. **/
	public void waitForAlertToBePresent() {
		log.info("waitForAlertToBePresent");
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.alertIsPresent());
	}

	/** Waiting for window to be of specific count **/
	public void waitForWindowCountPresent(int num) {
		log.info("Waiting for window to be of specific count");
		WebDriverWait wait = getWait();
		try {
			wait.until(ExpectedConditions.numberOfWindowsToBe(num));
		} catch (Exception e) {
			log.info("Windows count as expected");
		}
	}

	/** Waiting for specific time **/
	public void staticWait(int time) {
		log.info("Waiting for " + time + " ms");
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
