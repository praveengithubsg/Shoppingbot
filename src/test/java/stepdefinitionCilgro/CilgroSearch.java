package stepdefinitionCilgro;

import commonHelper.GenericHelper;
import cucumber.api.Scenario;
import cucumber.api.java.en.When;
import pageObjectsCilgro.CilgroSearchPage;


public class CilgroSearch {
	CilgroSearchPage searchPage = new CilgroSearchPage();
	GenericHelper genericHelper = new GenericHelper();
	Hooks hooks=new Hooks();
	Scenario scenario;
	
	@When("^Cilgro user selects the required quantity and clicks on AddToCart button$")
	public void cilgro_user_selects_the_required_quantity_and_clicks_on_AddToShoppingCart_button() {
		searchPage.enterRequiredQuantity(); 
		}
}
