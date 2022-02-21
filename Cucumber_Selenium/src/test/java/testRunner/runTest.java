package testRunner;
 
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions; 

@RunWith(Cucumber.class) 
@CucumberOptions(
		// feature files location
		features = "src/test"
		//Steps definition
		,glue={"StepDefinition"}
		//redable in console
		,monochrome = true
		//reporting
		,plugin = {"pretty","html:target/cucumber-html-report.html","json:target/cucumber-json-report.json","junit:target/cucumber-junit-report" }
//		,tags = "not @Google"
//		,tags = "@Element"
//		,tags = "@Facebook"
		)
public class runTest { }