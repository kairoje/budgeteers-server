# Description

Budgeteer is a budget app designed to allow people to take control of their finances. With Budgeteer, users can create custom budget categories and add expenses to these categories, allowing them to  monitor their income, expenses, and financial goals.

# Planning and Approach
- The first day of the project was spent planning. We began by creating user stories, an ERD, and listing our endpoints out. We knew that we wanted to configure our security first before creating our models and classes. Therefore, most of the next day was spent with security configuration together using a driver/navigator method. After configuring our approach to which models to implement, we decided on which roles each team member was going to tackle. The next 2 days were the most challenging days and were spent configuring our test classes and ensuring they ran seamlessly.<br><br>

- To keep merge conflicts minimal, we divided the workload by classes, and each morning we would resolve pull requests and merge conflicts together to ensure we started the day with no merge conflicts. This approach worked in our favor and we were able to keep merge conflicts to a minimum.

# User Stories

## User Authentication
As a user, I want to be able to create an account and log in so that I can access my budgets and financial data.

**Acceptance Criteria:**

1. Users can register with a unique username and a secure password.
2. Registered users can log in using their username and password.

## Managing Budgets
As a user, I want to create budgets so that I can effectively manage my expenses.

**Acceptance Criteria:**

1. Users can create a budget by providing a unique budget name.
2. Budget names must be unique for each user.
3. Users receive a confirmation message upon successful budget creation.

## Budget Details
As a user, I want the ability to update a budget's name or details to ensure accuracy.

**Acceptance Criteria:**

1. Users can edit a budget's name or details.
2. Changes to a budget's name do not conflict with existing budget names.

## Viewing Budget Details
As a user, I want to view the details of a specific budget.

**Acceptance Criteria:**

1. Users can click on a budget to view its details, including its name and associated expenses.
2. Users are presented with an error message if the budget does not exist.

## Listing All Budgets
As a user, I want to see a list of all my budgets for an overview.

**Acceptance Criteria:**

1. Users can access a list of all their budgets.
2. The list displays budget names and, if available, the number of associated expenses.

## Deleting Budgets
As a user, I want the option to delete a budget that I no longer need.

**Acceptance Criteria:**

1. Users can delete a budget.
2. Deletion of a budget should not affect associated expenses.

## Creating an Expense
As a user, I want to create an expense for a specific budget to track my spending.

**Acceptance Criteria:**

1. Users can create an expense for a budget by providing expense details such as name, amount, and description.
2. Users receive a confirmation message upon successful expense creation.
3. The budget's balance decreases by the expense amount when a new expense is added.

## Updating an Expense
As a user, I want the ability to update an expense's details for accuracy, and I want the associated budget's balance to reflect these changes.

**Acceptance Criteria:**

1. Users can edit an expense's name, amount, or description.
2. Changes to an expense's name, amount, or description are saved successfully.
3. The budget's balance decreases or increases based on the changes made to the expense amount. If the expense amount is increased, the budget balance decreases, and if the expense amount is decreased, the budget balance increases.

## Deleting an Expense
As a user, I want the ability to delete an expense, and I want the associated budget's balance to reflect this change.

**Acceptance Criteria:**

1. Users can delete an expense.
2. Deletion of an expense is confirmed with a confirmation message.
3. The budget's balance increases by the deleted expense amount when an expense is successfully deleted.

## Reading All Expenses for a Budget
As a user, I want to view a list of all expenses associated with a specific budget for a comprehensive overview of my spending.

**Acceptance Criteria:**

1. Users can access a list of all expenses associated with a budget.
2. The list displays the names, amounts, and descriptions (if available) of all expenses within the budget.
3. Users are presented with an error message if there are no expenses associated with the budget.



### Budgeteer ERD
![ERD.png](ERD.png)

### Endpoints
![Endpoints](budgeteer-endpoints.png)

# Hurdles
- **Test classes:** Creating our testing classes took us about 4 days (including the weekend). We had a mixture of highly experienced and less experienced teammates, so it was important to allow the less experienced teammates to use this project to strengthen their weaker areas, and to learn to lean on more senior teammates for support and assistance in debugging.<br><br>

- **Merge conflicts:** Throughout the development process, our team encountered merging conflicts. Such conflicts arose when we provided guidance to one another on coding solutions, leading to implementations that occasionally resulted in similar or conflicting code in separate branches. Nevertheless, our well-structured workload distribution and effective collaboration, we promptly resolved these conflicts.<br><br>

- **Model Configuration conflicts:** In accordance with our initial project plan, we contemplated the inclusion of a 'Category' model to serve as a repository for diverse expense types. Our initial design envisioned that expenses would be associated with specific categories, and these categories would, in turn, be linked to individual users. However, following a comprehensive review and based on guidance from our instructors, we made the strategic decision to streamline our data model. Consequently, we opted to eliminate the additional 'Category' model in favor of a simplified architecture. Our revised approach now features an 'Expense' model directly associated with user-specific monthly budget entries, aligning with best practices and ensuring greater efficiency in our application's design. 

# Tools and Technologies Used
- Java
- Springboot
- Postman
- H2
- PostGres
- Drawio
- Spring Initializer

# Dependencies
- [Springboot](https://spring.io/projects/spring-boot)
- [Lombok](https://projectlombok.org/)
- [Mockito](https://site.mockito.org/)
- [JUnit](https://junit.org/junit5/)

# Contributors
- [Kairo Evans](https://github.com/kairoje)
- [Julian Smith](https://github.com/jayastronomic)
- [Elizabeth Yang](https://github.com/lizabawa)



