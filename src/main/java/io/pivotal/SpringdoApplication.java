package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class SpringdoApplication implements CommandLineRunner {

    
    @Autowired
    ItemRepository itemRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringdoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        // save a couple of fake to do items
        itemRepository.save(new Item("Jack", "Bauer lorem ipsum and a very long story indeed. Bauer lorem ipsum and a very long story indeed.  Bauer lorem ipsum and a very long story indeed.  Bauer lorem ipsum and a very long story indeed. "));
        itemRepository.save(new Item("Chloe", "O'Brian"));
        itemRepository.save(new Item("Kim", "Bauer"));
        itemRepository.save(new Item("David", "Palmer"));
        itemRepository.save(new Item("Michelle", "Dessler"));
    }

}

