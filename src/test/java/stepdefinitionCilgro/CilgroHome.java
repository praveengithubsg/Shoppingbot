package stepdefinitionCilgro;

import base.Config;
import commonHelper.GenericHelper;
import cucumber.api.java.en.When;
import pageObjectsCilgro.CilgroHomePage;
import pageObjectsOtto.HomePage;

public class CilgroHome {
	CilgroHomePage homePage = new CilgroHomePage();
	GenericHelper genericHelper = new GenericHelper();
	Config config=new Config();
	
		  @When("^Cilgro user login is successfull$")
	  public void cilgro_user_login_is_successfull()
	  { homePage.verifyLogin(); }
	  
	  @When("^Cilgro user clicks on logout$")
	  public void cilgro_user_clicks_on_logout() {
		  if(config.getLogoutCondition().equalsIgnoreCase("true")) {
			  homePage.clickLogoutLink();
		  }
	  }
}
