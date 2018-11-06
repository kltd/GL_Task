Feature: Get customer by valid id
  Scenario: User calls web service to get a customer by its id
	Given open customer search page
	When customer filled search field: 78652
	And press search
	Then the status code is 200