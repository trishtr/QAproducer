package qaproducer;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ExcelWriter {

    public static void main(String[] args) throws IOException, ParseException {
        
        JSONReader reader = new JSONReader();
        XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
		int rownum = 0;
		JSONParser jsonparser = new JSONParser();
        List<String> dataJsonLst = new ArrayList<>();
        
        List<String> absolutePaths = reader.getFileName();

        for(String absolutePath: absolutePaths){
            FileReader filereader = new FileReader(absolutePath);
            Object obj = jsonparser.parse(filereader);
            JSONObject dataJSONObject = (JSONObject) obj;
            String dataJson = dataJSONObject.toString();
            dataJsonLst.add(dataJson);
        }
        for(String data : dataJsonLst){   
		    XSSFRow row = sheet.createRow(rownum++);
		    row.createCell(0).setCellValue(data);
			
        }
		FileOutputStream fo = new FileOutputStream("./qaproducer/TestClientData.xlsx");
		workbook.write(fo);
		fo.close();
		System.out.println("Excel file is created");
		
		
		
  
   
        
    }
    
}
