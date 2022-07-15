package tn.esprit.selenium.actions;

import java.util.concurrent.Callable;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import tn.esprit.config.LinkedInProperties;
import tn.esprit.utils.SpringContext;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public class CollectAction implements Callable<JSONObject> {

	private static final String BLANK_STRING = "";
	private static final String TEXT_CONTENT = "textContent";
	private static final String SRC = "src";

	private final LinkedInProperties props;

	private final WebDriver driver;

	public CollectAction(WebDriver driver) {
		this.driver = driver;
		this.props = SpringContext.getBean(LinkedInProperties.class);
	}

	@Override
	public JSONObject call() throws Exception {
		driver.navigate().to(props.getProfileUrl());
		WebElement element = driver.findElement(By.cssSelector(props.getProfileCardCssSelector()));
		String fullName = getElementContentAsString(element, props.getProfileNameXpath(), TEXT_CONTENT);
		String bio = getElementContentAsString(element, props.getProfileBioXpath(), TEXT_CONTENT);
		String currentCompany = getElementContentAsString(element, props.getProfileCurrentCompanyXpath(), TEXT_CONTENT);
		String education = getElementContentAsString(element, props.getProfileEducationXpath(), TEXT_CONTENT);
		String location = getElementContentAsString(element, props.getProfileLocationXpath(), TEXT_CONTENT);
		String profileImage = getElementContentAsString(element, props.getProfileImageXpath(), SRC);
		JSONObject data = new JSONObject().put("fullName", fullName).put("bio", bio).put("location", location)
				.put("profileImage", profileImage).put("currentCompany", currentCompany).put("education", education);
		return data;
	}

	private String getElementContentAsString(WebElement node, String xpath, String attribute) {
		By elementXpath = By.xpath(xpath);
		return isElementPresent(node, elementXpath) ? node.findElement(elementXpath).getAttribute(attribute).strip()
				: BLANK_STRING;
	}

	private static boolean isElementPresent(WebElement node, By xpath) {
		return !node.findElements(xpath).isEmpty();
	}

}
