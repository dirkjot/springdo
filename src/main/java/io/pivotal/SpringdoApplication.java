package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringdoApplication implements CommandLineRunner {

    
    @Autowired
    ItemRepository itemRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringdoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        // save a couple of customers
        itemRepository.save(new Item("Jack", "Bauer"));
        itemRepository.save(new Item("Chloe", "O'Brian"));
        itemRepository.save(new Item("Kim", "Bauer"));
        itemRepository.save(new Item("David", "Palmer"));
        itemRepository.save(new Item("Michelle", "Dessler"));
    }

}
