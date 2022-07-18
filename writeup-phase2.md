
For graded optional tasks: 

## work distribution
- Yung-Jen Yang: Task2.4, Task2.5, Task2.6
- 

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