package io.pivotal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by pivotal on 9/25/15.
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    private String name;  // login credential
    private String password;
    private String email;  // for password reminders etc

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

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
        return password + ",ROLE_USER,enabled";
    }
}
