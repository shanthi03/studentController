Feature:Student Feature

  Background:
    Given create a student


  Scenario: Verify that a student resource can be updated
    When updating the student
    Then the student is updated

  Scenario: Verify that a student is not able to create with no name
    When creating a student with no name
    Then Name is required error thrown

  Scenario: Verify that a student is not able to create with no age
    When creating a student with no age
    Then age is required error thrown

  Scenario: Verify that a student is not able to create with no email
    When creating a student with no email
    Then Email is required error thrown

  Scenario: Verify that a student is not able to create with  no id
    When creating a student with no id
    Then Internal server error thrown

  Scenario: Verify that a student resource can be updated
    When creating a student
    And updating a student
    Then Student is updated

  Scenario: Verify that a student resource getting the details
    When creating a student
    Then getting a student

  Scenario: Verify that a student resource is deleted
    When creating a student
    Then student deleted

  Scenario: Verify that path param is required
    When creating a student
    And updating without path
    Then Method error thrown

  Scenario: Verify that path param is required in deletion
    When creating a student
    And deleting without path
    Then Method error thrown

