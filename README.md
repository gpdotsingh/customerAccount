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
  
Dear Shiva & Ishwarya,
I would like to thank you both for your effort in building team bonding and transparency as chapter leads. I apologize for responding late to your request for feedback.
Here are the points that I would like to address:
1. I suggest explaining the incident in detail to the team, as some members were unaware of the email and the situation.
2. We should encourage the more T-shaped or comb-shaped attribute as a team, as incidents like these can create fear among team members.
3. Task accountability should be improved by reviewing team member work and performing blameless postmortem.
4. We should avoid multiple invalid bugs, as they can be time-consuming and impact our deliverables.
5. Chapter leads must ensure that any email sent contains authentic and proper information. For example, Sushmita sent an email that contained some points that were not proper, and I requested that Shiva respond to the same email to avoid any misunderstandings.
6. If a team member is skipping any team meeting, they must inform the Scrum master at least later, which I found to be a root cause of the incident.
7. Before sending emails, we should ensure that decisions are not forced by one person, as this can create misunderstandings and confusion.
In addition, to provide context, I have enclosed the sequence of events that happened recently:
-  On Wednesday, February 22nd, Sushmita raised a concern that Rama did not complete a story properly.
- On Thursday, February 23rd, I discussed the issue with Rama before refinement, and he mentioned that the creation of the postman suite was not mentioned in the US, which was created by Sushmita.
-  Sushmita skipped the refinement meeting on the same day, so we couldn't have a discussion.
-  On Friday, February 24th, I had a discussion with Sushmita and Rama, and we agreed that I would fix the issues and sync up with Sushmita for 15 minutes to verify the basics.
-  Sushmita refused to come on the call, and I downloaded the collection to set up on my system and found that the build was wrong.
- Sushmita missed the standup on the same day.
- Later, I informed the team about the mistake made by Sushmita, and she raised four new bugs that were later found to be invalid.
I would also like to have a discussion with you, Shiva, regarding the email that Sushmita sent, as you were looped into the same email.
Thank you for your understanding and cooperation.
Best regards,
[Your Name]
