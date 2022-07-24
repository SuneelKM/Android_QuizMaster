
# Project 3 - QuizMaster

## Project Description
An android Quiz application written in Kotlin

## Technologies Used
|               |               |          | 
| ------------- | ------------- | -------- |
| Kotlin          | Android Studio - Chipmunk         | MVVM Design  |
| Firebase Authentication           | Firebase Realtime Database         | Firebase Storage  |
| Firebase Crashlytics          | RxKotlin       | Retrofit2 |
| Dagger - Hilt         | Picasso      | Timber Tree logger|
| Mockito           | JUnit 4       | Espresso|

## APIs Used
* Open Trivia DB (https://opentdb.com) to fetch the quiz questions. No registration required.

## Screenshots

<img src = "https://github.com/SuneelKM/QuizMaster/blob/master/screenshot/1.png" width=1200 height=500>
<img src = "https://github.com/SuneelKM/QuizMaster/blob/master/screenshot/2.png" width=1200 height=500>
<img src = "https://github.com/SuneelKM/QuizMaster/blob/master/screenshot/3.png" width=1200 height=500>

## Features
* Used SonarQube for vulnerability testing.
* Layout designed using constraint layout.
* User can login with their existing account or register a new account. Firebase Authentication is used for login, logout, and registration.
* Added field validation checks and password strength check.
* Only registered users with their email id verified can login.
* To reset the password, user can use forgot password option on the login page.
* Localization: English and Hindi. User can select either English or Hindi language.
* Once login, user can choose category, level, and number of questions for the quiz.
* 30 seconds timer is set to answer each question. 
* User can stop and quit the quiz at any time before finishing.
* After finishing any quiz, user can view the final score as well as all the questions with their answers. These are also get saved in Firebase realtime database.
* In History page, user can see the past scores. By tapping on any score, user can view all the past questions with their answers.
* On History page, user can also sort the scores by Date, Category, Level, and Score.
* User have ability to change profile picture.


## To run the App
* Clone the Repo
* Open the Repo in Android Studio
* Let the Gradle to build
* Optional: You can create your own account on firebase on https://console.firebase.google.com to manage the authentication, realtime database, storage, and crashlytics. Remember to replace the google-services.json file in the "app" folder with your own.
* Enjoy!!

