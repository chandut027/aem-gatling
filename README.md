# aem-gatling
Gatling Performance tests for AEM projects using scala and Maven.

Gatling is a Scala based open-source load test tool which utilises asynchronous concurrency and a DSL for scripting.

### To run the tests

    simply execute the following command for the home classes:

    mvn -Denv=docker -DuserPerSec=2 -Dduration=50 gatling:execute -Dgatling.simulationClass=simulations.LoadTestAllResources

    mvn -Denv=docker -DuserPerSec=2 -Dduration=50 gatling:execute -Dgatling.simulationClass=simulations.LoadTestOnlyHtmls
    
    Above commands will perform 2 hits per second and it will run for 50 secs duration. 
    For a test with 20 hits/sec and running test for 5 mins, command -DuserPerSec=20 -Dduration=300

### Available tests

1. LoadTestOnlyHtmls 
	
	a. will load test only given urls
	
	b. This hits ur dispatcher url
2. LoadTestAllResources

	a. will load test current urls along with sub resource like css,js, images .. except child htmls..
	
	b. This hits ur domain url
	
### Updating urls

1. You can update urls for performance tests in pages.json.
2. You can add more locales for testing in locales.json.
3. If you want only one locale pages, then give one locale in locales.json.
4. 

### Available environments

1. uat
2. qa
3. docker(local)

### Jenkins Integration

Use Gatling Jenkins Plugin for reports.
