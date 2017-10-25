

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.walmart.qe.mobilebot.util.AppiumServerJava;
 
public class StartChrome
{
 
 
@Test
public void test1() throws Exception{
 
	  AppiumServerJava ap = new AppiumServerJava();
//	  DeviceService ds = new DeviceService();
//	  ActiveReservation ar = new ActiveReservation();
//	  
//	  ar.setDeviceManufacturer("05157df5fb3b6b30");
	  
	  // Create object of  DesiredCapabilities class and specify android platform
	  DesiredCapabilities capabilities=DesiredCapabilities.android();
 
	  capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
	  capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
	  capabilities.setCapability("deviceName", "Note5");
	  capabilities.setCapability("newCommandTimeout", 180);
	  capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
 
	  // Create object of URL class and specify the appium server address
	  URL url= new URL("http://172.21.17.142:4723/wd/hub");
	  //Start appium
	  ap.startServer("172.21.17.142", "4723", "05157df5fb3b6b30", "9723", "302");
	  
	  // Create object of  AndroidDriver class and pass the url and capability that we created
	@SuppressWarnings("rawtypes")
	WebDriver driver = new AndroidDriver(url, capabilities);
 
	  // Open url
	  driver.get("http://www.facebook.com");
  
	  System.out.println(ap.findPIDForPort("9723"));
  
	  ap.killProcess("9723");
 
	  // print the title
	  System.out.println("Title "+driver.getTitle());
 
	  // close the browser
	  driver.quit(); 
 
}
 
}
