# Personal Project

## Project titles: Personal Finance Tracker

### Application purpose
    Personal Finance Tracker is a Java desktop application designed to help individuals 
    manage their personal finances effectively. Users can input and view their income and
    expenses. The application will provide some features such as creating budgets for expense 
    purposes, setting financial goals and generating plus exporting financial reports.

### Target users
    This software is for everyone who wants to take control of their own finances, from those 
    who are just getting started with budgeting to those who want a comprehensive tool to manage 
    their financial goals and analyze their financial data. The program can be customised 
    due to users financial needs that all people can utilize this application.

### Application backgrounds
    Personal money management is an important component of adulthood, and I believe that having 
    a robust, user-friendly tool to assist individuals in making informed financial decisions 
    might considerably improve users financial well-being. As a university student, I do this 
    project because I want to start practically monitoring my financial situation to prepare
    for my bigger financial decisions in the future.


### ***User Stories:***
- As a user, I want to be able to add income sources to my tracker.
- As a user, I want to be able to input and categorize my expenses to my tracker.
- As a user, I want to be able to view income and expense history.
- As a user, I want to be able to create budgets for various expense categories.
- As a user, I want to be able to set and track my financial goals.
- As a user, I want to be able to generate and export my financial data to external tool.
- As a user, I want to be able to save my financial data within the application
- As a user, I want to be able to load my previous financial data within the application

Phase 4: Task 2
Printing all events since the application started:
Fri Dec 01 02:35:00 PST 2023
Added a new transaction: Description: cf, Amount: 5.0, Date: 2023-12-01, Type: EXPENSE
Fri Dec 01 02:35:06 PST 2023
Added a new budget: Category: grocery, Limit: 1000.0
Fri Dec 01 02:35:21 PST 2023
Added a new financial goal: Description: gift, Target Amount: 500.0, Target Date: 2023-12-25, Status: false
Fri Dec 01 02:36:01 PST 2023
Added a new financial goal: Description: ticket, Target Amount: 100.0, Target Date: 2023-12-31, Status: false
Fri Dec 01 02:36:24 PST 2023
Goal 1 is marked as achieved and deleted from tracker.
Fri Dec 01 02:36:50 PST 2023
Added a new financial goal: Description: phone plan, amount: 40.0, Due Date: 2023-12-06, Status: false
Fri Dec 01 02:37:35 PST 2023
Added a new financial goal: Description: tuition, amount: 99999.0, Due Date: 2024-01-10, Status: false
Fri Dec 01 02:37:42 PST 2023
Reminder 1 is marked as achieved and deleted from tracker.
Bye

Phase 4: Task 3
Consider breaking down the FinancialTrackerApp class into smaller classes, each responsible for a specific aspect of the
application (e.g., transactions, budgets, goals, reminders). This will enhance modularity and maintainability. Each class 
can encapsulate related functionality, promoting a clearer separation of concerns.

Furthermore, actionMenu methods in FinancialTrackerApp should be separated into classes because if the menu 
handling becomes more complex, creating a separate Menu class responsible for displaying options and processing user input. 
This adheres to the Single Responsibility Principle. In addition, some of the methods should have helpers method to 
increase readability and maintainability.
