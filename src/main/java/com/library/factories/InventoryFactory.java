package com.library.factories;

import com.library.enums.Source;
import com.library.serviceImpl.inventory.AddBulkInventory;
import com.library.serviceImpl.inventory.AddInventoryFromDB;
import com.library.serviceImpl.inventory.AddInventoryFromS3;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import com.library.strategies.BatchProcessingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class InventoryFactory {

    public static InventoryAddition getSourceService(Source source, Map<Object,Object> map, BooksService booksService) {
        if(source==Source.S3){
          return new AddInventoryFromS3(source,map,new BatchProcessingStrategy(booksService));
        }else if(source==Source.MYSQL_DB){
            return new AddInventoryFromDB(source,map,new BatchProcessingStrategy(booksService));
        }else{
            return new AddBulkInventory(source,map,new BatchProcessingStrategy(booksService));
        }
    }
}
