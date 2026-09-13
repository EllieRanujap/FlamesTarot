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
    - ex. Text, Label, Button, ImageView, Rectangle, Circle, Pane

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

*/

package com.fajunot.flamestarotgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage){
        Label label = new Label("FLAMES TAROT!");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.setTitle("FLAMES Tarot");
        stage.show();

    }
    
    public void next (Stage stage){
        var label = new Label("HEEEEEEEE");
        var scene = new Scene (new StackPane(label), 300, 300);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}