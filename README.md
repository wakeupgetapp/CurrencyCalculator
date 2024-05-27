# Currency Calculator
![overview](https://github.com/wakeupgetapp/CurrencyCalculator/assets/97065748/c10c6124-a0c6-465c-b665-c218e0708c54)

## Overview
- This application is designed for currency conversion. Data is updated each time the app is launched.
- Users can choose from over 120 currencies.
- The app features currency search, saving recently used currencies
- Users can also input their own exchange rates (e.g., when a currency was purchased at a specific rate).

## Architecture overview
- The application is built following the **Clean Architecture** principles from Google. \
It maintains clear **separations of concerns** (UI, Domain, Data).
- App is **fully modularized**.
- The application utilizes the **MVVM** architecture for efficient state management.
- It leverages **StateFlows** to ensure smooth data flows between the layers of the application.

#### Data managment
- Data is fetched from a remote API using **Retrofit** and saved to the **Room** database, which serves as the single source of truth. App has fallback mechanism if remote API is not available. 
- Currencies selected by user are saved in cache managed by **Datastore**.

#### UI
- The Screens and UI elements are built entirely using **Compose UI**.
- Some components utilize **Compose Animations**.
- The app leverages the **Material3** library for its design system.

## Tests
- Application tests primarily rely on **Fakes**.
- For testing the data-handling process from the network, the application utilizes **MockWebServer**
- The testing of DAOs is conducted using an in-memory database.
- In a minority of tests, **MockK** is utilized.
- UI Tests - WIP

## Technologies overview:
- Dagger/Hilt
- Room
- Retrofit2
- Datastore
- Compose
- Coroutines
- MockWebServer, MockK

