
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.walmart.qe.mobilebot.exceptions.AppiumNotStoppedException;
import com.walmart.qe.mobilebot.util.AppiumServerJava;


public class TestAppiumServerUtil {	
		
	//@Test
	public void startAppiumServer() throws MalformedURLException, AppiumNotStoppedException{
		
		AppiumServerJava ap = new AppiumServerJava();		
		ap.startServer("127.0.0.1", "4723", "05157df5fb3b6b30", "9500");
		
		//Check if server was successfully started
		Assert.assertEquals(true, ap.checkAppiumRunning("127.0.0.1","4723", 30000));
		
		//Stop the server
		ap.stopServer("4723");
		
	}
	
}
