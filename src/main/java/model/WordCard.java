package model;

public class WordCard {

    private int id;
    private String picLocation;
    private String theme;
    private String eng;
    private String hun;

    public WordCard(int id, String picLocation, String theme, String eng, String hun){
        this.id = id;
        this.picLocation = picLocation;
        this.theme = theme;
        this.eng = eng;
        this.eng = eng;
    }

    public String getPicLocation() {
        return picLocation;
    }

    public void setPicLocation(String picLocation) {
        this.picLocation = picLocation;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getHun() {
        return hun;
    }

    public void setHun(String hun) {
        this.hun = hun;
    }
}
