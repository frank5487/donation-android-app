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
  - create a variable cache to memorize the calculated result so that it doesn't need to make request to API server again, which avoid the redundancy requests and improve the efficiency
  - the variable of cache is Map instance where key represents fundID (String type), value represents fundName (String type).

- Task2.5
  - implement the defensive programming 
  - handle these situations related to illegal arguments (null input), wrong response from server (client may be null), wrong format of json
  
- Task2.6
  - create an another page (activity) called AggregateDonationsActivity to show the aggregate results.
  - use cache to store previous result, avoid duplicated calculations. We also need to update our cache when the contributor makes a new donation.
  - the aggregate results is sorted by total donations decreasingly.

- Task2.9
  - I used MD5 to encrypt the contributor's password
  - For Android (Java), I encapsulated the MD5Util (the code I looked up is from Stackflow)
  - For admin.js, I npm install md5 library to encrypt the password
