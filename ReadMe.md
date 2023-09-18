# Rabobank Customer Statement Processor

## Table of Contents
- [Introduction](#introduction)
- [Project Overview](#project-overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
- [Usage](#usage)
- [Input Data Format](#input-data-format)
- [Output](#output)


## Introduction

The Rabobank Customer Statement Processor is a Spring Boot application designed to process monthly customer statement 
records provided in CSV and XML formats.
It performs validations on the input data, such as ensuring unique transaction references and validating end balances.

## Project Overview

Rabobank receives monthly deliveries of customer statement records in CSV and XML formats. Each record includes fields 
like transaction reference, account number, start balance, mutation, description, and end balance.
The primary goal of this project is to validate these records.

### Getting Started

#### Prerequisites

Before running the application, make sure you have the following prerequisites installed:

- Docker()
- Browser(Chrome, Safari, Mozilla)

#### Running the Application

To run the application, you can use the provided `application.sh` script. This script performs the following steps:

1. Stops and removes any existing containers with the name "application-demo-container."

2. Removes the old "application-demo" Docker image if it exists.

3. Builds a Docker image tagged as "application-demo."

4. Runs a container from the "application-demo" image, mapping port 8070.

5. Sleeps for a brief moment to ensure the application has started.



  ``` bash ./application.sh```


## Usage
The application provides an interface for processing and validating customer statement records. 
You can access the Swagger UI documentation by opening the following URL in your web browser:

[Swagger UI](http://localhost:8070/swagger-ui/index.html)

In Swagger UI, you can access the API below the 'bank-statement-controller' section. You can use this API to post monthly bank statements in either CSV or XML format and view the results.

## Input Data Format
The input data is provided in a simplified version of the MT940 format which can upload as SCV or XML format file.
Each record includes the following fields:

- Transaction reference: A numeric value.
- Account number: An IBAN.
- Start balance: The starting balance in Euros.
- Mutation: Either an addition (+) or a deduction (-).
- Description: Free text.
- End balance: The end balance in Euros.

## Output
The application performs two validations:

1. **Unique Transaction References**: The application ensures that all transaction references are unique.

2. **End Balance Validation**: The end balance is validated to ensure it matches the calculated balance based on the


So, The API will return a report containing repetitive statements and invalid items reported as a JSON object, as shown below:
```agsl
{
[
{
    "reference": "112806",
    "description": "The transaction 112806 failed due to duplication."
  },
  {
    "reference": "192480",
    "description": "The transaction 192480 failed: The end balance is not as expected after the mutation."
  }
]
}
```

## Validation Rules
1. **Unique Transaction References**: The application ensures that all transaction references are unique.

2. **End Balance Validation**: The end balance is validated to ensure it matches the calculated balance based on the start balance and mutations.

