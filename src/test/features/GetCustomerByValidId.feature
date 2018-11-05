Feature: Get customer by valid id
  Scenario: User calls web service to get a customer by its id
	Given a customer exists with an id of 123456
	When a user retrieves the customer by id
	Then the status code should be 201
