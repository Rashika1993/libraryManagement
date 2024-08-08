package com.libraryManagementSystem.library.serviceImpl.inventory;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.services.UserService;
import com.libraryManagementSystem.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddInventoryFromS3 extends InventoryAddition {
    @Autowired
    UserService userService;
    public AddInventoryFromS3(Source source, Map<Object,Object> map){
        super(source);
        if(map.get("user")!=null) {
            addedBy = userService.findById((Long) map.get("user"));
        }
    }

    public AddInventoryFromS3(Source source,Object additionParam){
        super(source);
    }
    @Override
    public Map<Book,String> addInventory(List<Book> books) {
        Map<Book,String> bookInventory=new HashMap<>();
        for(Book book:books) {
            Book addedBook=booksService.addBook(book);
            if(addedBook!=null){
                bookInventory.put(addedBook,"Book Addition Successful!");
            }else {
                bookInventory.put(addedBook,"Book Addition Failed!");
            }
        }
        return bookInventory;
    }

    @Override
    public List<Book> fetchInventory() {
        /*
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
                System.out.printf("Column1: %s, Column2: %s%n", column1, column2);
            }
            csvParser.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
       }
         */
        try (BufferedReader br = new BufferedReader(new FileReader("/books.csv"))) {
            String line;
            // Skip the header if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String bookName = fields[0].trim();
                    Book book=new Book();
                    book.setBookName(bookName);
                    book.setAddedBy(addedBy);
                    book.setSource(source);
                    books.add(new Book(book));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}

