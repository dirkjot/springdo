package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class SpringdoApplication implements CommandLineRunner {

    
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;
    
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

        userRepository.save(new User("Navya", "secret", "n@example.com"));
        userRepository.save(new User("t", "t", "t@example.com"));

        userRepository.findAll().forEach(user -> inMemoryUserDetailsManager.createUser(user));
        // example of how you would change an existing user, this will throw Illegal State Exception if the username does not exist
        //inMemoryUserDetailsManager.updateUser(new User("t", "tt", "xx"));

    }

}

