/*
Group Members:
Fabian, Jaelica P.
Gunot, Vin Trixter B.
Pajunar, Willie Chad K.

Date Created: September 10, 2026
Date Updated: September 17, 2026
Summary: Animated Flames Tarot Game with stable input forms & auto-wrapping labels.
*/

package com.fajunot.flamestarotgame;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private Stage stage;
    private Scene scene;
    private String name1, name2;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Flames Tarot Game — Oracle of the Subconscious");
        stage.setResizable(false);

        scene = new Scene(new Pane(), 1280, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        } catch (Exception e) {
            // CSS fallback handled inline
        }

        mainMenu();
    }

    // ==========================================
    // ANIMATION HELPER METHODS
    // ==========================================
    private void fadeIn(Node node, int delayMs) {
        node.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(700), node);
        ft.setToValue(1.0);
        ft.setDelay(Duration.millis(delayMs));
        ft.play();
    }

    private void applyFloatAnimation(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(2600), node);
        tt.setByY(-8);
        tt.setAutoReverse(true);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.play();
    }

    private void applyPulseAnimation(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(1600), node);
        st.setByX(0.04);
        st.setByY(0.04);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        st.setInterpolator(Interpolator.EASE_BOTH);
        st.play();
    }

    // PHASE 1: MAIN MENU ==========================================================
    public void mainMenu() {
        var root = new StackPane();
        var scr_elements = new VBox(20);
        scr_elements.setAlignment(Pos.CENTER);

        Label orbSymbol = new Label("🔮");
        orbSymbol.setStyle("-fx-font-size: 54px;");
        applyFloatAnimation(orbSymbol);

        Label title = new Label("✨ FLAMES TAROT ✨");
        title.getStyleClass().add("gold-header");
        applyPulseAnimation(title);

        Label primerText = new Label(
            "The cards know what your conscious mind refuses to admit.\n" +
            "Step into the sanctuary to uncover the hidden frequencies\n" +
            "weaving between two tethered souls."
        );
        primerText.setStyle("-fx-font-size: 16px; -fx-text-fill: #e8e0ff; -fx-text-alignment: center; -fx-line-spacing: 6px;");

        Button btn_begin = new Button("ENTER THE SANCTUARY");
        btn_begin.getStyleClass().add("mystic-button");

        scr_elements.getChildren().addAll(orbSymbol, title, primerText, btn_begin);

        VBox cardContainer = new VBox(scr_elements);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setStyle("-fx-background-color: rgba(13, 11, 30, 0.88); -fx-padding: 40px; -fx-background-radius: 20px; -fx-max-width: 600px; -fx-border-color: #d4af37; -fx-border-radius: 20px; -fx-border-width: 1.5px;");

        root.getChildren().add(cardContainer);
        
        fadeIn(orbSymbol, 100);
        fadeIn(title, 250);
        fadeIn(primerText, 400);
        fadeIn(btn_begin, 550);

        setupScreen(root);

        btn_begin.setOnAction(click -> soulConsentScreen());
    }

    // PHASE 2: SOUL TETHER CONSENT =================================================
    public void soulConsentScreen() {
        var root = new VBox(22);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0b1e, #181136); -fx-padding: 50px;");

        Label warningSigil = new Label("⚖");
        warningSigil.setStyle("-fx-font-size: 48px; -fx-text-fill: #e63946;");
        applyPulseAnimation(warningSigil);

        Label warningTitle = new Label("THE SACRED COVENANT");
        warningTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #e63946;");

        Label consentText = new Label(
            "Before the cards are dealt, take pause:\n" +
            "By entering this ritual, you anchor a temporary fragment\n" +
            "of your psychological vibration into the deck.\n\n" +
            "What is revealed can never be unlearned."
        );
        consentText.setStyle("-fx-font-size: 16px; -fx-text-fill: #cfc0f0; -fx-text-alignment: center; -fx-line-spacing: 5px;");

        Button btn_consent = new Button("I CONSENT — BIND MY ESSENCE");
        btn_consent.setStyle("-fx-font-size: 16px; -fx-background-color: #e63946; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-background-radius: 20px; -fx-padding: 12 28; -fx-cursor: hand;");

        root.getChildren().addAll(warningSigil, warningTitle, consentText, btn_consent);

        fadeIn(warningSigil, 100);
        fadeIn(warningTitle, 250);
        fadeIn(consentText, 400);
        fadeIn(btn_consent, 550);

        setupScreen(root);

        btn_consent.setOnAction(click -> askFirstName());
    }

    // PHASE 3: INPUT FIRST NAME =====================================================
    public void askFirstName() {
        var root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0b1e, #1a1438); -fx-padding: 40px;");

        Label txt_1 = new Label("Whisper your FULL NAME to anchor the ritual:");
        txt_1.getStyleClass().add("gold-header");
        txt_1.setWrapText(true);
        txt_1.setTextAlignment(TextAlignment.CENTER);
        txt_1.setMaxWidth(500);

        TextField fld_name = new TextField();
        fld_name.setPromptText("e.g. Jaelica P. Fabian");
        fld_name.setMaxWidth(400);

        Button btn_enter = new Button("BIND SEEKER ESSENCE");
        btn_enter.getStyleClass().add("mystic-button");

        Label txt_err = new Label("");
        txt_err.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 14px;");

        fld_name.setOnAction(e -> btn_enter.fire());

        VBox formBox = new VBox(20, txt_1, fld_name, btn_enter, txt_err);
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-background-color: rgba(20, 14, 35, 0.85); -fx-padding: 40px; -fx-background-radius: 18px; -fx-border-color: #d4af37; -fx-border-radius: 18px; -fx-border-width: 1px; -fx-max-width: 560px;");

        root.getChildren().add(formBox);
        fadeIn(formBox, 150);

        setupScreen(root);

        btn_enter.setOnAction(click -> {
            String name = fld_name.getText().trim();
            if (name.isEmpty()) {
                txt_err.setText("The deck requires a name to anchor your frequency.");
                return;
            }
            if (name.length() > 50) {
                txt_err.setText("The ancient script can only hold up to 50 characters.");
                return;
            }
            this.name1 = name;
            askSecondName();
        });
    }

    // PHASE 4: INPUT SECOND NAME ====================================================
    public void askSecondName() {
        var root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0b1e, #1a1438); -fx-padding: 40px;");

        Label txt_1 = new Label("Name the shadow that haunts your thoughts:");
        txt_1.getStyleClass().add("gold-header");
        txt_1.setWrapText(true);
        txt_1.setTextAlignment(TextAlignment.CENTER);
        txt_1.setMaxWidth(500);

        TextField fld_name = new TextField();
        fld_name.setPromptText("Enter their full name or identifier");
        fld_name.setMaxWidth(400);

        Button btn_enter = new Button("SUMMON ENERGY TO TABLE");
        btn_enter.getStyleClass().add("mystic-button");

        Label txt_err = new Label("");
        txt_err.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 14px;");

        fld_name.setOnAction(e -> btn_enter.fire());

        VBox formBox = new VBox(20, txt_1, fld_name, btn_enter, txt_err);
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-background-color: rgba(20, 14, 35, 0.85); -fx-padding: 40px; -fx-background-radius: 18px; -fx-border-color: #d4af37; -fx-border-radius: 18px; -fx-border-width: 1px; -fx-max-width: 560px;");

        root.getChildren().add(formBox);
        fadeIn(formBox, 150);

        setupScreen(root);

        btn_enter.setOnAction(click -> {
            String name = fld_name.getText().trim();
            if (name.isEmpty()) {
                txt_err.setText("Speak their name properly to summon their energy...");
                return;
            }
            if (name.length() > 50) {
                txt_err.setText("The ancient script can only hold up to 50 characters.");
                return;
            }
            this.name2 = name;
            tarotTableScreen();
        });
    }

    // PHASE 4.5: INTERACTIVE ALTAR TABLE (CARD DEALING ANIMATION) =================
    public void tarotTableScreen() {
        var root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0b0712; -fx-padding: 30px;");

        Label title = new Label("🔮 THE ALTAR OF DESTINY 🔮");
        title.getStyleClass().add("gold-header");
        applyPulseAnimation(title);

        Label instruction = new Label("The deck has been dealt for " + this.name1 + " & " + this.name2 + ".\nTouch a card on the altar to flip and reveal your shared fate.");
        instruction.setStyle("-fx-font-size: 15px; -fx-text-fill: #cfc0f0; -fx-text-alignment: center;");

        HBox tableLayout = new HBox(25);
        tableLayout.setAlignment(Pos.CENTER);
        tableLayout.setStyle("-fx-background-color: rgba(20, 14, 35, 0.85); -fx-padding: 35px 50px; -fx-background-radius: 20px; -fx-border-color: #d4af37; -fx-border-radius: 20px; -fx-border-width: 1.5px;");

        root.getChildren().addAll(title, instruction, tableLayout);
        setupScreen(root);

        // Card Deal Animation
        for (int i = 1; i <= 3; i++) {
            VBox cardBack = createFaceDownCard(i);
            tableLayout.getChildren().add(cardBack);

            cardBack.setTranslateY(-100);
            cardBack.setOpacity(0);

            TranslateTransition tt = new TranslateTransition(Duration.millis(500), cardBack);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition ft = new FadeTransition(Duration.millis(500), cardBack);
            ft.setToValue(1.0);

            ParallelTransition dealAnim = new ParallelTransition(tt, ft);
            dealAnim.setDelay(Duration.millis(i * 180));
            dealAnim.play();
        }
    }

    private VBox createFaceDownCard(int cardNum) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(160, 250);
        card.getStyleClass().add("card-back");

        DropShadow defaultGlow = new DropShadow();
        defaultGlow.setColor(Color.web("#d4af37", 0.3));
        defaultGlow.setRadius(10);
        card.setEffect(defaultGlow);

        Label cardPattern = new Label("✦");
        cardPattern.setStyle("-fx-font-size: 40px; -fx-text-fill: #d4af37;");

        Label cardLabel = new Label("CARD " + cardNum);
        cardLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8a7da8; -fx-font-weight: bold;");

        card.getChildren().addAll(cardPattern, cardLabel);

        // Subtle Hover Animation on Card
        card.setOnMouseEntered(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(120), card);
            tt.setToY(-8);
            tt.play();

            ScaleTransition st = new ScaleTransition(Duration.millis(120), card);
            st.setToX(1.04);
            st.setToY(1.04);
            st.play();

            DropShadow hoverGlow = new DropShadow();
            hoverGlow.setColor(Color.web("#f3c623", 0.8));
            hoverGlow.setRadius(20);
            card.setEffect(hoverGlow);
        });

        card.setOnMouseExited(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(120), card);
            tt.setToY(0);
            tt.play();

            ScaleTransition st = new ScaleTransition(Duration.millis(120), card);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            card.setEffect(defaultGlow);
        });

        // Click card flip animation
        card.setOnMouseClicked(e -> {
            ScaleTransition flipAnim = new ScaleTransition(Duration.millis(250), card);
            flipAnim.setToX(0);
            flipAnim.setOnFinished(evt -> flamesResult());
            flipAnim.play();
        });

        return card;
    }

    // PHASE 5: REVEAL DESTINY ======================================================
    public void flamesResult() {
        int score1 = getSimScore(this.name1, this.name2);
        int score2 = getSimScore(this.name2, this.name1);
        int sum = score1 + score2;

        boolean isOdd = (sum % 2 != 0);

        String baseRelation;
        switch (sum % 6) {
            case 1: baseRelation = "FRIENDS"; break;
            case 2: baseRelation = "LOVERS"; break;
            case 3: baseRelation = "ACQUAINTANCE"; break;
            case 4: baseRelation = "MARRIED"; break;
            case 5: baseRelation = "ENEMIES"; break;
            default: baseRelation = "SOULMATES"; break;
        }

        String cardTitleBanner;
        String psychBreakdown;
        String primaryColorHex = isOdd ? "#ff4d6d" : "#f3c623";

        if (isOdd) {
            cardTitleBanner = "THE " + baseRelation + " (REVERSED) — ";
            switch (baseRelation) {
                case "LOVERS": cardTitleBanner += "THE LOST CAUSE"; break;
                case "FRIENDS": cardTitleBanner += "THE FALSE MIRROR"; break;
                case "ENEMIES": cardTitleBanner += "OBSESSIVE RUIN"; break;
                case "MARRIED": cardTitleBanner += "BOUND IN CHAINS"; break;
                case "ACQUAINTANCE": cardTitleBanner += "DISTANT SHADOWS"; break;
                default: cardTitleBanner += "ANXIOUS OBSESSION"; break;
            }
            psychBreakdown = "An odd numerical frequency (" + sum + ") indicates an underlying tension and asymmetry between your inner expectations and their energy. " +
                             "While there is a powerful pull between " + this.name1 + " and " + this.name2 + ", unexpressed friction threatens to distort the harmony.";
        } else {
            cardTitleBanner = "THE " + baseRelation + " — HARMONIOUS BOND";
            psychBreakdown = "An even numerical frequency (" + sum + ") reflects aligned energies and mutual resonance between your subconscious minds. " +
                             "The connection between " + this.name1 + " and " + this.name2 + " flows naturally, balanced by shared underlying archetypes.";
        }

        var root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0b0712; -fx-padding: 30px;");

        Label titleBanner = new Label(cardTitleBanner);
        titleBanner.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + primaryColorHex + "; -fx-effect: dropshadow(gaussian, " + primaryColorHex + ", 12, 0, 0, 0);");
        applyPulseAnimation(titleBanner);

        HBox cardsGrid = new HBox(30);
        cardsGrid.setAlignment(Pos.CENTER);

        VBox card1 = createTarotCardPlaceholder(this.name1, "Seeker", primaryColorHex);
        VBox card2 = createTarotCardPlaceholder(this.name2, "Target", primaryColorHex);

        cardsGrid.getChildren().addAll(card1, card2);

        Label psychLabel = new Label(psychBreakdown);
        psychLabel.setWrapText(true);
        psychLabel.setMaxWidth(650);
        psychLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e0d6f6; -fx-text-alignment: center; -fx-line-spacing: 4px;");

        VBox analysisCard = new VBox(psychLabel);
        analysisCard.setAlignment(Pos.CENTER);
        analysisCard.setStyle("-fx-background-color: rgba(20, 14, 35, 0.9); -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: " + primaryColorHex + "; -fx-border-radius: 12px; -fx-border-width: 1px;");

        Button btn_restart = new Button("Consult the Deck Again");
        btn_restart.setStyle("-fx-font-size: 15px; -fx-background-color: " + primaryColorHex + "; -fx-text-fill: #0b0712; -fx-font-weight: bold; -fx-background-radius: 20px; -fx-padding: 10 25; -fx-cursor: hand;");

        root.getChildren().addAll(titleBanner, cardsGrid, analysisCard, btn_restart);

        fadeIn(titleBanner, 100);
        fadeIn(cardsGrid, 250);
        fadeIn(analysisCard, 400);
        fadeIn(btn_restart, 550);

        setupScreen(root);

        btn_restart.setOnAction(click -> mainMenu());
    }

    private VBox createTarotCardPlaceholder(String personName, String role, String accentColorHex) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200, 300);
        card.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #191228, #0e0a17); " +
            "-fx-border-color: " + accentColorHex + "; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 15px; " +
            "-fx-background-radius: 15px; " +
            "-fx-padding: 15px;"
        );

        DropShadow glow = new DropShadow();
        glow.setColor(Color.web(accentColorHex, 0.4));
        glow.setRadius(15);
        card.setEffect(glow);

        Label roleTag = new Label("[" + role.toUpperCase() + "]");
        roleTag.setStyle("-fx-font-size: 11px; -fx-text-fill: #8a7da8;");

        Label symbol = new Label("🔮");
        symbol.setStyle("-fx-font-size: 48px;");

        Label nameTag = new Label(personName);
        nameTag.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-text-alignment: center;");
        nameTag.setWrapText(true);

        card.getChildren().addAll(roleTag, symbol, nameTag);
        return card;
    }

    public static void main(String[] args) {
        launch();
    }

    public void setupScreen(VBox root) {
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setupScreen(StackPane root) {
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    public int getSimScore(String n1, String n2) {
        StringBuilder s2 = new StringBuilder(n2.toUpperCase().replace(" ", ""));
        int sim = 0;

        for (char c : n1.toUpperCase().replace(" ", "").toCharArray()) {
            int index = s2.indexOf(String.valueOf(c));
            if (index != -1) {
                sim++;
                s2.deleteCharAt(index);
            }
        }
        return sim;
    }
}