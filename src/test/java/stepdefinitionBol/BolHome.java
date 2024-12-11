package stepdefinitionBol;

import base.Config;
import commonHelper.GenericHelper;
import cucumber.api.java.en.When;
import pageObjectsBol.BolHomePage;



public class BolHome {
	BolHomePage homePage = new BolHomePage();
	GenericHelper genericHelper = new GenericHelper();
	Config config=new Config();
	
		
	@When("^Bol home page launches$")
	public void bol_home_page_launches() {
		homePage.clickAccept();
	}
}
