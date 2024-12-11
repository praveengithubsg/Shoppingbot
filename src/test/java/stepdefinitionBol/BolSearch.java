package stepdefinitionBol;

import base.Config;
import commonHelper.GenericHelper;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjectsBol.BolSearchPage;


public class BolSearch {
	BolSearchPage searchPage = new BolSearchPage();
	GenericHelper genericHelper = new GenericHelper();
	Hooks hooks=new Hooks();
	Scenario scenario;
	
	@When("^bol user selects the required quantity and clicks on AddToCart button$")
	public void bol_user_selects_the_required_quantity_and_clicks_on_AddToShoppingCart_button() {
		searchPage.enterRequiredQuantity(); 
		
		System.out.println("boll");
		}
	
	
	@Then("^All the items added to the cart notification$")
	public void all_the_items_added_to_the_cart_notification() {
		Config.infoBox("All the items have been added to the Cart", "Add to Cart Completed");
	}
}
