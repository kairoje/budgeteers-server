# Description

Budgeteer is a budget app designed to allow people to take control of their finances. With Budgeteer, users can create custom budget categories and add items to these categories, allowing them to  monitor their income, expenses, and financial goals.

# User Stories

## User Authentication

**As a user**, I want to be able to create an account and log in so that I can access my budget and financial data.

**Acceptance Criteria:**
- Users can register with a unique username and a secure password.
- Registered users can log in using their username and password.

## Managing Categories

**As a user**, I want to create a budget category so that I can categorize my expenses effectively.

**Acceptance Criteria:**
- Users can create a budget category by providing a unique category name.
- Category names must be unique for each user.
- Users receive a confirmation message upon successful category creation.

**As a user**, I want the ability to update a budget category's name or details, ensuring accuracy.

**Acceptance Criteria:**
- Users can edit a budget category's name or details.
- Changes to a category's name do not conflict with existing category names.

**As a user**, I want to view the details of a specific budget category.

**Acceptance Criteria:**
- Users can click on a category to view its details, including its name and associated items.
- Users are presented with an error message if the category does not exist.

**As a user**, I want to see a list of all my budget categories for an overview.

**Acceptance Criteria:**
- Users can access a list of all their budget categories.
- The list displays category names and, if available, the number of associated items.

**As a user**, I want the option to delete a budget category that I no longer need.

**Acceptance Criteria:**
- Users can delete a budget category.
- Deletion of a category should not affect associated items.

## Managing Items

**As a user**, I want to add a budget item to a specific category to track my expenses accurately.

**Acceptance Criteria:**
- Users can add a budget item to a category by providing item details such as name, amount, and description.
- Users receive a confirmation message upon successful item addition.

**As a user**, I want the ability to update a budget item's details to ensure they are current.

**Acceptance Criteria:**
- Users can edit a budget item's name, amount, or description.
- Changes to an item's name should not conflict with existing item names within the same category.

**As a user**, I want to view the details of a specific budget item to track its information.

**Acceptance Criteria:**
- Users can click on an item to view its details, including name, amount, and description.
- Users are presented with an error message if the item does not exist.

**As a user**, I want to see a list of all budget items within a specific category.

**Acceptance Criteria:**
- Users can access a list of all budget items within a category.
- The list displays item names, amounts, and descriptions, if available.

**As a user**, I want the option to delete a budget item when it's no longer relevant.

**Acceptance Criteria:**
- Users can delete a budget item.
- Deletion of an item should not affect the category or other associated items.

# Budgeteer ERD
![ERD.png](ERD.png)



