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


--------------------------------------------------------



Dear Shiva & Ishwarya,

First of all I would like to thanks to both of you that you both came forward as chapter lead try to build team bonding and bring some more transparency inside team.
As asked by both of you for feedback I am writing this mail, enclosing the events that happened and as a Scrum master I told you verbally earlier.

I really appreciate your effort at the same time I am sorry to respond so late, as I was involved in some other tasks and the whole mail contains so many things even after making it shorter it’s way to huge. 


-  I would suggest that you explain the incident in detail to the team as some members were unaware of the email and the situation. 
- As a team we should encourage the more T shaped or comb shaped attribute which it seems that these type of incidents put some fear among the team member.
- Accountability of task is very much necessary but that must be improved by reviewing team member works and performing blameless postmortem.
-  We should avoid multiple invalid bugs as it can be time-consuming and impact our deliverables.
- At the same time I expect you as a Chapterlead if any of us is dropping a mail must contain authentic and proper information which I saw to be overlooked when Sushmita dropped mail looping Shiva where few of the points are were not a single person decision and I raised the same point as well to Shiva.
- At the same time if team member is skipping any team meeting he/she must inform the scrum master at least later which I found one of the root cause here also.
- I believe that before sending emails, we should ensure that decisions are not forced by one person, as this can create misunderstandings and confusion. For example, Sushmita sent an email that contained some points that were not proper, and I requested that Shiva respond to the same email to avoid any misunderstandings. 

Furthermore, . Lastly, if someone cannot attend team meetings, they should inform the Scrum master in advance to prevent any issues.
To provide context, I have also enclosed the events that happened recently. 

Sequence of events:

- Sushmita raised a concern on Wednesday, February 22th, that Rama did not complete a story properly. 

- However, as the Scrum Master, I was busy with other tasks, including preparing the RCA of production issues and their discussions. 
- On Thursday, February 23th, I discussed the issue with Rama before refinement, and he mentioned that the creation of the postman suite was not mentioned in the US, which was created by Sushmita.
- Same day i thought to have discussion in starting time the during refinement which we use for sharing the daily status also sometimes, but Sushmita skipped the refinement due to unknown reasons, so discussion didn't took place.
- After explaining to Rama that in testing user story normally we create and maintain the Postman test suite, he get agreed to do the same, but as he was the ops of the sprint so have to consider the workload as well .
- On Friday 24 Feb 2023, as  Rama was doing the testing User story first time, keeping these things in my mind I started having the discussion same with Sushmita and Rama , where Ram was also pulled in same discussion by Sushmita and there we agreed that i will fix the issues and for same I will connect with Sushmita for 15 mins so that I can sync up with the test cases and setup and can verify the basics.
- After some time I tried to connect to Sushmita , she refused to come on the call stating that she explained it 3 times to Rama and he made these mistake, so considering all points I suggested let's not take risk and request to connect to her over call and can see what all bugs are and at the same time I can use the same test suite what Sushmita is referring and can set up in my system and than can do the testing maybe on my local and can fix the bugs quickly and easily.
- Had chat with Sushmita where finally she refused to come on call, later I download the collection setup it on my system and involve Rama and found that build was wrong, the build tagged in the previous user story was not picked and deployed by Sushmita.
- At the same day we had standup where Sushmita missed that too.
- Later I informed about the mistake made by Sushmita, so on the same old bug she deleted the content and raise the new bug, and after same she raised other three bugs so in total it was 4 new bugs.
- As a team and individual also we spend time to give answers of each bug  and check the test cases which cost a lot of time, and found out later that all the bugs were invalid.
- Also she dropped the mail to team which I found to be not unto the mark , on the same I would like to have discussion with you Shiva, because you are looped into the same mail and I expect some response from you as well to understand below points
    - “”
    - “”

End of event

- This process cost us a lot of time, and Sushmita's email to the team was inappropriate, as it seemed to force certain things on the team.
Thank you for taking the time to read my email. I hope we can work together to improve our team's performance and avoid such incidents in the future.
Best regards,
[Your Name]
