
package commonHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import base.Config;

public class ResourceHelper {

	private Logger log = Logger.getLogger(ResourceHelper.class.getName());

	/** file path is set from config file **/
	public String getFilepath(String fileName) {
		Config config = new Config();
		String filePath = null;
		try {
			log.info("Getting File Path " + fileName);
			if (fileName.toUpperCase().contains("XLS")) {
				filePath = getBaseResourcePath() + "/src/main/resources/data/Excel/" + config.getExcelFileName();
			} else if (fileName.toUpperCase().contains("JSON")) {
				filePath = getBaseResourcePath() + "/src/main/resources/data/Json/" + config.getJsonFileName();
				log.info("The requested file path for file name " + fileName + " is " + filePath);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}

	/** returns the type of file based on its extension **/
	public String getFileExtention(String fileName) {
		String fileExtensionName = null;
		fileExtensionName = fileName.substring(fileName.lastIndexOf('.'));
		log.info("File Extension is " + fileExtensionName);
		return fileExtensionName;
	}

	/** creates file object for given file path **/
	public File getFileObject(String filename) {
		String filePath = this.getFilepath(filename);
		log.info("Creating File Object for file path " + filePath);
		File file = new File(filePath);
		return file;
	}

	/** getting project directory base path **/
	public String getBaseResourcePath() {
		log.info("getting project directory base path");
		String path = System.getProperty("user.dir");
		return path;
	}

	/** getting input stream object for given file name **/
	public FileInputStream getResourceInputStream(String filename) {
		File file = getFileObject(filename);
		log.info("getting input stream object for given file " + filename);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** getting output stream object for given file name **/
	public FileOutputStream getResourceOutputStream(String filename) {
		File file = getFileObject(filename);
		log.info("getting input stream object for given file " + filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			return fos;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}