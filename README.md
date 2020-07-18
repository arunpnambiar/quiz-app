# quiz-app (React JS and Spring Boot)

# quiz-app Login
![login](https://user-images.githubusercontent.com/20945050/87860412-e84fb980-c95a-11ea-83d5-541447cd223f.png)

# quiz-app Dashboard
![dashboard](https://user-images.githubusercontent.com/20945050/87860372-a6267800-c95a-11ea-88f1-3b7d4d3f5aa9.png)

# quiz-app End page
![lastpage](https://user-images.githubusercontent.com/20945050/87860433-16cd9480-c95b-11ea-8d16-9d7e97e112b9.png)


At the end of submit from backend it will calculate the total percentage of mark and shows whether the person passed or not. we can also retry the exam again.

# quiz-app Back-end
   Backend writtern in spring boot rest API. In backend we are using open csv dependency for reading and writing data from file. when a person login, if the person is not registered then data will save to login.csv file. If user already exist then validation will trigger. In bakend background there is a scheduler running every 30 minutes. It will check the file duration. If file duration greater than 30 minutes scheduler will delete the file. These file is used for login person exam answer sheet. when the persoon submit from UI the value will check with the answer sheet. If file not present in the folder then we will send a validation that exam completed. 

