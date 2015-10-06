package io.pivotal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pivotal on 9/21/15.
 */

@RestController
public class ListOfItemsController {

    @RequestMapping("/resource/list")
    List simpleminded() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", "1");
        map1.put("title", "Go for a swim");
        map1.put("content", "Go swimming on Monday night");
        result.add(0, map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", "2");
        map2.put("title", "Visit farmer's market");
        map2.put("content", "Buy dairy and eggs at farmers market on Wednesday");
        result.add(1, map2);

        return result;
    }
}
