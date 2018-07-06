package com.csi.itaca.users.model.dto;

public class MessageDTO {
 
    String name;
    String text;
 
    public MessageDTO(String name, String text) {
        this.name = name;
        this.text = text;
    }
 
    public String getName() {
        return name;
    }
 
    public String getText() {
        return text;
    }
 
}