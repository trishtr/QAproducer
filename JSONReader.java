package qaproducer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

        public static List<String> getJsonData() throws IOException, ParseException {
        //public static void main(String[] args) throws IOException, ParseException {
            
        
        JSONParser jsonparser = new JSONParser();
        List<String> dataJsonLst = new ArrayList<>();
        
        List<String> absolutePaths = getFileName();

        for(String absolutePath: absolutePaths){
            FileReader reader = new FileReader(absolutePath);
            Object obj = jsonparser.parse(reader);
            JSONObject dataJSONObject = (JSONObject) obj;
            String dataJson = dataJSONObject.toString();
            dataJsonLst.add(dataJson);
            System.out.println(dataJson);
        }  
        System.out.println(absolutePaths.size());
        return dataJsonLst;

    }

    public static List<String> getFileName(){    
        String fileName = "";
        List<String> fileNameLst = new ArrayList<>();
       

        for(int i = 1; i<=31; i++){
            try {
            
            String folderPath = "./qaproducer/2022/11/" + i;
            //System.out.println(folderPath);

            File folder = new File(folderPath);
            
            File[] files = folder.listFiles();

            for (File file:files){
                if(file.isFile()){
                    //System.out.println("File -> "+ file.getName());
                    fileName = file.getAbsolutePath();
                    fileNameLst.add(fileName);
                    //System.out.println(fileName);
                }
                else if(file.isDirectory()){
                    fileName = file.getAbsolutePath();
                    //System.out.println("Folder -> " + file.getName());
                
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    System.out.println(fileNameLst.size());
    return fileNameLst;
}
}

    
    

