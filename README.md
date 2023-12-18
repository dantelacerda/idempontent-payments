<a name="readme-top"></a>

[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Payments</h3>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contact">Contact</a></li>
    
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

That project covers three Payment forms.One for deposits (top-ups),
one for users, one for card payments. With that project you can face the emerging challenge of aggregating and organizing user
activities from different sources.
This projects’s goal is to provide an API to users create their payments and be able to consume it eighter by type or all together. Some important insights about the project will be given on next steps.

- Scalable with Spring Webflux

  
The chery on pie is that even if the project scales a million times, the performance will remain the same. I'm using Spring Webflux to return my data in all controllers on @Get requests that has "list_flux". Spring WebFlux is a suitable option for applications that require concurrency, efficiency in handling multiple requests with less resources, and scalability. How does it work?

![image](https://github.com/dantelacerda/idempontent-payments/assets/21350677/db789d02-2c1a-4ee1-9f05-212ab55eaf06)
<p align="right">(<a href="#readme-top">back to top</a>)</p>


- Easy to aggregate new Payment Types with Strategy Design Pattern


Besides that, Its easy to keep evolving the project since all the logic used to build to define which type of Payment would be used is defined using a Design Pattern called Strategy! Strategy design pattern is one of the behavioral design pattern. Strategy pattern is used when we have multiple algorithm for a specific task and client decides the actual implementation to be used at runtime. On the incoming example we can see exactly what we would have to do if we wanted to add Bitcoin or Paypal Payment type to our project

![image](https://github.com/dantelacerda/idempontent-payments/assets/21350677/d0e7879b-a87d-4727-9a1f-5d8c007c2c46)


- Idempontent Requests


On that project we have a simple Idempontency rule added on creation payments requests. Idempotency in the context of payments refers to the property of an operation where performing the operation multiple times has the same effect as performing it once. In other words, if you submit the same request multiple times, the system's response should be the same as if you had submitted it only once.

The rules that has been added are:
* Client side MUST provide an Idempontent Key passed on @RequestHeader("Idempotency-Key") within that name.
* You can just do your operation once per key. Even though its Batched or Individual payment.
  * If you try sending the same key on other operations, you will receive the message: "Payment(s) already processed."
* If you try sending a different key but your payment informations are the same (just validating the payment_id, deposit_id and transfer_id) you will receive the message: You processed a payment with same informations some time ago. Possibly a Fraud(Gonna add more validations on future)."

Don't forget you can check all about the requests on <a href="#usage">Usage</a> Section!



- Data Storage


To make this project easier to execute and less dependant, I'm not using any database! All the data are being saved on a final Set<Object> managed by a Singleton Instance on a Repository. This way, everytime you run the project, your inserted data is lost. Then its all on runtime! #keepThingSimple
### Built With

* Java
* SpringBoot


<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Java 17
* Maven
* Git
 
### Installation

If you want take a look at mvn commands, go on that link: 
https://jenkov.com/tutorials/maven/maven-commands.html

1. Clone the repo
   ```sh
   git clone git@github.com:dantelacerda/idempontent-payments.git
   ```
2. Execute the Maven command to download dependencies(if you didnt do it before) and execute tests
   ```sh
   mvn clean install
   ```
3. Execute the Project on command line with the below command or use your preffered IDE to see if everything is correct
   ```js
   mvn spring-boot:run
   ```
4. You can check if the project is up by opening Actuator URL on:
http://localhost:8080/actuator/health

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

After starting the project, you can go use postman or cUrl to make requests. On that project you can do 5 different Set of Operations:

* Credit Card Operations
  * Execute a Payment by posting a request on http://localhost:8080/credit_card/pay
  * Execute Payments in batch by posting a request on http://localhost:8080/credit_card/batch_payment
  * List all your payments by that type on http://localhost:8080/credit_card/list
  * List all your payments by that type ASYNC ON http://localhost:8080/credit_card/list_flux
* Deposit Operations
  * same operations as above but changing credit_card for deposit  
* P2P Operations
  * same operations as above but changing deposit for p2p
* Reconciliate
  * Reconciliate your payments by sending a CSV file with data separated by "|" and being read by lines. That operation is on: http://localhost:8080/reconciliate/file
    * Note 1: This Reconciliate method only check if the payments sent on file has been already inserted. In case it doesn't, it removes from system.
    * Note 2: The Reconciliation File was just programmed to read the first two fields, being the First the payment_id and the second is the payment_type. Example:   1234|DEPOSIT|YOU_CAN_ADD_MORE_FIELDS_BUT_WILL_BE_IGNORED
* Full Payment Operations
  * Here you can list all the payments that has been saved on  http://localhost:8080/payments/list_full
  * You can also get a resume of payments with only common fields between all payments on http://localhost:8080/payments/list_resume
  * List all your payments resume, with only common fields but ASYNC ON http://localhost:8080/payments/list_flux


_For full examples, you can run the application and check on [Swagger](http://localhost:8080/swagger-ui/index.html)_


Those are some examples of body to be used on requests:


{
"payment_id": "8c051707aadd416d8e7e094971e395c0",
"card_id": 12341234,
"user_id": 113411,
"payment_amount": 10,
"payment_currency": "EUR",
"status": "COMPLETED",
"created_at": "2023-12-14 13:37:31",
"merchant_name": "TFL*LONDON",
"merchant_id": 12309,
"mcc_code": 7399
}

Deposit
{
"deposit_id": "071d500bf9d74c72a963d77d2b9a0107",
"user_id": 113411,
"deposit_amount": 12.99,
"deposit_currency": "GBP",
"status": "PENDING",
"created_at": "2023-12-02 13:54:30",
"expires_at": "2023-12-04 13:54:30",
"payment_method_code": "BANK_TRANSFER"
}

P2P Transfer
{
"transfer_id": "7bec23e832ee41bfa0d59497ffccc553",
"sender_id":"113411",
"recipient_id":"113412",
"transfer_amount": 15,
"transfer_currency": "EUR",
"status":"COMPLETED",
"comment":"Dinner party at Richard’s",
"created_at": "2023-12-11 13:38:16"
}
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add Reconciliation Controller By File
- [ ] Add Rules to Reconciliation
- [ ] Add a DLQ to Reproccess Payments
- [ ] Multi-language Support
    - [ ] Portuguese
    - [ ] Spanish

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Dante Lacerda - [Linkedin](https://www.linkedin.com/in/dantelacerda/) - dantelacerda01@gmail.com

Project Link: [https://github.com/dantelacerda/idempontent-payments](https://github.com/dantelacerda/idempontent-payments/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/dantelacerda/
[Java-url]: https://www.java.com/pt-BR/
[Java-badge]: https://www.oracle.com/a/tech/img/rc10-java-badge-3.png
[Springboot-url]: https://spring.io/projects/spring-boot
