/*
Group Members:
Fabian, Jaelica P.
Gunot, Vin Trixter B.
Pajunar, Willie Chad K.

Date Created: September 10, 2026
Date Updated: September 22, 2026
Summary: Flames Tarot Game Strict Traditional FLAMES Algorithm & Dark Altar Reveal Mode
*/

package com.fajunot.flamestarotgame;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App extends Application {

    private Stage stage;
    private Scene scene;
    private String name1 = "";
    private String name2 = "";
    private final Random random = new Random();

    // Color Constants matching CSS Palette
    private static final String GOLD_COLOR = "#d4af37";
    private static final String DARK_RED_COLOR = "#e63946";
    
    //Pics ni Trix
    private final Image trixA = new Image( getClass().getResource("/images/TrixterA.png").toExternalForm() );
    private final Image trixI = new Image( getClass().getResource("/images/TrixterI.png").toExternalForm() );
    private final Image trixE = new Image( getClass().getResource("/images/TrixterE.png").toExternalForm() );
    private final Image trixU = new Image( getClass().getResource("/images/TrixterU.png").toExternalForm() );
    private final Image trixM = new Image( getClass().getResource("/images/TrixterM.png").toExternalForm() );
    private final Image trixRead = new Image( getClass().getResource("/images/TrixterRead.png").toExternalForm() );
    
    //effects
    private final Image rays = new Image( getClass().getResource("/images/Rays.png").toExternalForm() );
    
    //hand
    private final Image hand = new Image( getClass().getResource("/images/hand.png").toExternalForm() );

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Flames Tarot: Oracle of the Subconscious");
        stage.setResizable(false);

        StackPane root = new StackPane();
        scene = new Scene(root, 1280, 720);
        
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm()); //access stylesheet
        
        //tarotDeckScreen();
        introExpo();
        
        //Make the screen show up
        stage.setScene(scene);
        stage.show();
    }

    //Image loader
    private Image loadResourceImage(String baseName) {
        String[] names = {baseName + ".png", baseName + ".PNG", baseName + ".jpg", baseName + ".JPG"};

        for (String filename : names) {
            String pathWithSlash = "/images/" + filename;
            String pathNoSlash = "images/" + filename;

            try (InputStream is = App.class.getResourceAsStream(pathWithSlash)) {
                if (is != null) {
                    Image img = new Image(is);
                    if (!img.isError() && img.getWidth() > 0) return img;
                }
            } catch (Exception ignored) {}

            try (InputStream is = App.class.getClassLoader().getResourceAsStream(pathNoSlash)) {
                if (is != null) {
                    Image img = new Image(is);
                    if (!img.isError() && img.getWidth() > 0) return img;
                }
            } catch (Exception ignored) {}

            try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathNoSlash)) {
                if (is != null) {
                    Image img = new Image(is);
                    if (!img.isError() && img.getWidth() > 0) return img;
                }
            } catch (Exception ignored) {}
        }

        System.err.println("[RESOURCE WARNING] Missing image file for base: " + baseName);
        return null;
    }

    private StackPane createScreenWithBackground(String bgFileName) {
        StackPane root = new StackPane();
        Image bgImage = loadResourceImage(bgFileName);

        if (bgImage != null) {
            ImageView bgView = new ImageView(bgImage);
            bgView.setFitWidth(1280);
            bgView.setFitHeight(720);
            bgView.setPreserveRatio(false);
            root.getChildren().add(bgView);
        } else {
            root.setStyle("-fx-background-color: #100720;");
        }
        return root;
    }

    private void playFastTransition(Runnable loadNextScreen) {
        Node currentRoot = scene.getRoot();
        if (currentRoot == null) {
            loadNextScreen.run();
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            loadNextScreen.run();
            Node newRoot = scene.getRoot();
            if (newRoot != null) {
                newRoot.setOpacity(0.0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(150), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        });
        fadeOut.play();
    }

    private void fadeIn(Node node, int delayMs) {
        node.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(350), node);
        ft.setToValue(1.0);
        ft.setDelay(Duration.millis(delayMs));
        ft.play();
    }

    private ScaleTransition applyPulseAnimation(Node node, int ms) {
        ScaleTransition st = new ScaleTransition(Duration.millis(ms), node);
        st.setByX(0.03);
        st.setByY(0.03);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        st.setInterpolator(Interpolator.EASE_BOTH);
        st.play();
        return st;
    }
    
    //HEre para dli layo
    private int strIndex = 0;
    private Timeline anim_txt;
    
    //exposition
    public void introExpo(){
        //Initialization
        
        StackPane root = new StackPane();
        root.getStyleClass().add("blackBg");
        Label dialogue = new Label();
        
        Button btnContinue = new Button("Continue");
        btnContinue.setVisible(false);
        
        String message[] = {
            "You there.",
            "Yes, you.", 
            "Come in and have a seat, dear.",
            "I've been expecting you. This meeting has already been foretold in the cards.",
            "You wish to have your fate read? I see."
        };
        
        //Style
        dialogue.getStyleClass().add("primer-text");
        dialogue.setScaleX(1.5);
        dialogue.setScaleY(1.5);
        
        //Animate Text
        anim_txt = textScrollAnim (message[strIndex], dialogue, 25);
        anim_txt.setOnFinished( event -> {
            btnContinue.setVisible(true);
        });
        anim_txt.playFromStart();
        
        //Tie everything to root
        root.getChildren().add(dialogue);
        root.getChildren().add(btnContinue);
        
        btnContinue.setTranslateY(100);
        
        //Set Scene
        scene.setRoot(root);
        
        
        
        
        //inag cllick
        btnContinue.setOnAction(click -> {
            if (++strIndex < message.length){
                anim_txt = textScrollAnim (message[strIndex], dialogue, 25);
                
                //Dapat naa ni cya dri para mu trigger cya on every new message
                anim_txt.setOnFinished( event -> {
                    btnContinue.setVisible(true);
                });
                
                anim_txt.playFromStart();
                btnContinue.setVisible(false);
                return;
            }
                
            playFastTransition(this::mainMenu);
        });
    }

    // Main menu screen
    public void mainMenu() {
        StackPane root = createScreenWithBackground("1stBG");
        
        ImageView imgTrix = new ImageView( this.trixI );
        ImageView imgRays = new ImageView( this.rays );

        Label title = new Label("✨ FLAMES TAROT ✨");
        title.getStyleClass().add("gold-header-large");
        applyPulseAnimation(title, 3200);
        
        String primerStr =
            """
            The cards know what your conscious mind refuses to admit. Step into the sanctuary to uncover the 
            hidden frequencies weaving between two tethered souls.
            """;
        
        Label primerText = new Label();
        primerText.getStyleClass().add("primer-text");
        
        //Animation
        Timeline anim_txt_1;
        anim_txt_1 = textScrollAnim (primerStr, primerText, 25);
        anim_txt_1.playFromStart();

        Button btnBegin = new Button("READ YOUR FATE");
        btnBegin.getStyleClass().add("gold-button");
        ScaleTransition btnPulse = applyPulseAnimation(btnBegin, 1000);

        //Button hover events
        btnBegin.setOnMouseEntered(enter -> {
            btnPulse.play();
            imgTrix.setImage(trixRead);
        });
        btnBegin.setOnMouseExited(exit -> {
            btnPulse.stop();
            btnBegin.setScaleX(1.0); // Back to normal
            btnBegin.setScaleY(1.0);
            imgTrix.setImage(trixI);
        });
        
        //Align all the things 
        primerText.setAlignment(Pos.CENTER);
        primerText.setMaxWidth(800);
        
        //Adjust
        title.setTranslateY(-250);
        btnBegin.setTranslateY(175);
        primerText.setTranslateY(250);
        
        //Multiplier ni cya
        imgTrix.setScaleX(1.5);
        imgTrix.setScaleY(1.5);
        imgTrix.setTranslateY(100);
        
        //Trix Talking
        trixTalk(imgTrix, 100, 10);
        
        root.getChildren().add(imgRays);
        root.getChildren().add(imgTrix);
        root.getChildren().add(primerText);
        root.getChildren().add(title);
        root.getChildren().add(btnBegin);
        
        //spin rays
        RotateTransition spin = new RotateTransition(Duration.seconds(16), imgRays);

        spin.setByAngle(360);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setInterpolator(Interpolator.LINEAR);
        spin.play();
        imgRays.setOpacity(0.2);
        imgRays.setScaleX(3.5);
        imgRays.setScaleY(3.5);
        
        fadeIn(root, 50);
        scene.setRoot(root);

        btnBegin.setOnAction(click -> playFastTransition(this::soulConsentScreen));
    }

    // Consent screen
    public void soulConsentScreen() {
        StackPane root = createScreenWithBackground("3rdBG");

        Label warningSigil = new Label("⚖");
        warningSigil.getStyleClass().add("sigil-red");
        applyPulseAnimation(warningSigil, 3200);

        Label warningTitle = new Label("THE SACRED COVENANT");
        warningTitle.getStyleClass().add("dark-red-header");

        Label consentText = new Label(
            """
            Before the cards are dealt, take pause:
            By entering this ritual, you anchor a temporary fragment
            of your psychological vibration into the deck.
            What is revealed can never be unlearned.
            """
        );
        consentText.getStyleClass().add("consent-text");

        Button btnConsent = new Button("I CONSENT. BIND MY ESSENCE");
        btnConsent.getStyleClass().add("dark-button");

        VBox layoutContainer = new VBox(22, warningSigil, warningTitle, consentText, btnConsent);
        layoutContainer.setAlignment(Pos.CENTER);
        layoutContainer.setMaxWidth(750);

        root.getChildren().add(layoutContainer);
        fadeIn(layoutContainer, 50);
        scene.setRoot(root);

        btnConsent.setOnAction(e -> playFastTransition(this::askNamesScreen));
    }

    // Prompts for the other name
    private static class PromptSet {
        String title;
        String seekerLabel;
        String targetLabel;

        PromptSet(String title, String seekerLabel, String targetLabel) {
            this.title = title;
            this.seekerLabel = seekerLabel;
            this.targetLabel = targetLabel;
        }
    }

    //Basically just plac
    private final List<PromptSet> promptPresets = List.of(
        new PromptSet(
            "BEGIN THE RITUAL",
            "Seeker, enter your name:",
            "Whose ghost still haunts you in your quiet hours?"
        ),
        new PromptSet(
            "TETHER THE HEART",
            "Seeker, your true name, please:",
            "Who does your heart call out to?"
        ),
        new PromptSet(
            "MARK OF THE MIND",
            "Seeker, enter your name:",
            "The name of the one you can't forget:"
        ),
        new PromptSet(
            "ORACLE'S INQUIRY",
            "Your name, seeker:",
            "Whose memory brings you here?"
        )
    );
    
    public void askNamesScreen() {
        StackPane root = createScreenWithBackground("4thBG");

        PromptSet activePrompt = promptPresets.get(random.nextInt(promptPresets.size()));

        Label title = new Label(activePrompt.title);
        title.getStyleClass().add("gold-header");

        Label lbl1 = new Label(activePrompt.seekerLabel);
        lbl1.getStyleClass().add("input-label");

        TextField fldName1 = new TextField();
        fldName1.setPromptText("e.g. Jaelica Fabian");
        fldName1.setMaxWidth(480);

        Label lbl2 = new Label(activePrompt.targetLabel);
        lbl2.setWrapText(true);
        lbl2.setMaxWidth(480);
        lbl2.setTextAlignment(TextAlignment.CENTER);
        lbl2.getStyleClass().add("input-label");

        TextField fldName2 = new TextField();
        fldName2.setPromptText("e.g. Willie Pajunar");
        fldName2.setMaxWidth(480);

        Button btnSubmit = new Button("SUMMON ENERGY TO ALTAR");
        btnSubmit.getStyleClass().add("gold-button");

        Label txtErr = new Label("");
        txtErr.getStyleClass().add("error-label");

        VBox layoutContainer = new VBox(16, title, lbl1, fldName1, lbl2, fldName2, btnSubmit, txtErr);
        layoutContainer.setAlignment(Pos.CENTER);
        layoutContainer.setPadding(new Insets(30));

        root.getChildren().add(layoutContainer);
        fadeIn(layoutContainer, 50);
        scene.setRoot(root);

        btnSubmit.setOnAction(click -> {
            String n1 = fldName1.getText().trim();
            String n2 = fldName2.getText().trim();

            if (n1.isEmpty() || n2.isEmpty()) {
                txtErr.setText("Both names are required to anchor the ritual.");
                return;
            }

            this.name1 = n1;
            this.name2 = n2;

            playFastTransition(this::tarotDeckScreen);
        });
    }

    // Layout cards
    public void tarotDeckScreen() {
        StackPane root = createScreenWithBackground("6thBG");
        ImageView imgHand = new ImageView(this.hand);

        // Hand animation
        TranslateTransition hand_anim = new TranslateTransition();
        hand_anim.setDuration(Duration.seconds(1.25));
        hand_anim.setNode(imgHand);
        hand_anim.setByY(-650);
        hand_anim.setInterpolator(Interpolator.EASE_OUT);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(40, 10, 10, 10));

        // Header Labels (Positioned naturally in layout stream)
        Label title = new Label("THE ALTAR OF DESTINY");
        title.getStyleClass().add("gold-header-medium");

        Label instruction = new Label("Select 1 of the 6 cards to unlock the fate between " + this.name1 + " & " + this.name2);
        instruction.getStyleClass().add("instruction-label");

        VBox headerText = new VBox(8, title, instruction);
        headerText.setAlignment(Pos.CENTER);
        headerText.setTranslateY(50);

        // Card container
        Pane cardRoot = new Pane();
        cardRoot.setPrefSize(1000, 400);
        cardRoot.setMaxSize(1000, 400);

        double centerX = 325;
        double centerY = 200;
        double spacing = 70;

        for (int i = 0; i < 6; i++) {
            final int cardIndex = i;

            StackPane cardNode = createBlankCardNode(i + 1);
            cardNode.setPrefSize(130, 195);

            // Position cards centered inside cardRoot
            double x = centerX - ((5 * spacing) / 2) + (i * spacing);
            double y = centerY - 50;

            cardNode.setLayoutX(x);
            cardNode.setLayoutY(y);

            // Pivot rotation around bottom-center of card
            Rotate rot = new Rotate();
            rot.pivotXProperty().bind(cardNode.widthProperty().divide(2));
            rot.pivotYProperty().bind(cardNode.heightProperty().add(150));
            rot.setAngle(-50 + (i * 20));

            cardNode.getTransforms().add(rot);

            // Card Click Listener
            cardNode.setOnMouseClicked(e -> {
                e.consume();
                playFastTransition(() -> revealFate(cardIndex));
            });

            cardRoot.getChildren().add(cardNode);
        }

        // Stack layout elements in proper order without negative translation overlays
        mainLayout.getChildren().addAll(headerText, cardRoot);
        root.getChildren().addAll(imgHand, mainLayout);

        fadeIn(mainLayout, 50);
        hand_anim.play();
        scene.setRoot(root);
    }

    private StackPane createBlankCardNode(int cardNumber) {
        StackPane card = new StackPane();

        card.setAlignment(Pos.CENTER);
        card.setCursor(Cursor.HAND);

        ImageView cardImgView = new ImageView();
        Image blankImg = loadResourceImage("cardBlank");
        

        if (blankImg != null) {

            cardImgView.setImage(blankImg);

            cardImgView.setFitHeight(195);

            double targetWidth =
                195.0 * (blankImg.getWidth() / blankImg.getHeight());

            cardImgView.setFitWidth(targetWidth);
            cardImgView.setPreserveRatio(true);

            card.setPrefWidth(targetWidth);
            card.setPrefHeight(195);

            card.setMinWidth(targetWidth);
            card.setMinHeight(195);

            card.setMaxWidth(targetWidth);
            card.setMaxHeight(195);

            card.getChildren().add(cardImgView);

        } else {

            Label placeholder = new Label("✦\nCARD " + cardNumber);
            placeholder.getStyleClass().add("card-placeholder");

            card.setPrefWidth(130);
            card.setPrefHeight(195);

            card.getChildren().add(placeholder);
        }

        DropShadow defaultGlow = new DropShadow();
        defaultGlow.setColor(Color.web(GOLD_COLOR, 0.50));
        defaultGlow.setRadius(14);

        card.setEffect(defaultGlow);
        card.setPickOnBounds(false); 

        card.setOnMouseEntered(e -> {

            card.setTranslateY(-6);

            DropShadow hoverGlow = new DropShadow();
            hoverGlow.setColor(Color.web(GOLD_COLOR, 0.95));
            hoverGlow.setRadius(22);

            card.setEffect(hoverGlow);
        });

        card.setOnMouseExited(e -> {

            card.setTranslateY(0);
            card.setEffect(defaultGlow);
        });

        return card;
    }

    // Final reading
    public void revealFate(int chosenCardIndex) {
        FlamesResult result = calculateFlames();

        StackPane root = createScreenWithBackground(result.isDark ? "7thBG" : "4thBG");

        VBox content = new VBox(18);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label titleBanner = new Label(result.outcomeName);
        titleBanner.getStyleClass().add(result.isDark ? "dark-red-header-small" : "gold-header-small");
        applyPulseAnimation(titleBanner, 3200);

        HBox altarGrid = new HBox(8); 
        altarGrid.setAlignment(Pos.CENTER);
        altarGrid.setMaxWidth(Region.USE_PREF_SIZE); 

        // Card 1: Chosen Tarot Card Image
        ImageView cardView1 = new ImageView();
        Image resultImg = loadResourceImage(result.imageFileName);
        if (resultImg != null) {
            cardView1.setImage(resultImg);
            cardView1.setFitHeight(300);
            if (resultImg.getHeight() > 0) {
                cardView1.setFitWidth(300.0 * (resultImg.getWidth() / resultImg.getHeight()));
            }
            cardView1.setPreserveRatio(true);

            DropShadow cardGlow = new DropShadow();
            cardGlow.setColor(Color.web(result.isDark ? DARK_RED_COLOR : GOLD_COLOR, 0.85));
            cardGlow.setRadius(22);
            cardView1.setEffect(cardGlow);
            altarGrid.getChildren().add(cardView1);
        }

        // Connective Illuminated Sigil
        Label lineConnect = new Label(result.isDark ? "⚔" : "✦");
        lineConnect.setStyle("-fx-font-size: 22px; -fx-text-fill: " + (result.isDark ? DARK_RED_COLOR : GOLD_COLOR) + ";");
        applyPulseAnimation(lineConnect, 3200);
        altarGrid.getChildren().add(lineConnect);

        // Card 2: Uses number.png background for the count display
        StackPane cardView2 = new StackPane();
        ImageView seekerImg = new ImageView();
        Image numberImg = loadResourceImage("number");

        Image card2Backing = (numberImg != null) ? numberImg : loadResourceImage("cardBlank");
        if (card2Backing != null) {
            seekerImg.setImage(card2Backing);
            seekerImg.setFitHeight(300);
            if (card2Backing.getHeight() > 0) {
                double targetWidth = 300.0 * (card2Backing.getWidth() / card2Backing.getHeight());
                seekerImg.setFitWidth(targetWidth);
                cardView2.setMaxWidth(targetWidth);
                cardView2.setPrefWidth(targetWidth);
            }
            seekerImg.setPreserveRatio(true);
            cardView2.getChildren().add(seekerImg);
        }

        Label sigilOverlay = new Label(result.isDark ? "REVERSED\n" + result.score : "DIRECT\n" + result.score);
        sigilOverlay.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-text-fill: " + (result.isDark ? DARK_RED_COLOR : GOLD_COLOR) + ";");
        cardView2.getChildren().add(sigilOverlay);

        DropShadow card2Glow = new DropShadow();
        card2Glow.setColor(Color.web(result.isDark ? DARK_RED_COLOR : GOLD_COLOR, 0.65));
        card2Glow.setRadius(22);
        cardView2.setEffect(card2Glow);
        altarGrid.getChildren().add(cardView2);

        Label adviceLabel = new Label(result.adviceText);
        adviceLabel.setWrapText(true);
        adviceLabel.setMaxWidth(680);
        adviceLabel.getStyleClass().add(result.isDark ? "advice-text-dark" : "advice-text-gold");

        VBox infoBox = new VBox(8, adviceLabel);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setMaxWidth(720);
        infoBox.getStyleClass().add(result.isDark ? "info-box-dark" : "info-box-gold");

        Button btnRestart = new Button("Consult the Deck Again");
        btnRestart.getStyleClass().add(result.isDark ? "dark-button" : "gold-button");

        HBox actionBox = new HBox(btnRestart);
        actionBox.setAlignment(Pos.CENTER);

        content.getChildren().addAll(titleBanner, altarGrid, infoBox, actionBox);
        root.getChildren().add(content);

        fadeIn(content, 50);
        scene.setRoot(root);

        btnRestart.setOnAction(click -> playFastTransition(this::mainMenu));
    }

    // Flames logic
    private static class FlamesResult { 
        String outcomeName;
        String imageFileName;
        int score;
        String adviceText;
        boolean isDark;

        FlamesResult(String outcomeName, String imageFileName, int compatibilityScore, String adviceText, boolean isDark) {
            this.outcomeName = outcomeName;
            this.imageFileName = imageFileName;
            this.score = compatibilityScore;
            this.adviceText = adviceText;
            this.isDark = isDark;
        }
    }
    
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

    private FlamesResult calculateFlames() {
        int score1, score2, sum;
        score1 = getSimScore(this.name1, this.name2);
        score2 = getSimScore(this.name2, this.name1);
        sum = score1 + score2;
        
        boolean isDark = random.nextBoolean();

        String outcomeTitle;
        String adviceText;
        String imageFileName;

        switch (sum % "FLAMES".length()) {
            case 1:
                imageFileName = "friends";
                if (isDark) {
                    outcomeTitle = "Friends (Reversed) — The Shadow Pact";
                    adviceText = "Underneath a quiet alliance lies an unexpressed distance between " + this.name1 + " and " + this.name2 + ". Unspoken expectations linger, threatening to turn genuine friendship into silent resentment if boundaries are left unsaid.";
                } else {
                    outcomeTitle = "FRIENDS — Harmonic Alliance";
                    adviceText = "The stars reflect a high-frequency alignment built on mutual trust and shared understanding between " + this.name1 + " and " + this.name2 + ". Protect this bond as an enduring sanctuary.";
                }
                break;

            case 2:
                imageFileName = "lovers";
                if (isDark) {
                    outcomeTitle = "The Lovers (Reversed) — The Lost Cause";
                    adviceText = "A volatile passion burns between " + this.name1 + " and " + this.name2 + ", consumed by obsessive longing and emotional tension. Unresolved fears mask true vulnerability, pulling both souls into an addictive cycle of attraction and ruin.";
                } else {
                    outcomeTitle = "LOVERS — Magnetic Devotion";
                    adviceText = "An intense magnetic resonance pulls " + this.name1 + " and " + this.name2 + " into harmony. Vulnerability and devotion build an elevated path forward.";
                }
                break;

            case 3:
                imageFileName = "acquaintances";
                if (isDark) {
                    outcomeTitle = "Affection (Reversed) — Unspoken Obsession";
                    adviceText = "Surface-level interactions conceal hidden intensity between " + this.name1 + " and " + this.name2 + ". The emotional tether remains unresolved, trapped between fascinated curiosity and fear of rejection.";
                } else {
                    outcomeTitle = "AFFECTION — Gentle Orbit";
                    adviceText = "Your tethered energy grows in steady orbit. " + this.name1 + " and " + this.name2 + " share an emerging bond that flourishes through honest conversation.";
                }
                break;

            case 4:
                imageFileName = "marriage";
                if (isDark) {
                    outcomeTitle = "Marriage (Reversed) — Bound in Golden Chains";
                    adviceText = "An inescapable covenant links " + this.name1 + " and " + this.name2 + ", but obligations threaten to overshadow organic affection. Friction arises as duty clashes with personal freedom.";
                } else {
                    outcomeTitle = "MARRIAGE — Sacred Covenant";
                    adviceText = "A rare permanent soul-contract binds " + this.name1 + " and " + this.name2 + ". Your paths intertwine for enduring mutual growth, long-term stability, and unconditional devotion.";
                }
                break;

            case 5:
                imageFileName = "enemies";
                if (isDark) {
                    outcomeTitle = "Enemies (Reversed) — Eternal Nemesis";
                    adviceText = "A dark friction dominates the energetic field between " + this.name1 + " and " + this.name2 + ". Ego clashes and deep psychological pride provoke recurring conflict that requires strict boundaries to break.";
                } else {
                    outcomeTitle = "ENEMIES — Fiery Friction";
                    adviceText = "High kinetic tension exists between " + this.name1 + " and " + this.name2 + ". If directed constructively, this friction can spark transformative personal growth.";
                }
                break;

            case 6:
            default:
                imageFileName = "soulmates";
                if (isDark) {
                    outcomeTitle = "Soulmates (Reversed) — The Broken Mirror";
                    adviceText = "A spiritual mirror links " + this.name1 + " and " + this.name2 + ", reflecting both light and deepest insecurities. Until inner shadows are acknowledged, this intense connection will feel like a fated burden.";
                } else {
                    outcomeTitle = "SOULMATES — Cosmic Synchronicity";
                    adviceText = "An otherworldly cosmic synchronization binds " + this.name1 + " and " + this.name2 + ". Two halves of a single spiritual entity walking the path of destiny.";
                }
                break;
        }

        return new FlamesResult(outcomeTitle, imageFileName, sum, adviceText, isDark);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    //SUPPORT FUNCTIONS ================================
    public Timeline textScrollAnim(String msg, Label label, int spd){
        Timeline tm = new Timeline();
        
        for (int i = 0; i < msg.length(); i++){
            int index = i;
            
            KeyFrame keyFrame = new KeyFrame( Duration.millis(spd * i), event -> {
                label.setText(msg.substring(0, index + 1));
            });
            
            tm.getKeyFrames().add(keyFrame);
        }
        
        return tm;
    }
    
    public void trixTalk(ImageView imgTrix, int spd, int cycles){

        Timeline tm = new Timeline(
            new KeyFrame(Duration.millis(spd), e -> imgTrix.setImage(trixM)),
            new KeyFrame(Duration.millis(spd * 2), e -> imgTrix.setImage(trixA)),
            new KeyFrame(Duration.millis(spd * 3), e -> imgTrix.setImage(trixE)),
            new KeyFrame(Duration.millis(spd * 4), e -> imgTrix.setImage(trixI))
        );
        
        if (cycles == -1)
            tm.setCycleCount(Animation.INDEFINITE);
        else
            tm.setCycleCount(cycles);
            
        tm.play();
    }
}