package com.example.footballplayersgallery.beans;


public class Player {
    private int id;
    private String fullName;
    private String photoUrl;
    private String position;   // ex: "Attaquant", "Milieu"...
    private String club;
    private float score;
    private static int counter = 0;

    public Player(String fullName, String photoUrl, String position, String club, float score) {
        this.id = ++counter;
        this.fullName = fullName;
        this.photoUrl = photoUrl;
        this.position = position;
        this.club = club;
        this.score = score;
    }

    public int getId()         { return id; }
    public String getFullName(){ return fullName; }
    public String getPhotoUrl(){ return photoUrl; }
    public String getPosition(){ return position; }
    public String getClub()    { return club; }
    public float getScore()    { return score; }

    public void setFullName(String n) { fullName = n; }
    public void setPhotoUrl(String u) { photoUrl = u; }
    public void setScore(float s)     { score = s; }
    public void setPosition(String p) { position = p; }
    public void setClub(String c)     { club = c; }
}