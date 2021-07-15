# Current account transaction services


Service provide feature for end user to get list of existing customers and see and operate on account and can view transaction history.

These services are developed in two parts
- Customer -Account (Primary Service)
- Transaction - Account

Customer - Account services call transaction services internally to log transaction. In case if transaction-account service is not responding during transaction then logs will be stored which can later be used to update transaction.
- The base URL for primary service is 'http://localhost:8086/'
- Swagger URL is http://localhost:8086/swagger-ui.html

## Functionality

These services will allow end user to see all the customer list in pageable format.
Customer can have multiple types of account, but each type of account can have unique user.(But in current case only current account is considered)

End user can send request perform below steps to get meaningful result.

- Get customer listing (# "{baseURL}/api/v1/customers/)
- Retrieve customer Id from above step response(# customer1)
- Using customer id with below example url end user can see customer details ({baseURL}/api/v1/customers/{customerId})
- End user can choose to perform transaction using below URL. Transaction can have only two modes CREDIT or DEBIT. Also, transaction can be for only Current account only.
##

- For creation of new account Post can be used for below URL
  {baseURL}/api/v1/accounts/CURRENT/customer1?amount=2&transactionType=CREDIT

- For existing account of new account Post can be used for below URL
  {baseURL}/api/v1/accounts/CURRENT/customer1?amount=21&transactionType=CREDIT

- Using below sample URL end user can see customer transactions using accont number of customer.
  /api/v1/transaction/{accountNumber}

##### Steps
{baseURL}/api/v1/customers/
{baseURL}/api/v1/customers/customer1 (customer1 is id of a customer)
* In case account does not exists it will be created but and amount will be credited, but not in case when transaction type is DEBIT
  {baseURL}/api/v1/accounts/CURRENT/customer1?amount=2&transactionType=CREDIT
*
/api/v1/transaction/{accountNumber}



#### Note #### 
If a customer doesn't have account, then in case of any credit transaction its account will be open.

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven ](https://maven.apache.org)

# Steps to open project
Clone this repo and start application by following below steps
### Build
mvn clean install
### Run integration test
mvn clean verify
### Run
mvn spring-boot:run
### use same command to run both projects
### Pros

- Have scope to add different type of accounts and transaction

- Transaction service is independent of account type.
- Software contain in memory database so after each transaction tester can cross check data also, details for in memory database is below.
- Software have swagger ui so that all rest endpoints related documents can be done easily.
- Software is written while following SOLID principals.
- Follow Sonar coding standards.
- Giving response in pageble format
- Its readable testable and easy to understand code.

### Cons
- Have scope tp provice multiple type of searching option for customer and transaction list
- Software is not tested with all possible boundary conditions.
- Input file format is text it should also have multi value file upload.
- No security, no fallback implemented
- Proper logging need to be implemented
- Scope to add more test scenario need to be included.
- All parameters for request are in header . Have scope to refactor that to put request parameter and params in body. Warm Regards,Gaurav Pratap Singh