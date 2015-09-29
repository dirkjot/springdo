package io.pivotal;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pivotal on 9/29/15.
 */

@RestController
public class AdminController {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    // this is identical to the old listOfItems method at /resource/list/
    // that one has now changed to only list items of one user.
    @JsonView(JsonViews.ItemList.class)
    @RequestMapping(value = "/resource/admin/list/", method= RequestMethod.GET)
    List adminList(Principal principal) {
        // TODO we need to check we have admin permission
        Iterable <Item> iterable = itemRepository.findAll();
        List<Item> result = new ArrayList<>();
        iterable.iterator().forEachRemaining(result::add);
        return result;
    }

    /**
     * Show details for this user.  This does not work so well right now because we
     * changed to serialization of a user to {"name": <NAME>} in User.java.
     *
     * @param principal
     * @param username
     * @return
     */
    @JsonView(JsonViews.UserDetails.class)
    @RequestMapping(value = "/resource/user/{username}/", method=RequestMethod.GET)
    User adminShowUser(Principal principal, @PathVariable String username) {
        // TODO we need to check we have admin permission
        User user = userRepository.findUserByName(username);
        return user;
    }
}
