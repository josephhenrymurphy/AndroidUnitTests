# AndroidUnitTests

An android calculator application that showcases the following:

* MVP, Dagger2, Retrofit2/OkHttp3
* Developer debug drawer to enable/disable [LeakCanary](https://github.com/square/leakcanary)
* Unit testing with [Robolectric](https://github.com/robolectric/robolectric) and [Mockito](http://mockito.org/)
* Functional UI tests using Espresso and [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
* Code coverage with JaCoCo
* 
### Requirements

This project uses retrolambda for those sweet Java 8 lambda functions and method references.  Thus, you will need to have Java 8 installed on your machine. Set the JAVA8_HOME environment variable, and you should be good to go!

### Testing

#### Unit Tests

The application unit tests are located in [`src/tests`](/app/src/tests), with **debug ** specific tests being located in [`src/testDebug`](/app/src/testDebug). The unit tests check classes in isolation, and mock dependencies.

The unit tests are also all java, so no android emulator or device is needed in order to run the tests.  Android dependencies are mocked with Mockito or Robolectric as needed.

The tests use a [custom test runner](/app/src/test/java/com/mobiquity/androidunittests/CustomGradleRunner.java) to mock some dagger injected dependencies and configure Robolectric. (Ex. For activity tests, presenters are mocked.  We're testing the view in these tests after all, not the underlying backend.)

>The tests can be run using `./gradlew testDebugUnitTest` or `./gradlew testReleaseUnitTest`.  Results are in `app/build/reports/tests`

#### Functional UI Tests

The application functional tests are located in [`src/androidTest`](/app/src/androidTest). These tests simulate user input and verify that the behaviour of the application was as expected.  Espresso is used in order to simulate the user input.  MockWebServer allows for the tests to simulate network calls without calling real services, and control the returned result.

All functional tests need an emulator or device to run.

The tests use a [custom test runner](/app/src/androidTest/java/com/mobiquity/androidunittests/functionaltests/CalculatorFunctionalTestRunner.java) to configure the MockWebServer, mock AndroidDevMetrics, and gain access to methods to control the tests.

The tests also use a [rule](/app/src/androidTest/java/com/mobiquity/androidunittests/functionaltests/rules/DisableAnimationsRule.java) in order to disable animations for instrumented tests. The [espresso gradle file](/codeQuality/espresso.gradle) gives only connected test debug application permission to alter animation settings.

>The tests can be run using `./gradlew connectedDebugAndroidTest`. Results are in `app/build/reports/androidTests`

#### Code Coverage

Jacoco is used in order to view the code coverage of unit tests and is configured at [`codeQuality/jacoco.gradle`](/codeQuality/jacoco.gradle). Jacoco coverage reports can be view in `app/build/reports/jacoco`

>The application has `./gradlew testDebugUnitTestCoverage` for viewing the test coverage of only the unit tests, and `./gradlew fullTestDebugUnitTestCoverage` for viewing the test coverage of both unit and functional tests.

### Details of Implementation

#### Dev Drawer

The `DevModule` and `DevSetingsComponent` are used to provide the dev drawer and tools used by the dev drawer.  The main source code knows little about the developer libraries, and only knows of abstractions whose implementations are provided by the debug and release source sets.

#### Calculator

The `CalculatorActivity` follows a MVP design pattern, and a `CalculatorPresenter` is injected into the activity.  This allows the activity and presenter to be tested separately, and allows the application to follow SOLID principles.

`CalculatorPresenter`: Handles input passed from the UI, and notifies the activity to update with corresponding information.  The presenter uses a `Calculator` and `ExpressionConverter`

`Calculator`: Responsible for taking a list of calculator inputs, and evaluating the list.

`ExpressionConverter`: Responsible for normalizing an expression string, and converting an expression string to a list of calculator inputs.

##### Sources
---
Quality Matters: <a>http://artemzin.com/blog/android-development-culture-the-document-qualitymatters/</a>

Disabling animations in espresso tests: <a>https://product.reverb.com/2015/06/06/disabling-animations-in-espresso-for-android-testing/</a>
