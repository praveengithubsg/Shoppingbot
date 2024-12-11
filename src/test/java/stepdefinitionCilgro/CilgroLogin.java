package stepdefinitionCilgro;

import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import pageObjectsCilgro.CilgroCartPage;
import pageObjectsCilgro.CilgroLoginPage;
import pageObjectsOtto.CartPage;
import pageObjectsOtto.LoginPage;

public class CilgroLogin {

	CilgroLoginPage loginPage = new CilgroLoginPage();
	CilgroCartPage cartPage=new CilgroCartPage();
	Scenario scenario;
		
	@When("^Cilgro User enters valid login details$")
	public void cilgro_User_enters_valid_login_details() {
		loginPage.enterLoginDetails();
	}

	@When("^Cilgro User clicks on login button$")
	public void cilgro_user_clicks_on_login_button() {
		loginPage.clickLoginButton();
		loginPage.clickCloseButton();
		
	}
	
	@When("^Cilgro User clears cart before adding product$")
	public void cilgro_user_clears_cart_before_adding_product() {
		cartPage.clearsCart();
	}
	
}
