package io.pivotal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;
import java.util.*;

/**
 * Created by pivotal on 9/21/15.
 */

@RestController
public class ListOfItemsController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value="/resource/list/", method= RequestMethod.GET)
    List listOfItems(Principal principal) {
        // we know they are logged in as spring security is set up that way
        String username = principal.getName();
        User user = userRepository.findUserByName(username);
        Iterable<Item> iterable = itemRepository.findItemByUser(user);
        List<Item> result = new ArrayList<>();
        iterable.iterator().forEachRemaining(result::add);
        return result;
    }

    @RequestMapping(value="/resource/dummylist/", method= RequestMethod.GET)
    List listOfDummyItems() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", "1");
        map1.put("title", "Go for a swim");
        map1.put("content", "Go swimming on Monday night");
        map1.put("done","yes");
        result.add(0, map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", "2");
        map2.put("title", "Visit farmer's market");
        map2.put("content", "Buy dairy and eggs at farmers market on Wednesday");
        map2.put("done","no");
        result.add(1, map2);

        return result;
    }

    /**
     * Function to determine who is logged in.
     * @param principal
     * @return
     */
    @RequestMapping(value="resource/who/")
    String whoIsLoggedIn(Principal principal) throws JsonProcessingException {
        String name = principal.getName();
        // angular only wants json hashes as input, we can create one by hand or use the jackson json
        // library which is already a dependency of spring boot
        ObjectMapper tojson = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        name = tojson.writeValueAsString(map);
        // we could also just return the name , but do not forget to quote it!
        // we like to return hashes so we can expand the api at a later time
        //    name = "\"" + name + "\"";
        return name;
    }

    /**
     * Change the 'done' flag on a todo item
     * @param id: of todo item
     * @param done:  yes or no
     * @return ok message
     */
    @RequestMapping(value="/resource/done/{id}/{done}/", method=RequestMethod.POST)
    String postDoneUpdate(@PathVariable long id, @PathVariable String done) {
        Item item = itemRepository.findOne(id);
        if (done.equals("yes") || done.equals("no")) {
            item.done = done;
        } else {
          System.out.println("Invalid argument to postDoneUpdate:  " + done);
        }
        itemRepository.save(item);
        return "['ok']";
    }

    // post('/resource/save/<id>/title/content/done/')

    /**
     * Save a todo item
     * @param id
     * @param title
     * @param content
     * @param done
     * @return
     */
    @RequestMapping(value="/resource/save/{id}/{title}/{content}/{done}/", method=RequestMethod.POST)
    String postSaveUpdate(@PathVariable long id, @PathVariable String title, @PathVariable String content, @PathVariable String done) {
        Item item = itemRepository.findOne(id);
        if (done.equals("yes") || done.equals("no")) {
            item.done = done;
        } else {
            System.out.println("Invalid argument to postSaveUpdate:  " + done);
        }
        item.title = title;
        item.content = content;
        itemRepository.save(item);
        return "['ok']";
    }




    //  XX post('/resource/new/{title}/{content}/{done}/')
    //  post('/resource/create/') -> returns id.


    /**
     * Create a new todo item
     * @return full item representation, with empty fields
     */
    @RequestMapping(value="/resource/create/", method=RequestMethod.GET)
    Item postSaveUpdate(Principal principal) {
        User user = userRepository.findUserByName(principal.getName());
        Item item = itemRepository.save(Item.empty(user));
        return item;
    }

}
