package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


public class TestSteps extends AbstractTestNGCucumberTests {

    public static WebDriver driver;
    Response response;

    @After
    public void tearDown(){
        driver.quit();
    }

    @Given("The driver is initialized")
    public void theDriverIsInitialized() {
        startDriver();
    }

    @Given("I am a non-registered customer")
    public void iAmANonRegisteredCustomer() {}

    @And("I navigate to {string}")
    public void iNavigateToHomePage(String url) {
        driver.navigate().to(url);
    }

    @When("I select english")
    public void iSelectA() {
        driver.findElement(By.className("lang")).click();
    }

    @And("I Select {string}")
    public void iSelectFrom(String brand) {
        String selectionTxt = "//a[@href=\"/shop/shopByBrand/" + brand + "\"]";
        driver.findElement(By.xpath(selectionTxt)).click();
    }

    @And("I select the first results item")
    public void iSelectTheFirstResultsItem() throws InterruptedException {
        Thread.sleep(15000);
        WebElement firstResult = driver.findElements(By.className("heading")).get(2);
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(firstResult)).click();
    }

    @And("I press add to basket button")
    public void iPressAddToBasketButton() throws InterruptedException {
        Thread.sleep(4000);
        WebElement addBasketBtn = driver.findElement(By.xpath("//div[@class=\"addToBasket-btn\"]//button"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addBasketBtn)).click();
    }

    @And("I press proceed to checkout button")
    public void iPressProceedToCheckoutButton() {
        // Find the button then Scroll to see the button in the displayed screen
        WebElement checkoutBtn = driver.findElement(By.xpath("//div[@class=\"cart_checkout fontLightEnAr\"]//button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }

    @And("I select the delivery city {string}")
    public void iSelectTheDeliveryCity(String city) {
        WebElement cityList = driver.findElements(By.tagName("select")).get(0);
        Select select = new Select(cityList);
        select.selectByVisibleText(city);
    }

    @And("I select the delivery area {string}")
    public void iSelectTheDeliveryArea(String area) {
        WebElement areaList = driver.findElements(By.tagName("select")).get(1);
        Select select = new Select(areaList);
        select.selectByVisibleText(area);
    }

    @And("I select deliver to my address")
    public void iSelectDeliverToMyAddress() {
        WebElement deliverToMyAddBtn = driver.findElements((By.xpath("//div[@class=\"checkout-DelivaryOptionsRes\"]//p"))).get(0);
        // scroll until find the element in the screen
        ((JavascriptExecutor) driver).executeScript("scrollBy(0, 500);");
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(deliverToMyAddBtn)).click();
    }


    @And("I entered address details as {string} {string} {string} {string}")
    public void iEnteredAddressDetailsAs(String street, String buildingNum, String floorNo, String aprtNo) {
        driver.findElement(By.xpath("//input[@placeholder=\"Street Name\"]")).sendKeys(street);
        driver.findElements(By.xpath("//input[@placeholder=\"196\"]")).get(0).sendKeys(buildingNum);
        driver.findElements(By.xpath("//input[@placeholder=\"196\"]")).get(1).sendKeys(floorNo);
        driver.findElements(By.xpath("//input[@placeholder=\"196\"]")).get(2).sendKeys(aprtNo);
    }

    @And("I press continue in delivery option section")
    public void iPressContinue() {
        ((JavascriptExecutor) driver).executeScript("scrollBy(0, 700);");
        WebElement continueBtn = driver.findElement(By.xpath("//div[@class=\"checkout-addressBtn\"]//button"));
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    @And("I keep personal info empty")
    public void iKeepPersonalInfoEmpty() {
        // keep empty
    }

    @And("I press continue in personal info section")
    public void iPressContinueInPersonalInfoSection() {
        WebElement continueBtn = driver.findElement(By.id("shippingAddressContinue"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    @Then("I get an error on the mandatory empty fields")
    public void iGetAnErrorOnTheMandatoryEmptyFields() {
        String nameErrorMsg = driver.findElements(By.xpath("//div[@class=\"help-block alertComp\"]//div")).get(0).getText();
        String mobileErrorMsg = driver.findElements(By.xpath("//div[@class=\"help-block alertComp\"]//div")).get(1).getText();
        String EmailErrorMsg = driver.findElements(By.xpath("//div[@class=\"help-block alertComp\"]//div")).get(2).getText();

        Assert.assertEquals(nameErrorMsg, "Please enter a valid name");
        Assert.assertEquals(mobileErrorMsg, "Please enter a valid Mobile Number e.g. 01*********");
        Assert.assertEquals(EmailErrorMsg, "Please enter a valid email address");
    }

    @Given("I am a non-registered user")
    public void iAmANonRegisteredUser() {
    }

    @When("I request {string} with {string}")
    public void iRequest(String postsUrl, String id) {
        RestAssured.baseURI = postsUrl + id;

        response = given().contentType("application/json").when().get();
    }

    @Then("I get a response with success status code")
    public void iGetAResponseWithSuccessStatusCode() {
        Assert.assertEquals(response.statusCode(), 200);
    }

    @And("I received the post title")
    public void iReceivedThePostTitle() {
        String postTitle = response.jsonPath().getString("title");
        Assert.assertTrue(postTitle.startsWith("sunt"));
    }

    @And("I received the post body")
    public void iReceivedThePostBody() {
        String postBody = response.jsonPath().getString("body");
        Assert.assertTrue(postBody.startsWith("quia"));
    }

    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }
}
