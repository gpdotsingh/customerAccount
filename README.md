# Current account transaction services


Current account transaction services provide feature for end user to get list of existing customers and see and operations(CREDIT /DEBIT) on account and can view transaction history.

These services are developed in two parts
- Customer -Account (Primary Service)
    - Can see list of customers 
    - Can see detail of a customer
    - Can create and do account transactions(credit/debit)
    - Can see a customer account transactions
- Transaction - Account
  - Log transactions 
  - Provide transaction history to customer account service

Customer - Account services call transaction services internally to log transaction. 
In case if transaction-account service is not responding during transaction then logs
will be stored which can later be used to update transaction.

- The base URL for primary service is 'http://localhost:8086/'
- Swagger URL is http://localhost:8086/swagger-ui.html

## Functionality

These services will allow end user to see all the customer list in pageable format.
Customer can have multiple types of account, but each type of account can have unique user.(But in current case only current account is considered)

End user can send request perform below steps to get meaningful result.

- Get customer listing {baseURL}/api/v1/customers/{?pageNo=0&pageSize=10} (*  pageNo=0 and pageSize=10 by default )
- Using customer id, with sample mentioned below url, end user can see customer details ({baseURL}/api/v1/customers/{customerId})
- Customer account API can do transactions for customer, which will create or update existing customer account using URL ({baseURL}/api/v1/accounts/{accountType}/{customerId}?amount={decimalvalue}&transactionType={credit/debit})  
  (* End user can choose to perform transaction using below URL. Transaction can have only two modes CREDIT or DEBIT. Also, transaction can be for only Current account only.)
- User can see customer transactions by opting account type and customer id ({baseURL}/transaction/{Account Type}/{customerId}?pageNo=0&pageSize=10") (*  pageNo=0 and pageSize=10 by default )
## Sample

- For transaction to account(create / update) for customer end user use below URL
  {baseURL}/api/v1/accounts/current/{customerId}?amount={2}&transactionType={credit}

- Using below sample URL end user can see customer transactions using account number of customer.
  /api/v1/transaction/{accountNumber}


#### Note #### 
If a customer doesn't have account, then in case of any credit transaction its account(Not with debit account) will be open.

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

- Have scope to add different type of accounts
- Transaction service is independent of account type.
- Software contain in memory database so after each transaction tester can cross check data also, details for in memory database is below.
- Software have swagger ui so that all rest endpoints related documents can be done easily.
- Software is written while following SOLID principals.
- Follow Sonar coding standards.
- Giving response in pageble format
- Its readable testable and easy to understand code.

### Cons
- Have scope tp provice multiple type of searching option for customer and transaction list
- No security, no proper fallback implemented
- Proper logging need to be implemented
- Scope to add more test scenario need to be included.
- All parameters for request are in header 
  .Have scope to refactor that to put request parameter and params in body.
