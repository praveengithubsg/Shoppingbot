Feature: ottosimon - Add to cart Scenario

    @Smoke
	Scenario: TS_AddToCart_01 - User should be able to add required number of products into cart  
		When User enters valid login details username and password in the login 
		And User clicks on login button
		And user login is successfull
		And user selects the required quantity and clicks on AddToCart button
		And user clicks on logout	