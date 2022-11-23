package qaproducer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static List<String> ReadExcel() throws IOException {
        //String excelFilePath = "/home/ec2-user/trang_test/KafkaProducerDatasets.xlsx";
		 	String excelFilePath = "./qaproducer/TestClientData.xlsx";
	        

             FileInputStream inputStream = new FileInputStream(excelFilePath);
 
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
             XSSFSheet sheet = workbook.getSheet("Sheet1");
 
 
             int rows = sheet.getLastRowNum();
             //System.out.println(rows);
             int cols = sheet.getRow(1).getLastCellNum();
             //System.out.println(cols);
 
             List<String> messageListInString = new ArrayList<>();
 
             for(int r = 0; r <= rows; r++)
             {
                 XSSFRow row = sheet.getRow(r);
                 for(int c = 0; c<cols; c++){
                     XSSFCell cell = row.getCell(c);
                     //System.out.println("Print the mess: ");
                     //System.out.println(cell.getStringCellValue());
                     messageListInString.add(cell.getStringCellValue());
                     }
                 }
             return messageListInString;
             //System.out.println();
             //System.out.println(messageListInString);
 
            
             }
    }


