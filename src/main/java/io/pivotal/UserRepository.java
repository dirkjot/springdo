package io.pivotal;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by pivotal on 9/25/15.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByName(String name);
}


