# aem-gatling
Performance tests for AEM projects

Gatling is a Scala based open-source load test tool which utilises asynchronous concurrency and a DSL for scripting.

### To run the tests

    simply execute the following command for the home classes:

    mvn -Denv=docker -DuserPerSec=2 -Dduration=10 gatling:execute -Dgatling.simulationClass=simulations.LoadTestAllResources

    mvn -Denv=docker -DuserPerSec=2 -Dduration=10 gatling:execute -Dgatling.simulationClass=simulations.LoadTestOnlyHtmls

### Available tests

1. LoadTestOnlyHtmls 
	a. will load test only given urls
	b. This hits ur dispatcher url
2. LoadTestAllResources
	a. will load test current urls along with sub resource like css,js, images .. except child htmls..
	b. This hits ur domain url

### Available environments

1. uat
2. qa
3. docker(local)
