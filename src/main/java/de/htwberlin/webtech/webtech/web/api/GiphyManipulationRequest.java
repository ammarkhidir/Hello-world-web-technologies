package de.htwberlin.webtech.webtech.web.api;

public class GiphyManipulationRequest {

    private String title;
    private String link;

    public GiphyManipulationRequest(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public GiphyManipulationRequest() {

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
