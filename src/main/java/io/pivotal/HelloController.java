package io.pivotal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pivotal on 10/2/15.
 */

@RestController
public class HelloController {

    @RequestMapping(value = "/resource/hello")
    public Map hello () throws InterruptedException {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", "1");
        map1.put("content", "Go swimming on Monday night");
        return map1;
    }
}

