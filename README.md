# Java Programming Trial Task

This document provides instructions on how to start the Spring Boot application.

## Prerequisites

1. **Maven** and **JDK 21+** are required to build and run the application.

## Startup Instructions

1. **Navigate to the project directory** where the `pom.xml` file is located.
2. **Run the Spring Boot application** using Maven:

    ```bash
    ./mvnw spring-boot:run
    ```
   
   This will start the Spring Boot application on the default port `8080`.

## Verify the Application

Once the Spring Boot application is running:

- Access the application via `http://localhost:8080/delivery-fee` in your web browser.
- Verify that you can connect to the H2 database by visiting `http://localhost:8080/h2-console` in your web browser.
    - Use the following credentials:
        - **JDBC URL**: `jdbc:h2:mem:food_delivery`
        - **Username**: `sa`
        - **Password**: `password`
- The `weather` table is initially empty and gets populated by a configurable CronJob that fetches data 
  from the Estonian Environment Agency every hour at `HH:15:00` by default.

## Example Request

You can test the delivery fee calculation by making the following request:

`http://localhost:8080/delivery-fee?city=TALLINN&vehicleType=SCOOTER&datetime=2025-03-19 01:23:00`


### Request Parameters:

- **`city`**: The city where the delivery takes place. Supported values: `TALLINN`, `TARTU`, `PÄRNU`.
- **`vehicleType`**: The type of vehicle used for delivery. Supported values: `CAR`, `SCOOTER`, `BIKE`.
- **`datetime`**: The date and time of the delivery request in the format `yyyy-MM-dd HH:mm:ss`.

## Configuration

The application allows configuring weather data fetching through the following parameters:

| Parameter          | Description                                                      | Example Value                                                   |
|--------------------|------------------------------------------------------------------|-----------------------------------------------------------------|
| `weather.url`      | URL of the Estonian Environment Agency weather data API.         | `https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php` |
| `weather.cron`     | Cron expression defining the schedule for fetching weather data. | `0 15 * * * *` (Runs every hour at `HH:15:00`)                  |
| `weather.stations` | List of weather stations to fetch data from.                     | `["Tallinn-Harku", "Tartu-Tõravere", "Pärnu"]`                  |

## Run Tests
1. **Navigate to the project directory** where the `pom.xml` file is located.
2. Run tests

    ```bash
    mvn test
    ```