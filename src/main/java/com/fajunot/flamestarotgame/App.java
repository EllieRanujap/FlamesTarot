/*
Group Members:
Fabian, Jaelica P.
Gunot, Vin Trixter B.
Pajunar, Willie Chad K.

Date Created: Septmeber 10, 2026
Date Submitted: N/A
Summary: This the file for the final project. The app is a FLAMES game with a tarot aesthetic.
*/

package com.fajunot.flamestarotgame;

import javafx.application.Application;

import javafx.stage.Stage;

//Scene Imports
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;     //image
import javafx.scene.image.ImageView; //frame


/**
 * JavaFX App
 */
public class App extends Application {
    //Instance variable
    private Stage stage;
    private Scene scene;
    private String name1, name2;
    
    
    //Start =======================================================================
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Flames Tarot Game");
        scene = new Scene(new Pane(), 1280, 720); // the new pane is just temporary
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm()); //access stylesheet
        
        
        
        mainMenu();
    }
    
    //Screens ======================================================================
    public void mainMenu (){
        //Initialization
        var root = new StackPane();
        var scr_elements = new VBox();
        var title = new Label("Welcome to Flames Tarot!");
        var btn_play = new Button("PLAY!");
        
        //Pic ni Trix
        Image bgPic = new Image( getClass().getResource("/images/trixter.jpg").toExternalForm() );
        ImageView img_bgPic = new ImageView( bgPic );
        
        //Building (Back to front)
        root.getChildren().addAll(img_bgPic, scr_elements);
        
        scr_elements.getChildren().addAll(title, btn_play);
        
        //Image 
        img_bgPic.fitWidthProperty().bind( this.scene.widthProperty() );
        img_bgPic.fitHeightProperty().bind( this.scene.heightProperty() );
        
        //Setup
        setupScreen(root);
        
        //Button
        btn_play.setOnAction(click -> { //I just found out u can name this whatever u want
            askFirstName();
        });
    }
    
    public void askFirstName(){
        //Initialization
        var root = new VBox();
        var txt_1 = new Label("Let's start with your name.");
        var txt_err = new Label("");
        var fld_name = new TextField();
        var btn_enter = new Button("ENTER");
        
        //Building
        root.getChildren().add(txt_1);
        root.getChildren().add(fld_name);
        root.getChildren().add(btn_enter);
        root.getChildren().add(txt_err);
        
        //Setup
        setupScreen(root);
        
        //Button
        btn_enter.setOnAction(click -> { 
            String name = fld_name.getText().trim();
            
            //ERROR TRAPPING --Make this into a function in the future
            if (name.equals("")){
                txt_err.setText("Please input your name, darling.");
                return;
            }
            
            if (name.length() > 50){
                txt_err.setText("Sorry dear, I have a 50 character limit.");
                return;
            }
            
            this.name1 = name;
            askSecondName();
        });
    }
    
    public void askSecondName(){
        //Initialization
        var root = new VBox();
        var txt_1 = new Label("Alright, " + this.name1 + ". Who is the person you can't stop thinking about?");
        var txt_err = new Label("");
        var fld_name = new TextField();
        var btn_enter = new Button("ENTER");
        
        //Building
        root.getChildren().add(txt_1);
        root.getChildren().add(fld_name);
        root.getChildren().add(btn_enter);
        root.getChildren().add(txt_err);
        
        //Setup
        setupScreen(root);
        
        //Button
        btn_enter.setOnAction(click -> { 
            String name = fld_name.getText().trim();
            
            //ERROR TRAPPING
            if (name.equals("")){
                txt_err.setText("Please type their name properly...");
                return;
            }
            
            if (name.length() > 50){
                txt_err.setText("Sorry dear, I have a 50 character limit.");
                return;
            }
            
            this.name2 = name;
            txt_err.setText(this.name1 + " and " + this.name2 + "... What kind of a pair will this turn out to be?");
            flamesResult();
        });
    }
    
    public void flamesResult(){
        //Initialization
        int score1, score2, sum;
        String relation;
        score1 = getSimScore(this.name1, this.name2);
        score2 = getSimScore(this.name2, this.name1);
        sum = score1 + score2;
        
        switch (sum % "FLAMES".length()){
            case 1: relation = "FRIENDS"; break;
            case 2: relation = "LOVERS"; break;
            case 3: relation = "ACQUAINTANCE"; break;
            case 4: relation = "MARRIED"; break;
            case 5: relation = "ENEMIES"; break;
            default: relation = "SOULMATE"; break;
        }
        
        //Elements
        var root = new VBox();
        var txt_score1 = new Label(this.name1 + ": " + score1);
        var txt_score2 = new Label(this.name2 + ": " + score2);
        var result = new Label("You are " + relation + "!");
        var btn_return = new Button("RETURN");
        
        
        //Building
        root.getChildren().add(txt_score1);
        root.getChildren().add(txt_score2);
        root.getChildren().add(result);
        root.getChildren().add(btn_return);
        
        //Setup
        setupScreen(root);
        
        //Button Behavior
        btn_return.setOnAction(click -> {
            mainMenu();
        });
    }
    
    
    //MAIN ===============================================================================================
    public static void main(String[] args) {
        launch();
    }
    
    //SUPPORT FUNCTIONS
    public void setupScreen(VBox root){ //Make overloaded functions if not VBox
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setupScreen(StackPane root){
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //CALCULATING FUNCTIONS ===================================================================================
    public int getSimScore(String n1, String n2){
        int sim = 0;
        char c;
        n1 = n1.toUpperCase();
        n2 = n2.toUpperCase();
        
        for (int i = 0; i < n1.length(); i++){
            c = n1.charAt(i);
            if ((n2.indexOf(c) != -1) && (c != ' '))
                sim++;
        }
        
        return sim;
    }
    
    //AESTHETIC FUNCTIONS =====================================================================================
    
}