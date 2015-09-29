package io.pivotal;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.View;

import javax.persistence.*;
import java.util.*;

/**
 * Created by pivotal on 9/25/15.
 */

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;


    @OneToMany(mappedBy = "user")  // what this is called on the 'many' side
    private Set<Item> items = new HashSet<>();


    private String name;  // login credential
    private String password;
    private String email;  // for password reminders etc



    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */

    class Operation implements GrantedAuthority {
        public String getAuthority() {
            return "ROLE_USER";
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Operation> authcollection = new ArrayList<Operation>();
        authcollection.add(new Operation());
        return authcollection;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    public String getUsername() {
        return name;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     *         (ie expired)
     */
    public boolean isAccountNonExpired(){
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
     * authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
     *         valid (ie expired)
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * Standard three argument constructor
     * @param name
     * @param password
     * @param email
     */
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    /**
     * JPA requires a zero argument constructor
     */
    public User() {
        this.name = "";
        this.password = "";
        this.email = "";
    }

    // these two methods provide the information that a InMemoryUserDetailsManager expects (a key and a value)
    public String getAuthdbKey () {
        return name;
    }

    public String getAuthdbValue () {
        return password + ",ROLE_USER,enabled";  // TODO this should use our other variables
    }

    /**
     * The Jackson json parser will use this to represent users
     * @return
     */

    @JsonValue
    Map toJson() {
        Map<String, String> result = new HashMap<>();
        result.put("name", this.getUsername());
        return result;
    }
}



