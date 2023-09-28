# Description

Budgeteer is a budget app designed to allow people to take control of their finances. With Budgeteer, users can create custom budget categories and add expenses to these categories, allowing them to  monitor their income, expenses, and financial goals.

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
- Users can click on a category to view its details, including its name and associated expenses.
- Users are presented with an error message if the category does not exist.

**As a user**, I want to see a list of all my budget categories for an overview.

**Acceptance Criteria:**
- Users can access a list of all their budget categories.
- The list displays category names and, if available, the number of associated expenses.

**As a user**, I want the option to delete a budget category that I no longer need.

**Acceptance Criteria:**
- Users can delete a budget category.
- Deletion of a category should not affect associated expenses.

## Managing expenses

**As a user**, I want to add a budget expense to a specific category to track my expenses accurately.

**Acceptance Criteria:**
- Users can add a budget expense to a category by providing expense details such as name, amount, and description.
- Users receive a confirmation message upon successful expense addition.

**As a user**, I want the ability to update a budget expense's details to ensure they are current.

**Acceptance Criteria:**
- Users can edit a budget expense's name, amount, or description.
- Changes to an expense's name should not conflict with existing expense names within the same category.

**As a user**, I want to view the details of a specific budget expense to track its information.

**Acceptance Criteria:**
- Users can click on an expense to view its details, including name, amount, and description.
- Users are presented with an error message if the expense does not exist.

**As a user**, I want to see a list of all budget expenses within a specific category.

**Acceptance Criteria:**
- Users can access a list of all budget expenses within a category.
- The list displays expense names, amounts, and descriptions, if available.

**As a user**, I want the option to delete a budget expense when it's no longer relevant.

**Acceptance Criteria:**
- Users can delete a budget expense.
- Deletion of an expense should not affect the category or other associated expenses.

# Budgeteer ERD
![ERD.png](ERD.png)



