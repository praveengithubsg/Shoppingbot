package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private Properties properties;
	private final String propertyFilePath = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\config\\Config.properties";

	public ConfigReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitlyWait");
		if (implicitlyWait != null)
			return Long.parseLong(implicitlyWait);
		else
			throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
	}

	public String getApplicationUrl(String URL) {
		String url = null;
		if (URL.equalsIgnoreCase("SixthStreetUAEENurl"))
			url = properties.getProperty("SixthStreetUAEENurl");

		else if (URL.equalsIgnoreCase("TommyURL"))
			url = properties.getProperty("TommyURL");
		
		else if (URL.equalsIgnoreCase("SixthStreetKSAurl"))
			url = properties.getProperty("SixthStreetKSAurl");

		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

}