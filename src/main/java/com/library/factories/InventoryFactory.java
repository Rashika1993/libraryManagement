package com.library.factories;

import com.library.enums.Source;
import com.library.serviceImpl.inventory.AddBulkInventory;
import com.library.serviceImpl.inventory.AddInventoryFromS3;
import com.library.services.inventory.InventoryAddition;

import java.util.Map;

public class InventoryFactory {
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
