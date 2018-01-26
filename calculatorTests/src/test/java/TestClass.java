import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class TestClass {
    private AppiumDriver<?> driver;

    @Before
    public final void calcSetup() throws IOException {
        URL url;
        InputStream input = new FileInputStream("src/resources/calc_config.properties");
        Properties prop = new Properties();
        prop.load((input));

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("deviceName"));
        capabilities.setCapability("avd", prop.getProperty("avd"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, prop.getProperty("platformVersion"));
        capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
        capabilities.setCapability("appPackage", prop.getProperty("appPackage"));

        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, prop.getProperty("newCommandTimeout"));
        capabilities.setCapability("FastReset", "true");

        try {
            url = new URL(prop.getProperty("driverURL"));
            driver = new AndroidDriver<MobileElement>(url, capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void calcTest() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.google.android.gms:id/suw_navbar_next")));

        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
    }

    @After
    public final void tearDow() {
        driver.quit();
    }
}
