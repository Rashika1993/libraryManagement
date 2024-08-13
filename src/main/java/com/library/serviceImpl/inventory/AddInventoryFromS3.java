package com.library.serviceImpl.inventory;

import com.library.entities.Book;
import com.library.enums.Source;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import com.library.strategies.ProcessingStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class AddInventoryFromS3 extends InventoryAddition {

    public AddInventoryFromS3(Source source, Map<Object,Object> map, ProcessingStrategy processingStrategy){
        super(source,map,processingStrategy);
    }
    @Override
    public Map<Book,String> fetchAndAddInventory(){
        /* Fetch data from S3
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        String bucketName = "your-bucket-name";
        String key = "path/to/your-file.csv";
        try {
            S3Object s3Object = s3Client.getObject(bucketName, key);
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord csvRecord : csvParser) {
                // Access individual columns by header name
                String column1 = csvRecord.get("Column1");
                String column2 = csvRecord.get("Column2");
                // Process the data
                System.out.println("Column1: %s, Column2: %s%n", column1, column2);
            }
            csvParser.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
       }
         */
        Map<Book,String> bookStringMap=new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/file.csv"))) {
            bookStringMap=processingStrategy.process(br,addedBy,source);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as needed
        }
        return bookStringMap;
    }

}

