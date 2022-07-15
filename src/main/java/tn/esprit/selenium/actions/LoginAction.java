package tn.esprit.selenium.actions;

import java.time.Duration;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tn.esprit.config.LinkedInProperties;
import tn.esprit.utils.SpringContext;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public class LoginAction implements Callable<Void> {

	private final LinkedInProperties linkedInProperties = SpringContext.getBean(LinkedInProperties.class);

	private final WebDriver driver;
	private final String username;
	private final String password;

	public LoginAction(WebDriver driver, String username, String password) {
		this.driver = driver;
		this.username = username;
		this.password = password;
	}

	@Override
	public Void call() throws Exception {
		driver.navigate().to(linkedInProperties.getLoginUrl());

		WebElement emailField = new WebDriverWait(driver, Duration.ofMillis(60)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(linkedInProperties.getEmailFieldXpath())));
		WebElement passwordField = driver.findElement(By.xpath(linkedInProperties.getPasswordFieldXpath()));

		emailField.sendKeys(username);
		passwordField.sendKeys(password);

		new WebDriverWait(driver, Duration.ofMillis(10L))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(linkedInProperties.getSigningButtonXpath())))
				.click();

		return null;
	}

}
