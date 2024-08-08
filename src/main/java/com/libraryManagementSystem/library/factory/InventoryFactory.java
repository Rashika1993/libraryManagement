package com.libraryManagementSystem.library.factory;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.serviceImpl.inventory.AddBulkInventory;
import com.libraryManagementSystem.library.serviceImpl.inventory.AddInventoryFromS3;
import com.libraryManagementSystem.library.services.inventory.InventoryAddition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InventoryFactory {
    public static InventoryAddition source(Source source, Map<Object,Object> map) {
        if(source==Source.S3){
          return new AddInventoryFromS3(source,map);
        }else if(source==Source.MYSQL_DB){
            return new AddInventoryFromS3(source,map);
        }else{
            return new AddBulkInventory(source,map);
        }
    }
}
