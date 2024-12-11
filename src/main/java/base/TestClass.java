package base;

import java.io.IOException;
import fileReader.DataHandlers;

public class TestClass {

	public static void main(String[] args) throws IOException {
		
		DataHandlers dataReader=new DataHandlers();
		Config config=new Config();
		  	  
		  
		  String cdCRED=""; //System.out.println(dataReader.getExcel);
		  
		  for(int i=1;i<dataReader.rowCount("TestData");i++) {
			  
			  
				  cdCRED=dataReader.getIntegerCellValue(config.getExcelSheetName(),i,config.getcdCred());
				  System.out.println("User ID: "+cdCRED); 
				  String cdProduct = dataReader.getIntegerCellValue(config.getExcelSheetName(),i,config.getcdProduct());
				  System.out.println("Item Code: "+cdProduct); 
				  String aantal = dataReader.getIntegerCellValue(config.getExcelSheetName(),i,config.getAantal());
				  System.out.println("Item Code: "+aantal); 
				  String prijs = dataReader.getIntegerCellValue(config.getExcelSheetName(),i,config.getPrijs());
				  System.out.println("Item Code: "+prijs); 
				  System.out.println("i = "+i);
			  }
		  
		 
		

	}

}
