package com.example.androidlabs;

public class Message {
    private String text;
    private Boolean isSent;
    private long databaseId;


    public Message (String text, Boolean isSent, long databaseId){
        this.text = text;
        this.isSent = isSent;
        this.databaseId=databaseId;
    }

    public String getText(){
        return text;
    }
    public Boolean getSentStatus(){
        return isSent;
    }
    public long getId(){return databaseId;}

}
