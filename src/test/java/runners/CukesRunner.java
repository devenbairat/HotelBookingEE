package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = "src/test/resources/features",
        glue     = "/stepDefinitions",
        plugin   = { "pretty", "json:target/cucumber-report/cucumber.json",
                     "pretty", "html:target/cucumber-report.html",
                     "pretty", "json:target/reports/cucumber.json",
                     "pretty",  "junit:target/reports/cucumber.xml"}
)

public class CukesRunner {
}


