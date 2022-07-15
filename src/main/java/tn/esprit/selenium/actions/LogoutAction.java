package tn.esprit.selenium.actions;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;

import tn.esprit.config.LinkedInProperties;
import tn.esprit.utils.SpringContext;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public class LogoutAction implements Callable<Void> {

	private final LinkedInProperties linkedInProperties;

	private final WebDriver driver;

	public LogoutAction(WebDriver driver) {
		this.driver = driver;
		this.linkedInProperties = SpringContext.getBean(LinkedInProperties.class);
	}

	@Override
	public Void call() throws Exception {
		driver.navigate().to(linkedInProperties.getLogoutUrl());
		return null;
	}

}