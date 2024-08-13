package com.library.serviceImpl.inventory;
import com.library.entities.Book;
import com.library.enums.Source;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import com.library.strategies.ProcessingStrategy;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AddBulkInventory extends InventoryAddition {
    MultipartFile multipartFile;

    public AddBulkInventory(Source source, Map<Object,Object> map, ProcessingStrategy processingStrategy) {
        super(source,map,processingStrategy);
        this.multipartFile= (MultipartFile) map.get("file");
    }

    @Override
    public Map<Book,String> fetchAndAddInventory(){
        Map<Book,String> bookStringMap=new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            bookStringMap=processingStrategy.process(br,addedBy,source);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as needed
        }
        return bookStringMap;
    }
}
