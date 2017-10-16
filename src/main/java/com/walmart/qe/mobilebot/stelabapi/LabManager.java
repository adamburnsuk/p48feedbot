package com.walmart.qe.mobilebot.stelabapi;

//import com.walmart.gls.receiving.svc.bo.Container.java;
import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;




//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walmart.qe.mobilebot.model.DeviceFile;

/**
 * This class is used to interact with devices and reservations in the STE lab.   
 * 
 * @author a2burns
 *
 */
public class LabManager {

	private String sAuthUrl;
	private static final String RESERVATION_ENGINE_URL ="http://172.21.64.245:8080/xml";
	private static final String STE_LAB_API_URL = "http://172.21.17.142:8087/svc/devices/";
	private String[] sAuthToken;
	private String sUserID;
	private String errorMessage;
	private SessionInfo sessionInfo;

	/**
	 * 
	 * @return get the auth url for ste device lab
	 */
	public String getsAuthUrl() {
		return sAuthUrl;
	}

	/**
	 * 
	 * @param sAuthUrl set the auth url for ste device lab
	 */
	public void setsAuthUrl(String sAuthUrl) {
		this.sAuthUrl = sAuthUrl;
	}

	/**
	 * 
	 * @return get the auth token for ste device lab session (must be logged in for token to exist)
	 */
	public String[] getsAuthToken() {
		return sAuthToken;
	}

	/**
	 * 
	 * @param set the auth token for ste device lab session (this is used to store passed token..you cannot create this yourself)
	 */
	public void setsAuthToken(String[] sAuthToken) {
		this.sAuthToken = sAuthToken;
	}

	/**
	 * 
	 * @return get the userID for this session
	 */
	public String getsUserID() {
		return sUserID;
	}

	/**
	 * 
	 * @param sUserID set the userID for the session
	 */
	public void setsUserID(String sUserID) {
		this.sUserID = sUserID;
	}

