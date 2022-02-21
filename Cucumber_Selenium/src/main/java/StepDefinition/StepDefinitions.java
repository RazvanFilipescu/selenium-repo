package StepDefinition; 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager; 

public class StepDefinitions { 
   WebDriver driver = null; 
   // Create Properties class object to read properties file
   Properties obj=new Properties();
   
   @Before
   public void openTheApplication() throws IOException {
//     WebDriverManager.chromedriver().setup();
//     WebDriver driver = new ChromeDriver();    
//	   driver.get("http://www.google.co.in");
	   System.setProperty("webdriver.chrome.driver","d:\\Work\\Cucumber\\ChromeDriver\\chromedriver.exe");
	   driver = new ChromeDriver(); 
	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
       driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
       
       //load elements from repository
       // Specify the file location I used . operation here because
       //we have object repository inside project directory only
       File src=new File("src/main/elements.properties");

       // Create FileInputStream object
       FileInputStream objfile=new FileInputStream(src);

       // Load file so we can use into our script
       obj.load(objfile);

       System.out.println("Property class loaded");
   }

   //TC1
   @Given("I have accesed the {word} site") 
   public void openBrowser(String site) { 
      driver.navigate().to("https://www." + site + ".com/"); 
   } 
	
   @When("^TC1 - I do something Google$") 
   public void goToSite() { 
	   // get rid of cookies dialog	   
	   WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.elementToBeClickable(By.id(obj.getProperty("google.cookies.accept.id"))));
		        
	   firstResult.click();

	   // enter a search item
	   WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
       .until(ExpectedConditions.elementToBeClickable(By.name(obj.getProperty("google.searchbar.name"))));
	   element.sendKeys("Cheese!\n");
	   element = new WebDriverWait(driver, Duration.ofSeconds(10))
		       .until(ExpectedConditions.elementToBeClickable(By.name(obj.getProperty("google.searchbar.name"))));
	   element.submit();
   } 
	
   @Then("^TC1 - Login button should exist Google$") 
   public void loginButton() { 
	   /*if(driver.findElement(By.cssSelector("[aria-label=Acasă]")).isEnabled()) { 
			System.out.println("Test 1 Pass"); 
      	} else { 
         System.out.println("Test 1 Fail"); 
      } */
	  
	  // Take screenshot
	  File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  //String screenshotBase64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	  //Copy the file to a location and use try catch block to handle exception
      try 
      {
    	  FileUtils.copyFile(screenshot, new File("target/homePageScreenshot.png"));
      } 
      catch (IOException e) {
    	  System.out.println(e.getMessage());
      }
   } 
   
   //TC2
   @When("^TC2 - I do something Facebook$") 
   public void goToSite1() {
	   // get rid of cookies dialog	   
	   WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.elementToBeClickable(By.xpath(obj.getProperty("facebook.cookies.accept.xpath"))));

	   firstResult.click();

	   // press Login
	   WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
       .until(ExpectedConditions.elementToBeClickable(By.xpath(obj.getProperty("facebook.login.button.xpath"))));
	   element.click();
   } 
	
   @Then("^TC2 - Login button should exist Facebook$") 
   public void loginButton1() { 
	   WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
		       .until(ExpectedConditions.elementToBeClickable(By.xpath(obj.getProperty("facebook.login.error.xpath"))));
   } 

   //TC3
   @Given("^TC3 - I have accesed the Google site$") 
   public void openBrowser3() { 
      driver.navigate().to("https://www.google.com/"); 
   } 
	
   @When("^TC3 - I enter text and click Search$") 
   public void searchText3() { 
	   // get rid of cookies dialog	   
	   WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.elementToBeClickable(By.id(obj.getProperty("google.cookies.accept.id"))));
		        
	   firstResult.click();

	   // enter a search item
	   WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
       .until(ExpectedConditions.elementToBeClickable(By.name("q")));
	   element.sendKeys("a");
	   element = new WebDriverWait(driver, Duration.ofSeconds(10))
		       .until(ExpectedConditions.elementToBeClickable(By.xpath(obj.getProperty("google.searchbar.searchbutton.xpath"))));
	   element.click();
//	   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
   } 
	
   @Then("^TC3 - Number of results should be shown$") 
   public void existsResults3() { 
	   WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
		       .until(ExpectedConditions.presenceOfElementLocated(By.id(obj.getProperty("google.searchbar.results.id"))));
   } 

   @After
   public void tidyUp() {
      driver.close(); 
   }
}