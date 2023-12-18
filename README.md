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
This projects’s goal is to provide an API to users create their payments and be able to consume it eighter by type or all together.

The chery on pie is that even if the project scales a million times, the performance will remain the same. I'm using Spring Webflux to return my data in all controllers on @Get requests that has "list_flux". Spring WebFlux is a suitable option for applications that require concurrency, efficiency in handling multiple requests with less resources, and scalability. How does it work?

![image](https://github.com/dantelacerda/idempontent-payments/assets/21350677/db789d02-2c1a-4ee1-9f05-212ab55eaf06)
<p align="right">(<a href="#readme-top">back to top</a>)</p>


Besides that, Its easy to keep evolving the project since all the logic used to build to define which type of Payment would be used is defined using a Design Pattern called Strategy! Strategy design pattern is one of the behavioral design pattern. Strategy pattern is used when we have multiple algorithm for a specific task and client decides the actual implementation to be used at runtime. On the incoming example we can see exactly what we would have to do if we wanted to add Bitcoin or Paypal Payment type to our project

![image](https://github.com/dantelacerda/idempontent-payments/assets/21350677/d0e7879b-a87d-4727-9a1f-5d8c007c2c46)

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


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/dantelacerda/
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com
[Java-url]: https://www.java.com/pt-BR/
[Java-badge]: https://www.oracle.com/a/tech/img/rc10-java-badge-3.png
[Springboot-url]: https://spring.io/projects/spring-boot
