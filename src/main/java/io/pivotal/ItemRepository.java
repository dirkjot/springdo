package io.pivotal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRepository extends CrudRepository<Item, Long> {
  List<Item> findByUser(User user);
}
