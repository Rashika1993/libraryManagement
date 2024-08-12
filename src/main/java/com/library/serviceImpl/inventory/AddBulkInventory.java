package com.library.serviceImpl.inventory;
import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.BooksService;
import com.library.services.inventory.InventoryAddition;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AddBulkInventory extends InventoryAddition {
    MultipartFile multipartFile;

    public AddBulkInventory(Source source, Map<Object,Object> map){
        super(source,map);
    }
    //The inventory addition is being done synchronously
    // However, we can batch process it.
    // We can align multiple worker threads which will run in parallel
    // which will help in reducing latency and utilising CPU cores efficiently
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
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 1) {
                    String bookName = fields[0].trim();
                    Book book=new Book();
                    book.setBookName(bookName);
                    book.setAddedBy(addedBy);
                    book.setSource(source);
                    books.add(new Book(book));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as needed
        }
        return books;
    }
}
