
package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
        User defaultUser = userRepository.save(User.AdminUser("Navya", "secret", "n@example.com"));
        User secondUser = userRepository.save(new User("Dirk", "secret", "t@example.com"));


        itemRepository.save(new Item("swim", "in the pool", defaultUser));
        itemRepository.save(new Item("run", "around the park", defaultUser));
        itemRepository.save(new Item("shop", "for new shoes", defaultUser));
        itemRepository.save(new Item("drive", "to great viewpoint", secondUser));
        itemRepository.save(new Item("sleep", "at least 8 hours", secondUser));

        userRepository.findAll().forEach(user -> inMemoryUserDetailsManager.createUser(user));
        // example of how you would change an existing user, this will throw Illegal State Exception if the username does not exist
        //inMemoryUserDetailsManager.updateUser(new User("t", "tt", "xx"));

    }
}
