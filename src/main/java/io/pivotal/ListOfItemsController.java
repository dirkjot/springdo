package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by pivotal on 9/21/15.
 */

@RestController
public class ListOfItemsController {

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value="/resource/list/", method= RequestMethod.GET)
    List listOfItems() {
        Iterable<Item> iterable = itemRepository.findAll();
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

    @RequestMapping(value="/resource/done/{id}/{state}/", method= RequestMethod.POST)
    String postDoneUpdate(@PathVariable long id, @PathVariable String state) {
        Item item = itemRepository.findOne(id);
        if (state.equals("yes") || state.equals("no")) {
            item.done = state;
        } else {
          System.out.println("Invalid argument to postDoneUpdate:  " + state);
        }
        itemRepository.save(item);
        return "['ok']";
    }





}
