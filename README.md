# TRMS

## Project Description

Tuition Reimbursement Management System is a full stack java application that allows people to request reinbursements to their employers. An employee can submit a request form and the form will get passed up to 3 people; the Direct Supervisor, Direct Head, and the Benefit Coordinator. It will be passed in sequential order and each previous rank will have to give their stamp of approval or request more information.

## Technologies Used

* Hibernate
* JUnit
* Servlets

## Features

List of features ready and TODOs for future development
* Fully functional login system with cookies.
* Reinbursement form submissions.
* Form filing as well as form denial/approval.

To-do list:
* Allow supervisors to request more information easily.
* Improve on handling user available cash.

## Getting Started
   
> git clone https://github.com/SMoctezuma/TRMS.git
> Open eclipse and run the Servlet on a TomCat Server. 
> Database is hosted on my AWS RDS.

## Usage

> Once you have the server running, open your web browser and navigate to http://localhost:8081/project1/static/
![image](https://user-images.githubusercontent.com/3750077/111560468-8ac97200-8760-11eb-8541-91a14c665cca.png)

> You should then be able to log in using "supervisor@gmail.com" and "test" as password.
![TRMS - Google Chrome 3_17_2021 8_39_09 PM](https://user-images.githubusercontent.com/3750077/111560659-f0b5f980-8760-11eb-9f0e-0b909b2bcb5e.png)


## License

This project uses the following license: [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0).
