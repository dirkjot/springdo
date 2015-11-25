package io.pivotal;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Created by pivotal on 9/29/15.
 */

@RestController
public class AdminController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * adminList:  List of all items, owned by all users
     *
     * this is identical to the old listOfItems method at /resource/list/
     * that one has now changed to only list items of one user.
     * @param principal
     * @return list of items
     */

    @JsonView(JsonViews.ItemList.class)
    @RequestMapping(value = "/resource/admin/list/", method = RequestMethod.GET)
    public List adminList(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (user != null && user.isAdmin()) {
            Iterable<Item> iterable = itemRepository.findAll();
            List<Item> result = new ArrayList<>();
            iterable.iterator().forEachRemaining(result::add);
            return result;
        } else {
            return user.getItems();
        }
    }

    /**
     * userList:  List of usernames (strings)
     */
    @RequestMapping(value = "/resource/admin/userlist/", method = RequestMethod.GET)
    List<String> userList(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (user != null && user.isAdmin()) {
            Iterable <User> iterable = userRepository.findAll();
            List<String> result = new ArrayList<>();
            iterable.iterator().forEachRemaining((User u) -> {result.add(u.getUsername()); });
            return result;
        } else {
            System.err.println("Unauthorized request to /resource/admin/userlist/ by " + principal.getName());
            return new ArrayList<String>();
        }
    }

    /**
     * Show details for this user.  This returns the full user details .
     *
     * @return User object, with jsonview UserDetails profile (ie all details)
     */
    @JsonView(JsonViews.UserDetails.class)
    @RequestMapping(value = "/resource/user/{username}/", method = RequestMethod.GET)
    User adminShowUser(Principal principal, @PathVariable String username) {
        User user = userRepository.findByUsername(principal.getName());
        if (user != null && user.isAdmin()) {
            return userRepository.findByUsername(username);
        } else {
            return null;
        }
    }

    /**
     * Function to determine who is logged in returns non-sensitve user details
     * @param principal
     * @return Username in a JSON dict
     */
    @RequestMapping(value="/who/")
    String whoIsLoggedIn(Principal principal) throws JsonProcessingException {
        String name = "";
        if (principal != null) {
            name = principal.getName();
            // angular only wants json hashes as input, we can create one by hand or use the jackson json
            // library which is already a dependency of spring boot
            ObjectMapper tojson = new ObjectMapper();
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            name = tojson.writeValueAsString(map);
        } else {
            name = "{\"name\":\"\"}";
        }
        return name;
    }
}
