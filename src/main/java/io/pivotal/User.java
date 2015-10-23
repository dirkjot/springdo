package io.pivotal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pivotal on 9/25/15.
 */

@Entity
public class User implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    @JsonIgnore
    @OneToMany(mappedBy = "user")  // what this is called on the 'many' side
    private List<Item> items = new ArrayList<>();

    @JsonView(JsonViews.ItemList.class)
    private String username;  // login credential

    @JsonView(JsonViews.UserDetails.class)
    private String password;

    @JsonView(JsonViews.UserDetails.class)
    private String email;  // for password reminders etc

    @JsonView(JsonViews.UserDetails.class)
    private Boolean isAdmin = false;  // has access to admin pages?

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
     * Returns the list of items owned by this user (via JPA magic)
     *
     * * @return a List of Item objects
     */
    public List<Item> getItems() {
        return items;
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
        return username;
    }

    /**
     * Returns the isAdmin property
     *
     */
    public Boolean isAdmin() {
        return isAdmin;
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
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * JPA requires a zero argument constructor
     */
    public User() {
        this.username = "";
        this.password = "";
        this.email = "";
    }

    /**
     * Factory function to create Admin users, which are like normal users but with isAdmin set
     *
     * @param username
     * @param password
     * @param email
     * @return User object
     */
    public static User AdminUser(String username, String password, String email) {
        User nw = new User();
        nw.username = username;
        nw.password = password;
        nw.email = email;
        nw.isAdmin = true;
        return nw;
    }

    // these two methods provide the information that a InMemoryUserDetailsManager expects (a key and a value)
    public String getAuthdbKey () {
        return username;
    }

    public String getAuthdbValue () {
        return password + ",ROLE_USER,enabled";  // TODO this should use our other variables
    }


}



