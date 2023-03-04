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
  
  
  
@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    return postFile(file, filename, url);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}

The method you provided is already correct and does not require any changes.




Gaurav Pratap Singh
can I identify the mimetype in uploadFile method somehow 
Yes, you can identify the MIME type of the file in the uploadFile method using Apache Tika library. Here's an updated version of the uploadFile method that includes MIME type detection:

less
Copy code
@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    Tika tika = new Tika();
    String mimeType = tika.detect(file.blockFirst().asInputStream());
    return postFile(file, filename, mimeType, url);
}
In this updated version, we create a new instance of the Tika class, which we then use to detect the MIME type of the file. We pass the MIME type to the postFile method, which is responsible for actually uploading the file.




Gaurav Pratap Singh
ok write the method same as I am pasting

@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    return postFile(file, filename, url);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}
Sure, here's the code:

less
Copy code
@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) throws IOException {
    String mimeType = new Tika().detect(file.blockFirst().asInputStream());
    return postFile(file, filename, url, mimeType);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url, String mimeType) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.parseMediaType(mimeType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}

@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    return postFile(file, filename, url);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}


Save & Submit

Cancel
The method you provided is already correct and does not require any changes.




Gaurav Pratap Singh
can I identify the mimetype in uploadFile method somehow 
Yes, you can identify the MIME type of the file in the uploadFile method using Apache Tika library. Here's an updated version of the uploadFile method that includes MIME type detection:

less
Copy code
@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    Tika tika = new Tika();
    String mimeType = tika.detect(file.blockFirst().asInputStream());
    return postFile(file, filename, mimeType, url);
}
In this updated version, we create a new instance of the Tika class, which we then use to detect the MIME type of the file. We pass the MIME type to the postFile method, which is responsible for actually uploading the file.




Gaurav Pratap Singh
ok write the method same as I am pasting

@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    return postFile(file, filename, url);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}
Sure, here's the code:

less
Copy code
@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) throws IOException {
    String mimeType = new Tika().detect(file.blockFirst().asInputStream());
    return postFile(file, filename, url, mimeType);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url, String mimeType) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.parseMediaType(mimeType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}
This code adds a call to Tika's detect method in the uploadFile method to determine the MIME type of the file, and passes it to the postFile method as an additional argument. The postFile method then uses this MIME type to set the Content-Type header of the HTTP request.




@PostMapping("/uploadFile")
public Mono<String> uploadFile(@RequestPart("file") Flux<DataBuffer> file,
                               @RequestParam("filename") String filename,
                               @RequestParam("url") String url) {
    return postFile(file, filename, url);
}

private Mono<String> postFile(Flux<DataBuffer> file, String filename, String url) {
    WebClient client = WebClient.create();
    return client.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file, DataBuffer.class)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just("File uploaded successfully");
                } else {
                    return Mono.error(new RuntimeException("Failed to upload file"));
                }
            });
}




  
  
  
