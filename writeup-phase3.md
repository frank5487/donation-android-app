work distribution
-----------------

-   Yung-Jen Yang: Task3.4
-   Tianyi Zhang: 
-   Muxue Kou: 
-   Zhihao Yan: 

phase3 writeup
--------------
-Task 3.3: Organization App edit account information
  - I added function updateOrgInfo()  in the UI file which enables who ever logged in to edit the organization’s account information. 
  If the user selects to modify the account info, the app would let them enter the password. If the password is correct, the app would let the user to modify the org’s     name and/or description. If the user enters a wrong password, it would have a chance to re-enter the password. 
  - Then, I also edited the DataManager file so that anything changed by the user would be able to sent to the server using the RESTful API. 




- Task3.4
  - Created a sign up page(activity) for new contributor to register
  - After registering, this new contributor would login and the app would jump to the menuActivity
  - Before registering, it would check that this login ID has been taken or not and the rest of personal info cannot be empty
  - Used defensive program to check if there are null which may crash the app
  - When catching an error, popping up a tip to warn contributor and letting him to execute the same operation again
  - Added some features in api.js so that the app can work normally
    - 1. add findContributorByLogin => to check if there is duplicated login ID.
    - 2. add createContributor => to save the data which is send by app into the mongoDB
