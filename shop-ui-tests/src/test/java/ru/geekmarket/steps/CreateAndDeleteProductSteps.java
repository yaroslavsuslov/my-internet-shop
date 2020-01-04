package ru.geekmarket.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.geekmarket.DriverInitializer;

public class CreateAndDeleteProductSteps extends AbstractSteps {

    @Given("^I open browser$")
    public void iOpenBrowser() throws Throwable {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to products\\.html page$")
    public void iNavigateToProductsHtmlPage() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("products.url"));
    }

    @And("click Create new button")
    public void clickCreateNewButton() {
        WebElement webElement = webDriver.findElement(By.id("btn-create-product"));
        webElement.click();
    }

    @And("I provide Product name as {string} and Price as {string} and Brand as {string} and Categories as {string}")
    public void iProvideProductNameAsAndPriceAsAndBrandAsAndCategoriesAs(String product_name, String price, String brand, String category) {
        WebElement productNameField = webDriver.findElement(By.id("name"));
        WebElement priceField = webDriver.findElement(By.id("price"));
        WebElement brandField = webDriver.findElement(By.id("brand"));
        WebElement categoryField = webDriver.findElement(By.id("category"));

        productNameField.sendKeys(product_name);
        priceField.sendKeys(price);
        brandField.sendKeys(brand);
        categoryField.sendKeys(category);
    }

    @And("I click on Submit button")
    public void iClickOnSubmitButton() {
        webDriver.findElement(By.id("btn-submit-product")).click();
    }

    @Then("product with name {string} is created")
    public void productWithNameIsCreated(String expectedName) {
        String actualName = null;
        String uri = "http://localhost:8081/product/" + expectedName + "/find";
        webDriver.get(uri);
        WebElement productNameField = webDriver.findElement(By.id("name"));
        if (productNameField != null) {
            actualName = productNameField.getAttribute("value");
        }
        Assert.assertNotNull(actualName);
        Assert.assertEquals(expectedName, actualName);
    }

    @When("I delete product with name {string}")
    public void iDeleteProductWithName(String name) {
        String uri = "http://localhost:8081/product/" + name + "/find";
        webDriver.get(uri);
        WebElement webDriverElement = webDriver.findElement(By.id("id"));
        String id = webDriverElement.getAttribute("value");
        String deleteUri = "http://localhost:8081/product/" + id + "/delete";
        webDriver.get(deleteUri);
    }

    @Then("product with name {string} is deleted")
    public void productWithNameIsDeleted(String name) {
        String uri = "http://localhost:8081/product/" + name + "/find";
        webDriver.get(uri);
        WebElement webDriverElement = null;
        String actual = null;
        try {
            webDriverElement = webDriver.findElement(By.id("id"));
        } catch (Exception e) {

        } finally {
            if (webDriverElement != null) {
                actual = webDriverElement.getAttribute("value");
            }
            Assert.assertNull(actual);
        }
    }

    @After
    public void quitBrowser() {
        webDriver.quit();
    }
}
