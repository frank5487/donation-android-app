work distribution
-----------------

-   Yung-Jen Yang: Task3.4

-   Tianyi Zhang: Task3.1

-   Muxue Kou: Task 3.3

-   Zhihao Yan:Task 3.2

phase3 writeup
--------------

-   Task3.4

    -   Created a sign up page(activity) for new contributor to register

    -   After registering, this new contributor would login and the app would
        jump to the menuActivity

    -   Before registering, it would check that this login ID has been taken or
        not and the rest of personal info cannot be empty

    -   Used defensive program to check if there are null which may crash the
        app

    -   When catching an error, popping up a tip to warn contributor and letting
        him to execute the same operation again

    -   Added some features in api.js so that the app can work normally

        -   add findContributorByLogin =\> to check if there is duplicated login
            ID.

        -   add createContributor =\> to save the data which is send by app into
            the mongoDB

         

-   Task3.1

-   Modified file: api.js

-   Modified function: add ‘/createOrg’:

    1.  Pass the createOrg data between java application and database;

    2.  Check the duplication of login name;

    3.  Give responses based on the result (success, error, or name already
        exists)

 

-   Modified file: Userinterface

-   Modified functions: add chooseLoginOrCreate() and createOrg() function

    modify main, Interface constructor

    1.  Ask users to login or create a new account at the beginning;

    2.  If creating a new account, ask for login, password, organization,
        description and check validity (not null and not too long);

    3.  If success, ask to create a new fund;

 

-   Modified file: DataManager

-   Modified function: add createOrg() function

    1.  Communicate with API through makeRequest;

    2.  Create new organization if status is success;

    3.  Ask to re-create organization if status is duplicate login name;

    4.  Deal with null value or other errors in processing data;

 

-   Modified file: Create DataManager_createOrg_Test.java
-  Task 3.3: Organization App edit account information
I added function updateOrgInfo()  in the UI file which enables who ever logged in to edit the organization’s account information. 
If the user selects to modify the account info, the app would let them enter the password. If the password is correct, the app would let the user to modify the org’s name and/or description. If the user enters a wrong password, it would have a chance to re-enter the password. 
Then, I also edited the DataManager file so that anything changed by the user would be able to sent to the server using the RESTful API. 

-Task3.2， it enables user to securely changing password, with correctly input current password twice, the system allow the users to cahnge the password, otherwise meesage are sent that it is unsecure.
