package tn.esprit.service.impl.user;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import tn.esprit.config.SeleniumProperties;
import tn.esprit.exception.AppException;
import tn.esprit.model.user.User;
import tn.esprit.payload.LinkedInAuthRequest;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.selenium.actions.CollectAction;
import tn.esprit.selenium.actions.LoginAction;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SeleniumService {

	private final SeleniumProperties seleniumProperties;

	@Autowired
	private UserRepository userRepository;

	private WebDriver driver;
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	@Autowired
	public SeleniumService(SeleniumProperties seleniumProperties) {
		this.seleniumProperties = seleniumProperties;
	}

	@PostConstruct
	public void initialize() {
		// Initialize web driver...
		switch (seleniumProperties.getWebDriverType()) {
		case CHROME:
			System.setProperty("webdriver.chrome.driver", seleniumProperties.getWebDriverPath());
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--lang=en");
			// chromeOptions.addArguments("--headless");

			// this is to ENABLE LogType.PERFORMANCE
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.setAcceptInsecureCerts(true);
			chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			driver = new ChromeDriver(chromeOptions);
			break;
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", seleniumProperties.getWebDriverPath());
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("intl.accept_languages", "en");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			driver = new FirefoxDriver(firefoxOptions);
			break;
		default:
			throw new IllegalArgumentException("Unknown driver type!");
		}
	}

	public User collectProfileInfo(LinkedInAuthRequest request, UserPrincipal userPrincipal) {
		checkArgument(request.getUsername() != null && !request.getUsername().trim().isEmpty(),
				"The Username shouldn't be empty or null!");
		checkArgument(request.getPassword() != null && !request.getPassword().trim().isEmpty(),
				"The Password shouldn't be empty or null!");
		// Perform action in single threaded pool
		executorService.submit(new LoginAction(driver, request.getUsername(), request.getPassword()));

		Future<JSONObject> result = executorService.submit(new CollectAction(driver));
		try {
			User user = userRepository.getUserByName(userPrincipal.getUsername());
			user.setInfo(result.get().toString());
			return userRepository.save(user);
		} catch (InterruptedException | ExecutionException e) {
			throw new AppException("Failed To collect linkedin profile for user : " + userPrincipal.getUsername());
		} finally {
			driver.quit();
		}
	}

}
