package io.pivotal;

/**
 * Created by pivotal on 9/21/15.
 */


import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;



@Entity
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonView(JsonViews.ItemList.class)
    public long id;

    @JsonView(JsonViews.ItemList.class)
    public String title;

    @JsonView(JsonViews.ItemList.class)
    public String content;

    @JsonView(JsonViews.ItemList.class)
    public String done;

    @JsonView(JsonViews.ItemList.class)
    @ManyToOne
    public User user;

    /**
     * Normal constructor, with title, content and user
     * @param title
     * @param content
     * @param user
     */
    public Item(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.done = "no";
        this.user = user;
    }

    /**
     * JPA -REQUIRES- a zero argument constructor
     * we leave user unset here, which makes it so no user ever sees a note created this way,
     * even if it were to accidentally land in the database
     */
    public Item() {
        this.title = "";
        this.content = "";
        this.done = "no";
    }

    /**
     * Legacy constructor with a title and content
     * @param title
     * @param content
     */
    public Item(String title, String content){
        this.title = title;
        this.content = content;
        this.done = "no";
    }

    /**
     * A factory function instead of another constructor.  The main advantage
     * here is that we can give it a good name instead of having to remember what the constructor
     * with 1,2,3 or 4 arguments does again.
     *
     * @param user
     * @return empty item with user (owner) set.
     */
    public static Item empty(User user) {
        Item nw = new Item();
        nw.user = user;
        return nw;
    }

    /**
     * Nice way to format these on the terminal, it is good style to have one and it
     * make things more readable when debugging.
     * We specifically do not show the content (which could be long).
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return String.format(
                "Item[id=%d, title='%s', done='%s', user='%s']",
                id, title, done, user);
    }

}
