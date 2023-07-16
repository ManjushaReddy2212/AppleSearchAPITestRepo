# AppleSearchAPITestRepo
Required installations : Eclipse/Intellij(IDE), Maven, Testng, Git and Rest Assured java based framework

Test Execution Steps:
1. Clone github repo into your local using 'git clone https://github.com/ManjushaReddy2212/AppleSearchAPITestRepo.git'
2. Pull code into your local repository using 'git pull' after setting up required installations
3. Repo has three files under (src/test/java/default package) folder
   AppleSearchAPIAutomation.java - which has 5 test cases to be executed,
   Constants.java - All reusable constants like Base url and api endpoint url's are stored here
   ResponseItemValidator.java - Class to validate api response fields
4. Run the tests in Eclipse using run as-> Test Ng file (or)
5. Go to terminal -> change directory to local repository folder using command cd <folder path> -> Build project using command
   (mvn clean install -Dmaven.test.skip=true -U) -> If the build is sucessful run command (mvn test -Dtest=AppleSearchAPIAutomation) to      run tests
6. Five Test cases will run successfully
   
