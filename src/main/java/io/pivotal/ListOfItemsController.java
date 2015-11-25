package io.pivotal;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pivotal on 9/21/15.
 */

@RestController
public class ListOfItemsController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @JsonView(JsonViews.ItemList.class)
    @RequestMapping(value = "/resource/list/", method = RequestMethod.GET)
    public List listOfItems(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Iterable<Item> iterable = itemRepository.findByUser(user);
        List<Item> result = new ArrayList<>();
        iterable.iterator().forEachRemaining(result::add);
        return result;
    }

    @RequestMapping(value = "/resource/dummylist/", method = RequestMethod.GET)
    public List listOfDummyItems() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", "1");
        map1.put("title", "Go for a swim");
        map1.put("content", "Go swimming on Monday night");
        map1.put("done", "yes");
        result.add(0, map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", "2");
        map2.put("title", "Visit farmer's market");
        map2.put("content", "Buy dairy and eggs at farmers market on Wednesday");
        map2.put("done", "no");
        result.add(1, map2);

        return result;
    }


    /**
     * Change the 'done' flag on a todo item
     *
     * @param id:   of todo item
     * @param done: yes or no
     * @return ok message
     */
    @RequestMapping(value = "/resource/done/{id}/{done}/", method = RequestMethod.POST)
    String postDoneUpdate(@PathVariable long id, @PathVariable String done) {
        Item item = itemRepository.findOne(id);
        if (done.equals("yes") || done.equals("no")) {
            item.done = done;
        } else {
            System.out.println("Invalid argument to postDoneUpdate:  " + done);
        }
        itemRepository.save(item);
        return "[\"ok\"]";
    }


    /**
     * Save a todo item
     *
     * @param id
     * @param title
     * @param content
     * @param done
     * @return
     */
    @RequestMapping(value = "/resource/save/{id}/{title}/{content}/{done}/", method = RequestMethod.POST)
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
        return "[\"ok\"]";
    }


    /**
     * Create a new todo item
     *
     * @return full item representation, with empty fields
     */
    @JsonView(JsonViews.ItemList.class)
    @RequestMapping(value = "/resource/create/", method = RequestMethod.POST)
    Item postCreate(Principal principal) {
        principal = fixPrincipalForTest(principal);
        User user = userRepository.findByUsername(principal.getName());
        if (user != null) {
            Item item = itemRepository.save(Item.empty(user));
            return item;
        }
        return null;
    }


    /**
     * A delete method.  In line with traditional REST, we use the post method here.
     */
    @RequestMapping(value = "/resource/delete/{id}", method = RequestMethod.POST)
    // DPJ
    String deleteItem(@PathVariable long id) {
        itemRepository.delete(id);
        return "[\"ok\"]";
    }


    /**
     * Helper function to make SpringSecurity work under test, without enabling it globally
     *
     */
    public Principal fixPrincipalForTest(Principal principal) {
        if (principal == null) {
            principal = SecurityContextHolder.getContext().getAuthentication();
            if (principal == null) {
                System.err.println("Unauthorized request, principal is null");
            }
        }
        return principal;
    }
}
