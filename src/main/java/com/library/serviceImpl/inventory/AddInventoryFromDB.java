package com.library.serviceImpl.inventory;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import com.library.strategies.ProcessingStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AddInventoryFromDB extends InventoryAddition {

    public AddInventoryFromDB(Source source, Map<Object,Object> map, ProcessingStrategy processingStrategy){
        super(source,map,processingStrategy);
    }
    public Map<Book,String> fetchAndAddInventory(){
        Map<Book,String> bookStringMap=new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/file.csv"))) {
            bookStringMap=processingStrategy.process(br,addedBy,source);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as needed
        }
        return bookStringMap;
    }


}
