package de.htwberlin.webtech.webtech.web.api;

public class FavouriteGiphy {
    private long id;
    private String title;

    private String link;

    public FavouriteGiphy(long id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public long getId() {
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
