package stepdefinitionOtto;

import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import pageObjectsOtto.CartPage;
import pageObjectsOtto.LoginPage;

public class Login {

	LoginPage loginPage = new LoginPage();
	CartPage cartPage=new CartPage();
	Scenario scenario;
		
	@When("^User enters valid login details username and password in the login$")
	public void user_enters_valid_login_details_and_in_the_login() {	
		loginPage.enterLoginDetails();
	}

	@When("^User clicks on login button$")
	public void user_clicks_on_login_button() {
		loginPage.clickLoginButton();
		//loginPage.clickCloseButton();
	}
	
	@When("^User clears cart before adding product$")
	public void user_clears_cart_before_adding_product() {
		cartPage.clearsCart();
	}
	
}
