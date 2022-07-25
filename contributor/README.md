# cis573-project-contributor

## phase1 writeup
- Task1.4: 
  - fixed the bug in the makeDonation method.
    - we should check the amount is greater than zero and its type is Integer.
  - covered all statements

- Task1.5: 
  - calculated the total donations of contributor 
  - displayed it in the end of row of ListView component.

- Task1.6
  - handle error in the MakeDonationActivity
    - prompts the user to re-enter the value when they have invalid input
     
- Task1.8:
  - changed date format

## phase2 writeup
- Task2.4
  - Create a variable cache to memorize the calculated result so that it doesn't need to make request to API server again, which avoid the redundancy requests and improve the efficiency
  - The variable of cache is Map instance where key represents fundID (String type), value represents fundName (String type).

- Task2.5
  - Implement the defensive programming 
  - Handle these situations related to illegal arguments (null input), wrong response from server (client may be null), wrong format of json
  - Pop up a Toast to warn people that this operation is not valid (such as invalid inputs, server errors...)
  
- Task2.6
  - Create an another page (activity) called AggregateDonationsActivity to show the aggregate results.
  - Use cache to store previous result, avoid duplicated calculations. We also need to update our cache when the contributor makes a new donation.
  - The aggregate results is sorted by total donations decreasingly.

- Task2.9
  - I used MD5 to encrypt the contributor's password
  - For Android (Java), I encapsulated the MD5Util (the code I looked up is from Stackflow)
  - For admin.js, I npm install md5 library to encrypt the password

## phase3 writeup
- Task3.4
  - Created a sign up page(activity) for new contributor to register
  - After registering, this new contributor would login and the app would jump to the menuActivity
  - Before registering, it would check that this login ID has been taken or not and the rest of personal info cannot be empty
  - Used defensive program to check if there are null which may crash the app
  - When catching an error, popping up a tip to warn contributor and letting him to execute the same operation again
  - Added some features in api.js so that the app can work normally
    - 1. add findContributorByLogin => to check if there is duplicated login ID.
    - 2. add createContributor => to save the data which is send by app into the mongoDB