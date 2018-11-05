Feature: Create customer with invalid data
  Scenario: User calls web service to fill the register form with invalid data and submit it
	Given User open register form on web service
	When user filled the field "id" text: "5bdef552be30096d6c0bcfed"
	And filled the field "first_name" text: "Obrien”
	And filled the field "last_name" text: "Kelly”
	And filled the field "age" text: "23”
    And filled the field "active" text: "true”
    And filled the field "date_of_birth" text: "1997-11-18”
	And press submit
	Then the status code is 401


