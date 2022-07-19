
For graded optional tasks: Task2.4, Task2.6, Task2.9

## work distribution
- Yung-Jen Yang: Task2.4, Task2.5, Task2.6, Task2.9
- 

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
