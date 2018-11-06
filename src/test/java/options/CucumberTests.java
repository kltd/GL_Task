package options;



import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

import cucumber.api.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"steps"},
        features = {"src/test/CucumberScenarios"})

public class CucumberTests {

}
