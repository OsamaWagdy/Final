package com.example.application.myfinal;



public class Movies {

    public Movies(){

    }
    private String PosterUrl;
    private String Overview;
    private String Title;
    private String Voteavg;
    private String Releasedate;
    private String ID;
    private String TrailerName;
    private String TrailerSrc;
    private String ReviewAuthor;
    private String ReviewContent;

    public String getReviewContent() {
        return ReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        ReviewContent = reviewContent;
    }

    public String getReviewAuthor() {

        return ReviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        ReviewAuthor = reviewAuthor;
    }

    public String getTrailerSrc() {

        return TrailerSrc;
    }

    public void setTrailerSrc(String trailerSrc) {
        TrailerSrc = trailerSrc;
    }

    public String getTrailerName() {

        return TrailerName;
    }

    public void setTrailerName(String trailerName) {
        TrailerName = trailerName;
    }

    public String getReleasedate() {
        return Releasedate;
    }

    public void setReleasedate(String releasedate) {
        Releasedate = releasedate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVoteavg() {

        return Voteavg;

    }

    public void setVoteavg(String voteavg) {
        Voteavg = voteavg;
    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {

        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public void setPosterUrl(String posterUrl) {

        PosterUrl = posterUrl;
    }

    public String getPosterUrl(){
        return PosterUrl;
    }
    public String getMoviePoster(){
        return "http://image.tmdb.org/t/p/w185/"+PosterUrl;
    }
}
