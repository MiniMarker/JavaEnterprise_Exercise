# JavaEnterprise_Exercise

* Student: Christian Marker
* Subject: PG5100-1 18V Enterpriseprogrammering 1

**Project Structure**

The project contains 3 submodules
* backend
    * Entities
    * Services
    * ServiceTests
* frontend
    * Controllers
    * WebSecurityConfig
    * Selenium Tests
    * Application Runner
* reports
    * JaCoCo coverage
    * OWASP check

## How to run code

1. Clone this repo
2. Run from project root folder: 
<br/> ```mvn install``` will install and get reports **with** running tests
<br/> ```mvn install -DskipTests``` will install and get reports **without** running tests
3. Run **LocalApplicationRunner** located in ~/frontend/src/test/java/no/cmarker/frontend/LocalApplicationRunner.java
4. Open ```localhost:8080/``` in your browser

To generate code reports after install:
```mvn jacoco:report-aggregate```