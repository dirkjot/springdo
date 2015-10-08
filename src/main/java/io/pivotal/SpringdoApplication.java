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
        itemRepository.save(new Item("swim", "in the pool"));
        itemRepository.save(new Item("run", "around the park"));
        itemRepository.save(new Item("shop", "for new shoes"));
        itemRepository.save(new Item("drive", "to great viewpoint"));
        itemRepository.save(new Item("sleep", "at least 8 hours"));
    }

}
