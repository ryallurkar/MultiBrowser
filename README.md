# MultiBrowser

Steps to Import and run the project

1.	Prerequisites
•	Java
•	Maven
•	Eclipse
•	Allure
•	Chrome and FF latest

Important : create personal access token and add to BaseTest.java for downloading chrome and gecko driver automatically

	String tokenName = "";
	String tokenSecret = "";

git clone https://github.com/ryallurkar/MultiBrowser.git

1. To run from command line "mvn clean test"

2. Go to File > Import > Maven > Existing Maven Projects > Select unzipped folder (MultiBrowser)

3. Right click on testing.xml > Run as > TestNg Suite
