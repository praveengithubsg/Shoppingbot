
package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import commonHelper.BrowserFactory;
import fileReader.DataHandlers;
import testRunner.CucumberBase;

public class Config {

	Properties config;
	private final Logger log = Logger.getLogger(Config.class.getName());
	private Properties prop;
	private String filepath;
	//public static BrowserFactory browserFactory;
	DataHandlers dataReader=new DataHandlers();

	/** This methods creates config object **/
	public Properties createConfigObject() {
		log.info("Creating config object");
		FileInputStream ip;
		this.setConfigPath();
		try {
			ip = new FileInputStream(new File(filepath));
			prop = new Properties();
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/** This methods sets values to prop.properties **/
	public void setConfigFileValue(String key, String value) {
		checkToCreateProp();
		log.info("Setting values to config file for key " + key + " and value " + value);
		try {
			this.setConfigPath();
			createConfigObject();
			prop.setProperty(key, value);
			FileOutputStream fos = new FileOutputStream(new File(filepath));
			prop.store(fos, "save");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** This methods returns implicit wait from property file **/
	public int getImplicitTimeoutInSec() {
		checkToCreateProp();
		log.info("getting implicit wait from property file in sec");
		return Integer.parseInt(prop.getProperty("ImplicitTimeoutInSec"));
	}

	/** This methods returns explicit wait from property file **/
	public int getExplicitTimeoutInSec() {
		checkToCreateProp();
		log.info("getting explicit wait from property file in sec");
		return Integer.parseInt(prop.getProperty("ExplicitTimeOutInSec"));
	}

	/** This methods returns page load wait from property file **/
	public int getPageLoadTimeoutInSec() {
		checkToCreateProp();
		log.info("getting page load timeout from property file in sec");
		return Integer.parseInt(prop.getProperty("PageLoadTimeoutInSec"));
	}

	/** This methods returns polling wait from property file **/
	public int getPollingTimeoutInMilliSec() {
		checkToCreateProp();
		log.info("getting polling wait from property file in milli sec");
		return Integer.parseInt(prop.getProperty("PollingTimeoutInMilliSec"));
	}

	/** This methods returns site url from property file 
	 * @throws IOException **/
	public String getSiteURL(String cdCred)  {
		checkToCreateProp();
		
		try {
			cdCred= this.getCdCredentials();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		log.info("getting URL based on the CDCRED user, from property file");
		String url = prop.getProperty("URL"+cdCred);
		return url;
	}

	/** Returns Excel Data For Key **/
	public String getExcelValue(String key) {
		log.info("getting excel value against key value having sheet and key");
		String country = key.substring(0, key.indexOf("_"));
		String sheet = country;
		String keyValue = key.replace(country, "").replace("_", "");
		DataHandlers data = new DataHandlers();
		return data.getMapData(sheet, keyValue);
	}

	/** This methods sets excel value against key value having key and value **/
	public void setExcelValue(String key, String value) {
		log.info("setting excel value against key value having sheet and key");
		String country = key.substring(0, key.indexOf("_"));
		String sheet = country;
		try {
			DataHandlers data = new DataHandlers();
			data.setCellValue(sheet, key, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** This methods returns excel file name from property file **/
	public String getExcelFileName() {
		checkToCreateProp();
		log.info("getting excel file name from property file");
		String supplier = CucumberBase.returnSupplierCode();
		System.out.println("Supplier code is>>>>: "+supplier);
		String ecxelName = prop.getProperty("ExcelFileName"+supplier);
		return ecxelName;		
	}

	/** This methods returns json file name from property file **/
	public String getJsonFileName() {
		checkToCreateProp();
		log.info("getting Json file name from property file");
		return prop.getProperty("JsonFileName");
	}

	/** returns value for input key from property file **/
	public String getConfigValue(String key) {
		checkToCreateProp();
		String value = prop.getProperty(key);
		log.info("The value for key " + key + " is " + value);
		return value;
	}

	/** creates prop object if null **/
	private void checkToCreateProp() {
		if (prop == null) {
			log.info("Setting Config object if null");
			this.createConfigObject();
		}
	}

	/** setting config file path **/
	private void setConfigPath() {
		log.info("Setting Config Path");
		filepath = "src/main/resources/config/Config.properties";
	}

	/** setting new excel key value **/
	public void setNewExcelValue(String key, String value) {
		log.info("setting excel value against key value having sheet and key");
		String country = key.substring(0, key.indexOf("_"));
		String sheet = country;
		String keyValue = key.substring(key.indexOf("_") + 1);
		DataHandlers data = new DataHandlers();
		data.setNewCellValue(sheet, keyValue, value);
	}

	public String getBaseUrl() {
		log.info("Getting base url");
		return createConfigObject().getProperty("BaseURL");
	}

	
	/** This methods returns CDCRED value from property file **/
	public int getcdCred() {
		checkToCreateProp();
		log.info("getting CDCRED value from property file ");
		return Integer.parseInt(prop.getProperty("CDCRED"));
	}
	
	
	/** This methods returns CDPRODUCT value from property file **/
	public int getcdProduct() {
		checkToCreateProp();
		log.info("getting CDPRODUCT value from property file ");
		return Integer.parseInt(prop.getProperty("CDPRODUKT"));
	}
	
	/** This methods returns AANTAL value from property file **/
	public int getAantal() {
		checkToCreateProp();
		log.info("getting AANTAL value from property file ");
		return Integer.parseInt(prop.getProperty("AANTAL"));
	}
	
	/** This methods returns PRIJS value from property file **/
	public int getPrijs() {
		checkToCreateProp();
		log.info("getting PRIJS value from property file ");
		return Integer.parseInt(prop.getProperty("PRIJS"));
	}
	
	/** This methods returns excel sheet name from property file **/
	public String getExcelSheetName() {
		checkToCreateProp();
		log.info("getting excel sheet from property file ");
		return prop.getProperty("ExcelSheetName");
	}
	
	public int getRow() {
		checkToCreateProp();
		log.info("getting row value from property file ");
		return Integer.parseInt(prop.getProperty("row"));
	}
	
	/* This methods returns Robo quantity header value from property file */
	public String getRoboQtyHeader() {
	checkToCreateProp();
	log.info("getting Robo quantity header value from property file ");
	return prop.getProperty("RoboQtyHeaderName");
	}

	/* This methods returns Robo Log header value from property file */
	public String getRoboLogHeader() {
	checkToCreateProp();
	log.info("getting robo log header value from property file ");
	return prop.getProperty("RoboLogHeaderName");
	}

	/* This methods returns Robo qty value from property file */
	public int getRoboQty() {
	checkToCreateProp();
	log.info("getting Robo qty value from property file ");
	return Integer.parseInt(prop.getProperty("RoboQtyCol"));
	}

	/* This methods returns Robo log value from property file */
	public int getRoboLog() {
	checkToCreateProp();
	log.info("getting Robo log value from property file ");
	return Integer.parseInt(prop.getProperty("RoboLogCol"));
	}
	
	public String getCdCredentials()  {
		String cdCred = null;
		checkToCreateProp();		
		try {
			cdCred= dataReader.getIntegerCellValue(this.getExcelSheetName(),this.getRow()+1,this.getcdCred());
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return cdCred;
	}
	
	public int getCdProductLength() {
		checkToCreateProp();
		log.info("getting CD Product length value from property file ");
		return Integer.parseInt(prop.getProperty("cdProductLength"));
		}
	
	public String getLogoutCondition() {
		checkToCreateProp();
		log.info("getting Logout condition value from property file ");
		return prop.getProperty("Logout");
		}
	
	public String getCloseBrowserCondition() {
		checkToCreateProp();
		log.info("getting Close Browser condition value from property file ");
		return prop.getProperty("CloseBrowser");
		}
	
	public String getPrefixZeroesToItemCode() {
		checkToCreateProp();
		log.info("getting PrefixZeroesToItemCode value from property file ");
		return prop.getProperty("PrefixZeroesToItemCode");
		}
	
	public static void infoBox(String infoMessage, String titleBar) {
		
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

}
