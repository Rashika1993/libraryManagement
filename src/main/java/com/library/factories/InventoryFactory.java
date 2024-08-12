package com.library.factories;

import com.library.enums.Source;
import com.library.serviceImpl.inventory.AddBulkInventory;
import com.library.serviceImpl.inventory.AddInventoryFromS3;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class InventoryFactory {
    @Autowired
    BooksService booksService;
    public static InventoryAddition getSourceService(Source source, Map<Object,Object> map) {
        if(source==Source.S3){
          return new AddInventoryFromS3(source,map);
        }else if(source==Source.MYSQL_DB){
            return new AddInventoryFromS3(source,map);
        }else{
            return new AddBulkInventory(source,map);
        }
    }
}
