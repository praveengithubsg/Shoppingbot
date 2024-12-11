package fileReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import base.Config;
import commonHelper.ResourceHelper;

public class JsonReader {

	private Logger log = Logger.getLogger(JsonReader.class.getName());

	/** this method reads json array from file **/
	private JSONArray readJson() {
		log.info("Reading JSON Array");
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
		ResourceHelper resourceHelper = new ResourceHelper();
		Config config = new Config();
		try {
			FileReader reader = new FileReader(resourceHelper.getFilepath(config.getJsonFileName()));
			obj = jsonParser.parse(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray dataList = (JSONArray) obj;

		return dataList;
	}

	/** this method returns user login details **/
	private List<String> getUserLogin(String userType) {
		log.info("Getting user login for type " + userType);
		JSONObject users = (JSONObject) readJson().get(0);
		JSONObject userdata = ((JSONObject) users.get("CDCRED"));
		JSONObject user = null;
		if (userType.equalsIgnoreCase("10656")) {
			user = (JSONObject) userdata.get("10656");
		} else if (userType.equalsIgnoreCase("10376")) {
			user = (JSONObject) userdata.get("10376");
		} else if (userType.equalsIgnoreCase("10657")) {
			user = (JSONObject) userdata.get("10657");
		} else if (userType.equalsIgnoreCase("10658")) {
			user = (JSONObject) userdata.get("10658");
		} else if (userType.equalsIgnoreCase("10659")) {
			user = (JSONObject) userdata.get("10659");
		} else if (userType.equalsIgnoreCase("10660")) {
			user = (JSONObject) userdata.get("10660");
		} else {
			try {
				throw new Exception("Invalid User Type Requested");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//String url = (String) user.get("url");
		String username = (String) user.get("username");
		String password = (String) user.get("password");

		List<String> userdetail = new ArrayList<String>();
		// userdetail.add(url);
		userdetail.add(username);
		userdetail.add(password);
		return userdetail;
	}

	/** this method returns url **//*
									 * public String getURL(String userType) { log.info("Getting url for type " +
									 * userType); return getUserLogin(userType).get(0); }
									 */

	/** this method returns user name **/
	public String getUserName(String userType) {
		log.info("Getting user name for type " + userType);
		return getUserLogin(userType).get(0);
	}

	/** this method returns password **/
	public String getPassword(String userType) {
		log.info("Getting user password for type " + userType);
		return getUserLogin(userType).get(1);
	}
}
