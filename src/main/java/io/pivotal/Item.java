package io.pivotal;

/**
 * Created by pivotal on 9/21/15.
 */


import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
    public String title;
    public String content;
    public String done;

    @ManyToOne
    private User userId;

    public Item(String title, String content){
        this.title = title;
        this.content = content;
        this.done = "no";
    }

    public Item() {
        this.title = "";
        this.content = "";
        this.done = "no";
    }

    @Override
    public String toString() {
        return String.format(
                "Item[id=%d, title='%s', done='%s']",
                id, title, done);
    }

}
