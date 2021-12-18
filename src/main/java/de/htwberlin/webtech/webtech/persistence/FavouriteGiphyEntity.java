package de.htwberlin.webtech.webtech.persistence;

import javax.persistence.*;

@Entity(name = "giphys")
public class FavouriteGiphyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    private String link;

    public FavouriteGiphyEntity( String title, String link) {

        this.title = title;
        this.link = link;
    }

    public FavouriteGiphyEntity() {

    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
