cis573-project-org
==================

Task 1.1

create files:

DataManager_createFund_Test; DataManager_getContributorName_Test;
DataManager_attemptLogin_Test

 

tests for each function:

file: DataManager_createFund_Test:

test1: testSuccessfulCreation \#test a successful fund creation
(status==“success”)

test2: testFailedCreation \#test a failed creation (status==“error”), e.g. empty
id.

test3: testNegativeTargetCreation \#test an invalid target input (target \<= 0)

test4: testExceptionErrorInCreation \#test exception error e.g. null input

file: DataManager_getContributorName_Test:

test1: testSuccessfulGetContributorName \#test a successful name retrieval
(status==“success”)

test2: testFailedGetContributorName \#test a failed retrieval (status==“not
found”), e.g. wrong id.

test3: testErrorGetContributorName \#test an error retrieval (status==“error”),
e.g. wrong id.

test4: testExceptionErrorInGetContributorName \#test exception error e.g. null
input

file: DataManager_attemptLogin_Test:

test1: testSuccessfulLogin\#test a successful login and data retrieval
(status==“success”)

test2: testFailedLogin \#test a failed login (status==“login failed”), e.g. id
and password mismatched.

test3: testErrorLogin \#test an error login (status==“error”), e.g. wrong id.

test4: testExceptionErrorInLogin \#test exception error e.g. null input

 

Task 1.2

bugs in each function:

function: createFund()

bug1: no restriction in target -\> fix: target should \>0, otherwise return
null/(IllegalArgument)

function: getContributorName()

bug1: no distinction on status==“not found” and “error” -\> fix: give different
instructions for the two

function: attemptLogin()

bug1: no distinction on status==“login failed” and “error” -\> fix: give
different instructions for the two

bug2: no restriction on fundId, name, contributorName, target, amount -\> fix:
assertions on those variables. The first three should be not null, and the last
two should be greater than 0.

Task 1.7 
bug1: need while loop instead of if so that wrong input get a chance to re-enter 

Task 1.8
bug1: need try.catch method to avoid bad date format, for any unregonized input return null for this function 


 

Contributions:

Tianyi Zhang: Task1.1 and Task1.2
Zhihao Yan: Task 1.7 and Task 1.8
