package test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestAutomation{
	
	WebDriver driver;
    WebDriverWait wait;
	
	@BeforeClass
	public void setUp() {
		// Initialize the driver

		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void watchDemo() {

		driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
		for (int i = 0; i < 2; i++) {
			WebElement macBook = driver.findElement(By.xpath("//a[text()='MacBook']"));
			macBook.click();
			WebElement addToCartButton = driver.findElement(By.id("button-cart"));
			addToCartButton.click();
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement successMessage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
			Assert.assertTrue(
					successMessage.getText().contains("Success: You have added MacBook to your shopping cart!"));
			driver.navigate().back();
		}
	}

	@Test(priority = 2)
	public void openShoppingCart() {
		WebElement shoppingCart = driver.findElement(By.cssSelector("#top-links a[title='Shopping Cart']"));
		shoppingCart.click();
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("route=checkout/cart"));
	}

	@Test(priority = 3)
	public void validatePrices() {
		WebElement unitPriceElement = driver.findElement(By.xpath("//div[@class='table-responsive']//tbody/tr/td[5]"));
		WebElement totalPriceElement = driver.findElement(By.xpath("//div[@class='table-responsive']//tbody/tr/td[6]"));

		String unitPriceText = unitPriceElement.getText().replaceAll("[^0-9.]", "");
		String totalPriceText = totalPriceElement.getText().replaceAll("[^0-9.]", "");

		double unitPrice = Double.parseDouble(unitPriceText);
		double totalPrice = Double.parseDouble(totalPriceText);

		Assert.assertEquals(totalPrice, unitPrice * 2, "Total price is not updating correctly.");
	}

}
