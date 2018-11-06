Feature: Create customer with invalid data
  Scenario: User calls web service to fill the register form with valid data and submit it
	Given User open register form on web service
	When user filled the field id with input text: 1232
	And filled the field first name with input text: Jack
	And filled the field last name with input text: Reacher
	And filled the field age with input text:
    And filled the field active with input text: true
    And filled the field date of birth with input text: 1984-06-29
	And with invalid data press submit
	Then the status code is 401

