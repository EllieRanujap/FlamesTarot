/*
Group Members:
Fabian, Jaelica P.
Gunot, Vin Trixter B.
Pajunar, Willie Chad K.

Date Created: Septmeber 10, 2026
Date Submitted: N/A
Summary: This the file for the final project. The app is a FLAMES game with a tarot aesthetic.
*/

/*
====================
Notes ni Willie
====================
1. STAGE
    - an object; the actual application window (with the x and everything)
    Declaration: Stage <stage_name>
    Import: javafx.stage.Stage;

2. SCENE
    - an object; the particular scene like in a play, display.
    Declaration: Scene <scene_name>
    Import: javafx.scene.Scene;

3. NODE
    - things inside the scene.
    - ex. Text, TextField, Label, Button, ImageView, Rectangle, Circle, Pane

The base scene graph:
Stage -> Scene -> StackPane -> Label

4. LABEL
    - use it for UI. If not, use text. 
    Import: javafx.scene.control.Label;

5. APPLICATION
    - the child of Application and must have the same name as the file.

6. START
    - the first to run.
    Usually found:
    - Create player, create thinngs, background, buttons, window etc.

stage.setTitle("<Title>");
    - makes the title of the window

var - automatic variable identification unlike C.
ImageView - might be used later
Rectangle - Maybe for bars
Button - Menu 

EVENT LISTENERS:
clickMe.setOnAction( event -> {
    System.out.println("YOU CLICKED ME!");
});
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
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); //access stylesheet
        
        mainMenu();
    }
    
    //Screens ======================================================================
    public void mainMenu (){
        //Initialization
        var root = new VBox();
        var title = new Label("Welcome to Flames Tarot!");
        var btn_play = new Button("PLAY!");
        
        //Building
        root.getChildren().add(title);
        root.getChildren().add(btn_play);
        
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
    
    //CALCULATING FUNCTIONS ===================================================================================
    
    
    //AESTHETIC FUNCTIONS =====================================================================================

}