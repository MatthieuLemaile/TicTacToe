# TicTacToe

This project is a nought and crosses (tic-tac-toe) in java. It has no real purpose other than learning some things in Java for me.

Actual version is 1.0

## Installation

JDK 1.11 is required.

This project use gradle to be built, and Git for version control.

To ensure you have correctly download the project,
you may run `gradlew clean build`,
and gradle should be successful.

More about git :
https://rogerdudler.github.io/git-guide/index.fr.html
just, please, use `git pull --rebase` instead of just `git pull`.

Full gradle command line, with test and sonar :
``` shell script
gradlew clean build jacocoTestReport sonarqube
```

To run as a standalone jar, use the "all" jar, located in build/libs after you ran the gradle build task.

####Sonar
Run sonar docker with docker run -d -p 9000:9000 sonarqube
In your sonar (localhost:9000) log in with admin/admin,
create a project following instructions,
and update build.gradle with your project key.
you can then run sonarqube with :
```shell script
./gradlew build jacocoTestReport sonarqube
``` 


## Authors

Marc Lemaile
Matthieu Lemaile

## Miscellaneous


## Things to add

#### backlog
###### technical improvement task
* Check plugins version number in build.gradle

###### code improvement

###### New features

###### User-friendly

## Release History

#### 1.0
TODO :
* UI
* Board : WinCheck with better cognitive complexity.
* Board : WinCheck with Pat
* Make Test for controller part

###### User visible
* Playable Tic-Tac-Toe, with minimalist interface

###### Dev Part
* Structure creation.