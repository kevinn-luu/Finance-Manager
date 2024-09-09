# My Personal Project

## Project Proposal
The application I plan to produce for my term project is some sort of **personal finance manager**. The finance manager will help any user keep track of their expenditures and earnings over time. This application can be used by any user; however, I think it would be most useful for University students.

When I was growing up, I didn't have to worry about buying things for myself since I could always rely on my parents. They would get groceries every week along with all the other household essentials. As an outcome, I was pretty blind to how much money was actually being spent. When I started University and moved away from home, I became a lot more conscientious about money since spending became a lot more independent. I also became a lot more anxious about buying things for myself. Building a finance application could be of great use to me, as I would be able to personally track my daily expenditures. I could also be able to review how much money I spent on different things for the past month, or the past year. I want to set a general goal for myself in terms of the money I spend every month and this finance manager would give me a very clear idea of my spending habits.

I hope that my application can also help other students better understand their spending habits.    

## User Stories
- I want to be able to track both my spendings and my earnings
- I want to be able to edit each expense 
- I want the option to delete expenses and earnings
- I want to be able to view all my expenses on a given date and the total
- I want to be able to view all my expenses for a given month and the total
- I want to be able to view all my earnings for a given month and the total
- I want to be able to set a monthly spending goal and see how my current spending compares to it
- I want to be able to save all my expenses added so far
- I want to be able to save my monthly goal and load it from file
- I want to be able to load all my saved expenses from file

## Phase 4: Task 2
Tue Aug 06 15:47:47 PDT 2024 Expenses read from file.

Tue Aug 06 15:47:52 PDT 2024 Expenses have been sorted by date.

Tue Aug 06 15:47:57 PDT 2024 Expenses have been sorted by month

Tue Aug 06 15:48:10 PDT 2024 Expense has been added.

Tue Aug 06 15:48:17 PDT 2024 Expense has been removed.

Tue Aug 06 15:48:23 PDT 2024 Monthly goal has been loaded from file.

Tue Aug 06 15:48:23 PDT 2024 Monthly goal has been updated.

Tue Aug 06 15:48:37 PDT 2024 Monthly goal has been updated.

Tue Aug 06 15:48:40 PDT 2024 Monthly goal has been saved to file.

## Phase 4: Task 3
I would first remove the association between my **MonthlyExpenseGoal** class and my **Expense** class. Currently, I have a collection of expenses as a field inside **MonthlyExpenseGoal** called **current**, however, when designing the project, I found this field to be redundant since the **FincanceManager** already has a collection of expenses. When displaying expenses for the monthly goal,I found that it was much easier to just pull out a new list of all expenses from month X from expenses in the **FinanceManager** - I never actually made use of **current**. Removing this association would also remove unecessary coupling between my classes.

Next, I would refactor my GUI design to make use of the **FinanceManager** class instead of separately using **List<Expense>** and **MonthlyExpenseGoal** fields. I would just condense those two fields into one field of type **FinanceManager**. Interestingly, I realized that this was a much better choie of design when I was building **FinanceManagerGUI**. As seen on the UML diagram, **FinanceManagerGUI** makes use of the **FinanceManager** class but **FinanceManagerConsole** doesn't.

Finally, I would refactor the code inside **FinanceManagerGUI**. I think I would extract different parts of the application to be different classes instead of handling everything in one class. When I was designing the GUI, I found that I had a lot of methods and panels for different parts of the application all in one class. It was really hard to focus on specific functionalities. For example, I wanted to update the way expenses were displayed, but that functionality was handled by multiple methods in my program that weren't organized in one location. I think it would improve readability and debugging if I made different classes for specific components of my GUI, then use those classes in **FinanceManagerGUI**.