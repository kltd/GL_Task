Feature: Get customer by invalid id
  Scenario: User calls web service to get a customer by its id
	Given open customer search page
	When customer filled search field: 7A6k2
	And with invalid data press search
	Then the status code is 404