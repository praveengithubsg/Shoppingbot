package stepdefinitionOtto;

import commonHelper.GenericHelper;
import cucumber.api.java.en.When;
import pageObjectsOtto.HomePage;

public class Home {
	HomePage homePage = new HomePage();
	GenericHelper genericHelper = new GenericHelper();
	
		  @When("^user login is successfull$")
	  public void user_login_is_successfull()
	  { homePage.verifyLogin(); }
	  
	  @When("^user clicks on logout$")
	  public void user_clicks_on_logout() {
		  homePage.clickLogoutLink();
	  }
}