	/**
	 * 
	 * @return get error message associated with session
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 
	 * @param errorMessage set the error message associated with this session
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * This method used to log in to the STE device lab.  It requires your plain text userID and password.
	 * 
	 * @param userID userID for ste reservation engine (currently EggCloud)
	 * @param password password for ste reservation engine (currently EggCloud)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws LoginException  (Catches cases where login isn't successful, such as invalid credentials)
	 */
	public SessionInfo Login(String userID, String password) throws ClientProtocolException, IOException, LoginException {
		Login loginRequest = new Login();
		SessionInfo sessionInfo = new SessionInfo();

		Gson gson = new GsonBuilder().create();

		// Set new user info
		loginRequest.setAction("login");
		loginRequest.setUsername(userID);
		loginRequest.setPassword(password);

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(loginRequest));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());
			
			sessionInfo = gson.fromJson(sEntity, SessionInfo.class);

			//Check to find out if login was successful...if not, throw LoginException
			if(sessionInfo.getRequestStatus().getStatusDetails().equalsIgnoreCase("Missing authorization credentials")){
				throw new LoginException("Invalid login credentials.");
			}
			
			//Set global session info variable
			this.sessionInfo = sessionInfo;
			
			return sessionInfo;
		} else {

			throw new LoginException(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());
		}

	}
	
	/**
	 * This will return a list of all currently available devices that you have access to.  
	 * 
	 * @return AvaibleDeviceResponse -- list of all available devices 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public AvailableDeviceResponse getAvailableDevices() throws ClientProtocolException, IOException{
		AvailableDeviceRequest deviceRequest = new AvailableDeviceRequest();
		
		Gson gson = new GsonBuilder().create();

		// Set new user info
		deviceRequest.setAction("request-available-devices");
		deviceRequest.setSessionId(this.sessionInfo.getSessionId());
		deviceRequest.setStartTime("1970-01-01 00:00");
		deviceRequest.setEndTime("1970-01-01 00:15");

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(deviceRequest));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);
		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			AvailableDeviceResponse availableDevices = gson.fromJson(sEntity, AvailableDeviceResponse.class);

			return availableDevices;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}

	
	/**
	 * This returns the deviceID of the device in the STE reservation engine (Currently EggCloud).
	 * It requires you to pass a list of available devices.
	 * 
	 * @param deviceList
	 * @param deviceName
	 * @return
	 */
	public int getDeviceIdByName(AvailableDeviceResponse deviceList, String deviceName){
		
		for(int i=0; i<=deviceList.getDevices().size()-1; i++){
			if(deviceList.getDevices().get(i).getMetaTags().get(0).getName().equalsIgnoreCase(deviceName)){
				return deviceList.getDevices().get(i).getDeviceId();
			}
		}
		
		return 0;
		
	}
	
	/**
	 * 
	 * @param deviceList  This is the list of available devices in the STE reservation system. (Currently EggCloud)
	 * @param deviceType This is the device type of the device (MC40, TC70)
	 * @return
	 * @throws NoAvailableDeviceException - If no device is found matching your device type with no active reservation, this exception is thrown.
	 */
	public int getDeviceIdByType(AvailableDeviceResponse deviceList, String deviceType) throws NoAvailableDeviceException {
		
		for(int i=0; i<=deviceList.getDevices().size()-1; i++){
			if(deviceList.getDevices().get(i).getMetaTags().get(4).getModel().equalsIgnoreCase(deviceType)){
				if(deviceList.getDevices().get(i).getActiveReservation()==null){
					return deviceList.getDevices().get(i).getDeviceId();
				}
			}
		}
		
		//If no devices found, throw exception
		throw new NoAvailableDeviceException("No free devices found matching your criteria.");
		
	}
	
	/**
	 * 
	 * @param deviceSerial
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException - If no device is found matching your device serial number, this exception is thrown.
	 */
	public int getDeviceIdBySerial(String deviceSerial) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		
		AvailableDeviceResponse deviceList = new AvailableDeviceResponse();
		
		deviceList = this.getAvailableDevices();
		
		for(int i=0; i<=deviceList.getDevices().size()-1; i++){
			if(deviceList.getDevices().get(i).getDeviceManufacturer().equalsIgnoreCase(deviceSerial)){
					return deviceList.getDevices().get(i).getDeviceId();
			}
		}
		
		//If no devices found, throw exception
		throw new NoAvailableDeviceException("No free devices found matching your criteria.");
		
	}
	
	/**
	 * 
	 * @param deviceSerial
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException - If no device is found matching your device serial number, this exception is thrown.
	 */
	public String getSerialByDeviceID(Integer deviceID) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		
		AvailableDeviceResponse deviceList = new AvailableDeviceResponse();
		
		deviceList = this.getAvailableDevices();
		
		for(int i=0; i<=deviceList.getDevices().size()-1; i++){
			if(deviceList.getDevices().get(i).getDeviceId().equals(deviceID)){
					return deviceList.getDevices().get(i).getDeviceManufacturer();
			}
		}
		
		//If no devices found, throw exception
		throw new NoAvailableDeviceException("No free devices found matching your criteria.");
		
	}
	
	/**
	 * 
	 * @param deviceName
	 * @param numMinutes
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ReservationResponse createReservationByDeviceName(String deviceName, int numMinutes) throws ClientProtocolException, IOException{
		Reservation reservation = new Reservation();
		AvailableDeviceResponse availableDevices = new AvailableDeviceResponse();
		ReservationResponse resResponse = new ReservationResponse();
		int deviceId;
		
		availableDevices = getAvailableDevices();
		
		deviceId = getDeviceIdByName(availableDevices, deviceName);
		
		Gson gson = new GsonBuilder().create();

		// Set reservation 
		reservation.setAction("reservation");
		reservation.setDeviceId(deviceId);
		reservation.setSessionId(this.sessionInfo.getSessionId());
		reservation.setStartIn(0);
		reservation.setDuration(numMinutes);
		

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			resResponse = gson.fromJson(sEntity, ReservationResponse.class);

			return resResponse;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}
	
	/**
	 * 
	 * @param deviceType
	 * @param numMinutes
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException 
	 */
	public ReservationResponse createReservationByDeviceType(String deviceType, int numMinutes) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		Reservation reservation = new Reservation();
		AvailableDeviceResponse availableDevices = new AvailableDeviceResponse();
		ReservationResponse resResponse = new ReservationResponse();
		int deviceId;
		
		availableDevices = getAvailableDevices();
		

		deviceId = getDeviceIdByType(availableDevices, deviceType);
		
		Gson gson = new GsonBuilder().create();

		// Set reservation 
		reservation.setAction("reservation");
		reservation.setDeviceId(deviceId);
		reservation.setSessionId(this.sessionInfo.getSessionId());
		reservation.setStartIn(0);
		reservation.setDuration(numMinutes*60);
		

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			resResponse = gson.fromJson(sEntity, ReservationResponse.class);

			return resResponse;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}
	
	/**
	 * 
	 * @param deviceSerial
	 * @param numMinutes number of minutes for reservation (reservations are in 15minute blocks...)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException
	 */
	public ReservationResponse createReservationByDeviceSerial(String deviceSerial, int numMinutes) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		Reservation reservation = new Reservation();
		ReservationResponse resResponse = new ReservationResponse();
		int deviceId;
		
		deviceId = getDeviceIdBySerial(deviceSerial);
		
		Gson gson = new GsonBuilder().create();

		//Set reservation 
		reservation.setAction("reservation");
		reservation.setDeviceId(deviceId);
		reservation.setSessionId(this.sessionInfo.getSessionId());
		reservation.setStartIn(0);
		reservation.setDuration(numMinutes*60);
		

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			resResponse = gson.fromJson(sEntity, ReservationResponse.class);

			return resResponse;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}
	
	/**
	 * 
	 * @param deviceSerial
	 * @param numMinutes number of minutes for reservation (reservations are in 15minute blocks...)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException
	 */
	public ReservationResponse cancelReservation(ActiveReservation reservation) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		Reservation reservationRequest = new Reservation();
		ReservationResponse resResponse = new ReservationResponse();
		
		Gson gson = new GsonBuilder().create();

		//Set reservation 
		reservationRequest.setAction("cancel-reservation");
		reservationRequest.setSessionId(this.sessionInfo.getSessionId());
		reservationRequest.setReservationId(reservation.getReservationId());

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservationRequest));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			resResponse = gson.fromJson(sEntity, ReservationResponse.class);

			return resResponse;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}
	
	/**
	 * 
	 * @param minutes
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ReservationResponse createReservation(int minutes) throws ClientProtocolException, IOException {
		Reservation reservation = new Reservation();
		ReservationResponse resResponse = new ReservationResponse();
		
		Gson gson = new GsonBuilder().create();

		// Set reservation 
		reservation.setAction("reservation");
		reservation.setDeviceId(3);
		reservation.setSessionId(this.sessionInfo.getSessionId());
		reservation.setStartIn(5);
		reservation.setDuration(minutes*60);
		

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPost postRequest = new HttpPost(RESERVATION_ENGINE_URL);

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		postRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(postRequest);

		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			resResponse = gson.fromJson(sEntity, ReservationResponse.class);

			return resResponse;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return null;
		}

	}
		 	
 	
	
	/**
	 * 
	 * @param reservationID (This is the STE Lab Reservation engine reservation ID...you cannot manage a device without a reservation first)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException 
	 */

	public boolean startAppiumForDevice(ActiveReservation reservation) throws ClientProtocolException, IOException, NoAvailableDeviceException{
		
		//Create gsonbuilder object to handle serialization/deserialization of json objects/messages
		Gson gson = new GsonBuilder().create();

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPut putRequest = new HttpPut(STE_LAB_API_URL + "/startappium");

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		putRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(putRequest);
		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			return true;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return false;
		}

	}
	
	/**
	 * 
	 * @param reservationID (This is the STE Lab Reservation engine reservation ID...you cannot manage a device without a reservation first)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException 
	 */

	public boolean stopAppiumForDevice(ActiveReservation reservation) throws ClientProtocolException, IOException, NoAvailableDeviceException{
		
		//Create gsonbuilder object to handle serialization/deserialization of json objects/messages
		Gson gson = new GsonBuilder().create();

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(reservation));

		// Create http post object
		HttpPut putRequest = new HttpPut(STE_LAB_API_URL + "/stopappium");

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		putRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(putRequest);
		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			AvailableDeviceResponse availableDevices = gson.fromJson(sEntity, AvailableDeviceResponse.class);

			return true;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
					+ response.getStatusLine().getReasonPhrase());

			return false;
		}

	}
	
	/**
	 * 
	 * @param reservation
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException
	 */
	public boolean installAPKForDevice(ActiveReservation reservation, File file) throws ClientProtocolException, IOException, NoAvailableDeviceException{
	
		if(uploadFiletoLab(file)){
			return(installAPKForDevice(reservation, file.getName()));
		}else{
			return false;
		}

	}
	
	/**
	 * This method cannot be used unless file has already been uploaded to the server.
	 * 
	 * @param reservation
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException
	 */

	private boolean installAPKForDevice(ActiveReservation reservation, String fileName) throws ClientProtocolException, IOException, NoAvailableDeviceException{
	
		//Create gsonbuilder object to handle serialization/deserialization of json objects/messages
		Gson gson = new GsonBuilder().create();
		DeviceFile dFile = new DeviceFile();
		
		dFile.setName(fileName);

		// Create your http client
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// Generate String input
		StringEntity input = new StringEntity(gson.toJson(dFile));

		// Create http post object
		HttpPut putRequest = new HttpPut("http://172.21.17.142:8087/svc/devices/"+ reservation.getDeviceManufacturer() + "/packages/install");

		// Set content type for post
		input.setContentType("application/json");

		// attach message body to request
		putRequest.setEntity(input);

		// submit request and save response
		HttpResponse response = httpclient.execute(putRequest);
		// get status code from response
		int sStatusCode = response.getStatusLine().getStatusCode();

		if (sStatusCode == 200) {

			// Get response body (entity and parse to string
			String sEntity = EntityUtils.toString(response.getEntity());

			return true;
		} else {

			this.setErrorMessage(response.getStatusLine().getStatusCode()
							+ response.getStatusLine().getReasonPhrase());

			return false;
		}

	

	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public boolean uploadFiletoLab(File file) throws ParseException, IOException{
		
		//Create your http client
				HttpClient httpclient = HttpClientBuilder.create().build();

				//Create http post object
				HttpPost postRequest = new HttpPost("http://172.21.17.142:8087/svc/files");

				//String message = "This is a multipart post";
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
				//builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);

				HttpEntity entity = builder.build();
				postRequest.setEntity((org.apache.http.HttpEntity) entity);
				HttpResponse response = httpclient.execute(postRequest);
				
				// get status code from response
				int sStatusCode = response.getStatusLine().getStatusCode();

				if (sStatusCode == 200) {

					// Get response body (entity and parse to string
					String sEntity = EntityUtils.toString(response.getEntity());

					return true;
				} else {

					this.setErrorMessage(response.getStatusLine().getStatusCode()
							+ response.getStatusLine().getReasonPhrase());

					return false;
				}

		
	}
	
	
}// end of class
