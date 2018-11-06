Feature: Get customer by valid id below in table
  Scenario: User calls web service to get a customer by its id
	Given open customer search page
	When customer filled search field from table below
	| 12345 |
	| 77834 |
	| 51934 |
	And press search
	Then the status code is 200

