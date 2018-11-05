Feature: Create customer with invalid data
  Scenario: User calls web service to fill the register form with invalid data and submit it
	Given User open register form on web service
	When user filled the field id with input text: Lasd
	And filled the field first name with input text: Shak
	And filled the field last name with input text: Reacher
	And filled the field age with input text: go
    And filled the field active with input text: true
    And filled the field date of birth with input text: 1978-06-29
	And press submit
	Then the status code is 401

