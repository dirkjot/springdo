
package io.pivotal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by pivotal on 9/25/15.
 */

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
