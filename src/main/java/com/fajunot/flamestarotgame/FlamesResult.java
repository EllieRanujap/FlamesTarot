/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fajunot.flamestarotgame;

public class FlamesResult {
    
    private String outcomeName;
    private String imageFileName;
    private int score;
    private String adviceText;
    private boolean isDark;

    FlamesResult(String outcomeName, String imageFileName, int compatibilityScore, String adviceText, boolean isDark) {
        this.outcomeName = outcomeName;
        this.imageFileName = imageFileName;
        this.score = compatibilityScore;
        this.adviceText = adviceText;
        this.isDark = isDark;
    }
    
    public boolean getIsDark(){
        return isDark;
    }
    
    public String getOutcomeName(){
        return outcomeName;
    }
    
    public String getImageFileName(){
        return imageFileName;
    }
    
    public String getAdviceText(){
        return adviceText;
    }
    
    public int getScore(){
        return score;
    }
}
