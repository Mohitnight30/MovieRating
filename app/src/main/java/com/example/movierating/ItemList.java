package com.example.movierating;

public class ItemList {

     String title,description,genres;
     String year,imageAdress;
     float rating;



    public ItemList(String title, String genres, float rating, String imageAdress)
    {
        this.title=title;
        this.imageAdress=imageAdress;
        this.rating=rating;
        this.genres=genres;
    }

    public String getGenres() {
        return genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getYear() {
        return year;
    }

    public float getRating() {
        return rating;
    }

    public String getImageAdress() {
        return imageAdress;
    }
}
