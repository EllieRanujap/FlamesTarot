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

    @Override
    public void start(Stage stage) {
        var title = new Label ("Flames Tarot");
        var root = new StackPane();
        root.getChildren().add(title);
        
        var scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle(title.getText());
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }

}