package io.pivotal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pivotal on 10/1/15.
 */
@RestController
public class ListOfItemsController {

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String mainPage() {
        return "<html><body><h1>List of ToDO items</h1></body></html>";
    }
}
