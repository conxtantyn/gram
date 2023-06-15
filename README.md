## Assessment Project

This project is an assessment that demonstrates the implementation of a mobile application using a clean architecture approach and incorporating the Android Navigation library with the Uber RIBs architecture design. It includes two flavors, a mock flavor for testing without an API key and a production flavor for the final application release. Additionally, the project includes thoroughly tested business logic and smoke instrumented tests.

### Clean Architecture

The project follows the principles of clean architecture, which emphasizes the separation of concerns and the independence of layers. The architecture is organized into three main layers:

1. **Domain Layer**: Contains the core business logic and entities of the application. It is independent of any framework or platform-specific implementation.

2. **Data Layer**: Handles data retrieval and storage, including network requests, local database access, or any other data sources. This layer communicates with the domain layer via interfaces or use case contracts.

3. **Presentation Layer**: Implements the user interface and handles user interactions. It relies on the domain layer to perform business logic operations and uses the data layer for data retrieval. The Android Navigation library is used for handling navigation within the application, providing a seamless user experience.

### Uber RIBs Architecture Design

The project utilizes the Uber RIBs architecture design as the basis for structuring the application. However, instead of using the built-in routing mechanism provided by RIBs, the Android Navigation library is integrated. This allows for a more consistent and familiar navigation experience leveraging the capabilities of the Navigation Component.

### Flavors

The assessment project consists of two flavors: `mock` and `production`.

- **Mock Flavor**: This flavor is intended for testing purposes without requiring a valid API key. It simulates the API responses and allows for comprehensive testing of the application's functionality.

- **Production Flavor**: This flavor represents the final application release and should be used when deploying the application with a valid API key.

To switch between flavors, configure the build variant in the Gradle configuration or using the IDE's build variant settings.

### Testing

The assessment project includes extensive testing to ensure the correctness and robustness of the codebase.

- **Business Logic Tests**: The business logic is thoroughly tested using unit tests. These tests validate the functionality of the core business logic and use case implementations.

- **Smoke Instrumented Tests**: Smoke tests are included to validate the overall functionality and integration of the application's components. These tests run on an Android device or emulator and simulate user interactions to verify the behavior of the application.

To run the tests, execute the respective Gradle tasks or use the IDE's testing capabilities.
