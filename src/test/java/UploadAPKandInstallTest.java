
import java.io.File;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.walmart.ste.stelabapi.LabManager;
import com.walmart.ste.stelabapi.LoginException;
import com.walmart.ste.stelabapi.NoAvailableDeviceException;
import com.walmart.ste.stelabapi.ReservationResponse;
import com.walmart.ste.stelabapi.SessionInfo;


public class UploadAPKandInstallTest {
	WebDriver driver;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	LabManager labManager = new LabManager();
	SessionInfo myInfo = new SessionInfo();
	ReservationResponse resResponse = new ReservationResponse();
	
	
	//@BeforeTest
	public void setUp() throws Exception {
		
		try {
			try {
				myInfo = labManager.Login("admin", "admin");
			} catch (LoginException e) {

				e.printStackTrace();
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println(myInfo.getUserId());
		
		try {
			resResponse = labManager.createReservationByDeviceType("NOTE5", 30);	
			
			System.out.println(labManager);
			
		} catch (IOException | NoAvailableDeviceException e) {

			e.printStackTrace();
		}
		
	}

	//@Test
	public void UploadFile() throws ClientProtocolException, IOException, NoAvailableDeviceException {

		File myFile = new File("C:\\toolbox\\Android\\MyProductivityApks\\store_MyProductivity_debug.apk");
		
		labManager.installAPKForDevice(resResponse.getActiveReservation(), myFile);
		
		
		
	}
	

	//@AfterTest
	public void End() throws ClientProtocolException, IOException, NoAvailableDeviceException {
		
		labManager.cancelReservation(resResponse.getActiveReservation());

	}
}
