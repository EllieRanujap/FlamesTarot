/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fajunot.flamestarotgame;

/**
 *
 * @author user
 */
public class PromptSet {
    private String title;
    private String seekerLabel;
    private String targetLabel;

    //CONSTRUCTOR
    PromptSet(String title, String seekerLabel, String targetLabel) {
        this.title = title;
        this.seekerLabel = seekerLabel;
        this.targetLabel = targetLabel;
    }
    
    //GETTER
    public String getTitle(){
        return this.title;
    }
    
    public String getSeekerLabel(){
        return this.seekerLabel;
    }
    
    public String getTargetLabel(){
        return this.targetLabel;
    }
}
