
package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

public class StringUtility {
	private Logger log = Logger.getLogger(StringUtility.class.getName());
	String orderIDPattern = "[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";

	/** extracts int value from string **/
	public int getIntValue(String input) {
		log.info("Get integer value for input string " + input);
		int intValue = 0;
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(input);
		while (m.find()) {
			intValue = Integer.parseInt(m.group());

		}
		return intValue;
	}

	/** returns number of occurance of substring in given string **/
	public int countSubStringInString(String str, String findStr) {
		int count = 0, fromIndex = 0;
		log.info("Getting number of occurance of substring in given string");
		while ((fromIndex = str.indexOf(findStr, fromIndex)) != -1) {
			count++;
			fromIndex++;

		}
		return count;
	}

	/** returns attribute indexes as list **/
	public List<Integer> allOccuranceIndex(String searchableString, String keyword) {
		log.info("Getting indexes of speific attributes in string");
		List<Integer> value = new ArrayList<Integer>();
		int index = searchableString.indexOf(keyword);
		value.add(index);
		while (index >= 0) {
			index = searchableString.indexOf(keyword, (index + 1));
			if (index != -1) {
				value.add(index);
			}
		}
		return value;

	}

	/** Returns nth index for substring **/
	public int ordinalIndexOf(String str, String c, int n) {
		log.info("Returning index of nth occurance for substring");
		int pos = str.indexOf(c);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

	/** returns order id from string **/
	public String getOrderNumber(String string) {
		log.info("Getting order ID from given string");
		String[] arr = string.split(" ");
		String orderID = null;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].matches(orderIDPattern)) {
				orderID = arr[i];
			}
		}
		if (orderID == null) {
			try {
				throw new Exception("No orderID found for input String");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("OrderID = " + orderID);
		return orderID;
	}

	/** validates email id pattern **/
	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		log.info("Verifying whether the string is email");
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	/** validates order id pattern **/
	public boolean isOrderID(String string) {
		log.info("Verifying whether the string matches sampleproj brands order id pattern");
		return string.matches(orderIDPattern);
	}

	/** returns sku from string **/
	public String getSkuFromLink(String link) {
		log.info("Getting SKU attribute value");
		int indexStart = link.lastIndexOf("/");
		int indexEnd = link.lastIndexOf(".");
		return link.substring(indexStart + 1, indexEnd);
	}

	/** checks whether the string contains numeric values **/
	public boolean checkContainsNumericValue(String input) {
		log.info("Verifying whether the string contains numeric value");
		return Pattern.compile("[0-9]").matcher(input).find();
	}

	/** Getting decimal value from given string **/
	public float getDecimalValue(String str) {
		log.info("Getting decimal value from given string");
		String regex = "[0-9]*.*[0-9]";
		str = removeChar(str, '$');
		str = removeChar(str, '+');
		str = removeChar(str, '(');
		str = removeChar(str, ')');
		str = removeChar(str, 'C');
		String[] arr = str.split(" ");

		String decimalValue = null;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i].trim().matches(regex)) {
				decimalValue = arr[i].trim();
			}
		}
		float decimalValue1 = Float.parseFloat(decimalValue);
		return decimalValue1;
	}

	/** this method removes all occurance of specific character in string **/
	String removeChar(String s, char c) {
		log.info("Removing all occurance of specific character in string");
		int j, count = 0, n = s.length();
		char[] t = s.toCharArray();
		for (int i = j = 0; i < n; i++) {
			if (t[i] != c)
				t[j++] = t[i];
			else
				count++;
		}

		while (count > 0) {
			t[j++] = '\0';
			count--;
		}
		String temp = "";
		for (int i = 0; i < t.length; i++) {

			temp = temp + t[i];
		}
		return temp;
	}

	/** this method random gmail email id **/
	public String generateRandomEmailID() {
		log.info("Returning random gmail email id");
		String firstName = RandomStringUtils.randomAlphabetic(5);
		String lastName = RandomStringUtils.randomAlphabetic(5);
		String email = firstName + "." + lastName + RandomStringUtils.randomNumeric(3) + "@gmail.com";
		return email;
	}
}