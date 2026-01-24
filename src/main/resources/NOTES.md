In order to use multiple models in a single application, you can follow these steps:
1. **Add the Dependencies**: Ensure that you have the necessary dependencies for each model you want to use in your project's build file (e.g., `pom.xml` for Maven or `build.gradle` for Gradle).
2. **Load the Models**: Load each model separately in your application code. This typically involves initializing the model with its configuration and weights.
3. **Create the config bean for each model**: Define a configuration bean for each model to manage its settings and parameters.
4. **Inject the Models**: Use dependency injection to inject the models into the components where they are needed.
5. **Use the Models**: Call the appropriate methods on each model instance as required by your application logic.