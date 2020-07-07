package com.example.androidlabs;

public class Message {
    private String text;
//    private String type;
    private Boolean isSent;

//    public Message (String text, String type){
//        this.text = text;
//        this.type = type;
//    }

    public Message (String text, Boolean isSent){
        this.text = text;
        this.isSent = isSent;
    }

    public String getText(){ return text; }


    public Boolean getSentStatus(){
        return isSent;
    }

}
