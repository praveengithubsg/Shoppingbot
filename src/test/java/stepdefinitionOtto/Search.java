package stepdefinitionOtto;

import commonHelper.GenericHelper;
import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import pageObjectsOtto.SearchPage;


public class Search {
	SearchPage searchPage = new SearchPage();
	GenericHelper genericHelper = new GenericHelper();
	Hooks hooks=new Hooks();
	Scenario scenario;
	
	@When("^user selects the required quantity and clicks on AddToCart button$")
	public void user_selects_the_required_quantity_and_clicks_on_AddToShoppingCart_button() {
		searchPage.clickIncreaseQtyButton(); 
		}
}
