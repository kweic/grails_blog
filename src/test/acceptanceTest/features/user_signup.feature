Feature: Users can sign up to use the blog page

  Scenario: Sign up for site
    When I sign up as a new user
    Then I can post blogs
    And comment on blogs