# AndroidUnitTests
This is a sample project to demonstrate, how to create and run unit tests.

## How to Run unit tests?
The first thing we should do is change to the Project perspective in the Project Window. This will show us a full view of everything contained in the project. The default setting (theAndroid perspective) hides certain directories (including the unit tests).  
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/projectOverview.png" width="300" height="400"/>
</br> 
Next, open the Build Variants window and set the Test Artifact to Unit Tests. Without this, our unit tests won't be included in the build.
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/BuildVariant.png" width="300" height="400"/>
</br>
Creating a new project will automatically create a directory for our unit tests at src/test/java directory to the project. This is the default location for unit tests.
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/testClasses.png" width="500" height="600"/>
</br>
Run a single test through Android Studio, Right click on the test class and select Run
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/RunUnitTest.png" width="400" height="500"/>
</br>
View the results in the Console output. You may need to enable Show Passed.
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/showtestpassed.png" width="800" height="500"/>
</br>
Run all the tests through Gradle: We can run the tests either through Android Studio IDE or through CommandLine. 
Through command line we can run as or Gradle:
```groovy
./gradlew clean test
```</br>
Open the Gradle window and find testDebugUnitTest under Tasks =>verification and double click it to run.
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/rununittests.png" width="500" height="500"/>
</br>
This will Generate an html result report at app/build/reports/tests/debug/index.html
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/unittestreports.png" width="800" height="500"/>
</br>
</br>

### Generating JaCoCo reports
JaCoCo reports can be generated either by using gradle task in IDE or through Command line
Through command line we can run as 
```groovy
./gradlew clean testDebugUnitTestCoverage
```
Open the Gradle window and find testDebugUnitTestCoverage under Tasks =>reporting and double click it to run.
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/rundebugjacocoreports.png" width="400" height="500"/>
</br>
This will Generate an html result report at app/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html
<img src="https://raw.githubusercontent.com/Mobiquity/AndroidUnitTests/ImplemetJaCoCoReporting/jacocoreportswithcoverage.png" width="800" height="500"/>
</br>