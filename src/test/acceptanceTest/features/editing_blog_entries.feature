Feature: Edit a Blog Entry
  In order to hide from the rest of the world how dumb I am
  As a Blogger
  I want to be able to edit a blog entry

  Scenario: Edit Blog Post
    Given I am logged in as a blogger
    And I have created a blog post
    When I edit my blog post
    Then I am notified that the blog post was successfully edited
    And the edits I have made are saved