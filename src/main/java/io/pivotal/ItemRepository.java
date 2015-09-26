package io.pivotal;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
     // Item findItembyTitle(String title);
     // Collection<Item> findItembyUser(User user);
}
