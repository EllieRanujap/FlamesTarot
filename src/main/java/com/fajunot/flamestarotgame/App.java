/*
Group Members:
Fabian, Jaelica P.
Gunot, Vin Trixter B.
Pajunar, Willie Chad K.

Date Created: September 10, 2026
Date Updated: September 20, 2026
Summary: Flames Tarot Game — Strict Traditional FLAMES Algorithm & Dark Altar Reveal Mode
*/

package com.fajunot.flamestarotgame;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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

    // Atmospheric Prompt Pair Structure for Screen 3
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

    private final List<PromptSet> promptPresets = List.of(
        new PromptSet(
            "ANCHOR THE RITUAL",
            "Enter your name (The Seeker):",
            "Whose ghost still haunts your quiet hours?"
        ),
        new PromptSet(
            "THE SHADOW TETHER",
            "Your True Name:",
            "Who was your first unrequited obsession?"
        ),
        new PromptSet(
            "THE UNFORGOTTEN BIND",
            "Enter your name:",
            "The name of the one you can't forget:"
        ),
        new PromptSet(
            "ORACLE'S INQUIRY",
            "Your Name (The Seeker):",
            "Whose memory draws you back to the deck?"
        )
    );

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Flames Tarot Game — Oracle of the Subconscious");
        stage.setResizable(false);

        StackPane root = new StackPane();
        scene = new Scene(root, 1280, 720);

        // Load CSS with flexible resource lookup
        String[] cssPaths = {"/styles/style.css", "/style.css"};
        for (String path : cssPaths) {
            try {
                URL cssUrl = getClass().getResource(path);
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                    break;
                }
            } catch (Exception ignored) {}
        }

        mainMenu();
        stage.setScene(scene);
        stage.show();
    }

    // ==========================================
    // MULTI-STRATEGY CLASSLOADER IMAGE LOADER
    // ==========================================
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

    private void applyPulseAnimation(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(3200), node);
        st.setByX(0.030);
        st.setByY(0.030);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        st.setInterpolator(Interpolator.EASE_BOTH);
        st.play();
    }

    // ==========================================
    // SCREEN 1: MAIN MENU
    // ==========================================
    public void mainMenu() {
        StackPane root = createScreenWithBackground("1stBG");

        Label title = new Label("✨ FLAMES TAROT ✨");
        title.getStyleClass().add("gold-header-large");
        applyPulseAnimation(title);

        Label primerText = new Label(
            "The cards know what your conscious mind refuses to admit.\n" +
            "Step into the sanctuary to uncover the hidden frequencies\n" +
            "weaving between two tethered souls."
        );
        primerText.getStyleClass().add("primer-text");

        Button btnBegin = new Button("ENTER THE SANCTUARY");
        btnBegin.getStyleClass().add("gold-button");

        VBox layoutContainer = new VBox(28, title, primerText, btnBegin);
        layoutContainer.setAlignment(Pos.CENTER);
        layoutContainer.setMaxWidth(800);

        root.getChildren().add(layoutContainer);
        fadeIn(layoutContainer, 50);
        scene.setRoot(root);

        btnBegin.setOnAction(e -> playFastTransition(this::soulConsentScreen));
    }

    // ==========================================
    // SCREEN 2: SOUL CONSENT
    // ==========================================
    public void soulConsentScreen() {
        StackPane root = createScreenWithBackground("3rdBG");

        Label warningSigil = new Label("⚖");
        warningSigil.getStyleClass().add("sigil-red");
        applyPulseAnimation(warningSigil);

        Label warningTitle = new Label("THE SACRED COVENANT");
        warningTitle.getStyleClass().add("dark-red-header");

        Label consentText = new Label(
            "Before the cards are dealt, take pause:\n" +
            "By entering this ritual, you anchor a temporary fragment\n" +
            "of your psychological vibration into the deck.\n\n" +
            "What is revealed can never be unlearned."
        );
        consentText.getStyleClass().add("consent-text");

        Button btnConsent = new Button("I CONSENT — BIND MY ESSENCE");
        btnConsent.getStyleClass().add("dark-button");

        VBox layoutContainer = new VBox(22, warningSigil, warningTitle, consentText, btnConsent);
        layoutContainer.setAlignment(Pos.CENTER);
        layoutContainer.setMaxWidth(750);

        root.getChildren().add(layoutContainer);
        fadeIn(layoutContainer, 50);
        scene.setRoot(root);

        btnConsent.setOnAction(e -> playFastTransition(this::askNamesScreen));
    }

    // ==========================================
    // SCREEN 3: NAME INPUT FORM (DYNAMIC PROMPTS)
    // ==========================================
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

    // ==========================================
    // SCREEN 4: 6-CARD BLANK DECK (ALTAR LAYOUT)
    // ==========================================
    public void tarotDeckScreen() {
        StackPane root = createScreenWithBackground("6thBG");

        VBox mainLayout = new VBox(16);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20, 10, 10, 10));

        Label title = new Label("THE ALTAR OF DESTINY");
        title.getStyleClass().add("gold-header-medium");

        Label instruction = new Label("Select 1 of the 6 cards to unlock the fate between " + this.name1 + " & " + this.name2);
        instruction.getStyleClass().add("instruction-label");

        VBox headerText = new VBox(4, title, instruction);
        headerText.setAlignment(Pos.CENTER);

        GridPane cardGrid = new GridPane();
        cardGrid.setHgap(20);
        cardGrid.setVgap(14);
        cardGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < 6; i++) {
            final int cardIndex = i;
            StackPane cardNode = createBlankCardNode(i + 1);
            cardNode.setOnMouseClicked(e -> playFastTransition(() -> revealFate(cardIndex)));

            int row = i / 3;
            int col = i % 3;
            cardGrid.add(cardNode, col, row);
        }

        mainLayout.getChildren().addAll(headerText, cardGrid);
        root.getChildren().add(mainLayout);

        fadeIn(mainLayout, 50);
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
            if (blankImg.getHeight() > 0) {
                double targetWidth = 195.0 * (blankImg.getWidth() / blankImg.getHeight());
                cardImgView.setFitWidth(targetWidth);
                card.setMaxWidth(targetWidth);
                card.setPrefWidth(targetWidth);
            }
            cardImgView.setPreserveRatio(true);
            card.getChildren().add(cardImgView);
        } else {
            Label placeholder = new Label("✦\nCARD " + cardNumber);
            placeholder.getStyleClass().add("card-placeholder");
            card.getChildren().add(placeholder);
        }

        DropShadow defaultGlow = new DropShadow();
        defaultGlow.setColor(Color.web(GOLD_COLOR, 0.50));
        defaultGlow.setRadius(14);
        card.setEffect(defaultGlow);

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

    // ==========================================
    // SCREEN 5: THE FINAL SPREAD (THE READING)
    // ==========================================
    public void revealFate(int chosenCardIndex) {
        FlamesResult result = calculateFlames(this.name1, this.name2);

        StackPane root = createScreenWithBackground(result.isDark ? "7thBG" : "4thBG");

        VBox content = new VBox(18);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label titleBanner = new Label(result.outcomeName);
        titleBanner.getStyleClass().add(result.isDark ? "dark-red-header-small" : "gold-header-small");
        applyPulseAnimation(titleBanner);

        // Balanced side-by-side card container
        // Balanced side-by-side card container (tightened spacing)
        HBox altarGrid = new HBox(8); // Reduced from 24 to 8 to bring cards close together
        altarGrid.setAlignment(Pos.CENTER);
        altarGrid.setMaxWidth(Region.USE_PREF_SIZE); // Prevents HBox from taking extra horizontal space

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
        applyPulseAnimation(lineConnect);
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

        Label sigilOverlay = new Label(result.isDark ? "REVERSED\n" + result.unmatchedCount : "DIRECT\n" + result.unmatchedCount);
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

    // ==========================================
    // FLAMES LOGIC & ALGORITHM (STRICT TRADITIONAL)
    // ==========================================
    private static class FlamesResult {
        String outcomeName;
        String imageFileName;
        int compatibilityScore;
        String adviceText;
        boolean isDark;
        int unmatchedCount;

        FlamesResult(String outcomeName, String imageFileName, int compatibilityScore, String adviceText, boolean isDark, int unmatchedCount) {
            this.outcomeName = outcomeName;
            this.imageFileName = imageFileName;
            this.compatibilityScore = compatibilityScore;
            this.adviceText = adviceText;
            this.isDark = isDark;
            this.unmatchedCount = unmatchedCount;
        }
    }

    private FlamesResult calculateFlames(String n1, String n2) {
        String s1 = n1.toUpperCase().replaceAll("[^A-Z]", "");
        String s2 = n2.toUpperCase().replaceAll("[^A-Z]", "");

        List<Character> list1 = new ArrayList<>();
        for (char c : s1.toCharArray()) list1.add(c);

        List<Character> list2 = new ArrayList<>();
        for (char c : s2.toCharArray()) list2.add(c);

        for (int i = list1.size() - 1; i >= 0; i--) {
            char c = list1.get(i);
            if (list2.contains(c)) {
                list1.remove(i);
                list2.remove((Character) c);
            }
        }

        int N = list1.size() + list2.size();
        boolean isOdd = (N % 2 != 0);

        List<Character> flames = new ArrayList<>(List.of('F', 'L', 'A', 'M', 'E', 'S'));

        if (N > 0) {
            int startIndex = 0;
            while (flames.size() > 1) {
                int removeIndex = (startIndex + N - 1) % flames.size();
                flames.remove(removeIndex);
                startIndex = removeIndex % flames.size();
            }
        }

        char winningLetter = flames.get(0);
        int score = Math.min(99, Math.max(30, 100 - (N * 5)));

        String outcomeTitle;
        String adviceText;
        String imageFileName;

        switch (winningLetter) {
            case 'F':
                imageFileName = "friends";
                if (isOdd) {
                    outcomeTitle = "Friends (Reversed) — The Shadow Pact";
                    adviceText = "Underneath a quiet alliance lies an unexpressed distance between " + this.name1 + " and " + this.name2 + ". Unspoken expectations linger, threatening to turn genuine friendship into silent resentment if boundaries are left unsaid.";
                } else {
                    outcomeTitle = "FRIENDS — Harmonic Alliance";
                    adviceText = "The stars reflect a high-frequency alignment built on mutual trust and shared understanding between " + this.name1 + " and " + this.name2 + ". Protect this bond as an enduring sanctuary.";
                }
                break;

            case 'L':
                imageFileName = "lovers";
                if (isOdd) {
                    outcomeTitle = "The Lovers (Reversed) — The Lost Cause";
                    adviceText = "A volatile passion burns between " + this.name1 + " and " + this.name2 + ", consumed by obsessive longing and emotional tension. Unresolved fears mask true vulnerability, pulling both souls into an addictive cycle of attraction and ruin.";
                } else {
                    outcomeTitle = "LOVERS — Magnetic Devotion";
                    adviceText = "An intense magnetic resonance pulls " + this.name1 + " and " + this.name2 + " into harmony. Vulnerability and devotion build an elevated path forward.";
                }
                break;

            case 'A':
                imageFileName = "acquaintances";
                if (isOdd) {
                    outcomeTitle = "Affection (Reversed) — Unspoken Obsession";
                    adviceText = "Surface-level interactions conceal hidden intensity between " + this.name1 + " and " + this.name2 + ". The emotional tether remains unresolved, trapped between fascinated curiosity and fear of rejection.";
                } else {
                    outcomeTitle = "AFFECTION — Gentle Orbit";
                    adviceText = "Your tethered energy grows in steady orbit. " + this.name1 + " and " + this.name2 + " share an emerging bond that flourishes through honest conversation.";
                }
                break;

            case 'M':
                imageFileName = "marriage";
                if (isOdd) {
                    outcomeTitle = "Marriage (Reversed) — Bound in Golden Chains";
                    adviceText = "An inescapable covenant links " + this.name1 + " and " + this.name2 + ", but obligations threaten to overshadow organic affection. Friction arises as duty clashes with personal freedom.";
                } else {
                    outcomeTitle = "MARRIAGE — Sacred Covenant";
                    adviceText = "A rare permanent soul-contract binds " + this.name1 + " and " + this.name2 + ". Your paths intertwine for enduring mutual growth, long-term stability, and unconditional devotion.";
                }
                break;

            case 'E':
                imageFileName = "enemies";
                if (isOdd) {
                    outcomeTitle = "Enemies (Reversed) — Eternal Nemesis";
                    adviceText = "A dark friction dominates the energetic field between " + this.name1 + " and " + this.name2 + ". Ego clashes and deep psychological pride provoke recurring conflict that requires strict boundaries to break.";
                } else {
                    outcomeTitle = "ENEMIES — Fiery Friction";
                    adviceText = "High kinetic tension exists between " + this.name1 + " and " + this.name2 + ". If directed constructively, this friction can spark transformative personal growth.";
                }
                break;

            case 'S':
            default:
                imageFileName = "soulmates";
                if (isOdd) {
                    outcomeTitle = "Soulmates (Reversed) — The Broken Mirror";
                    adviceText = "A spiritual mirror links " + this.name1 + " and " + this.name2 + ", reflecting both light and deepest insecurities. Until inner shadows are acknowledged, this intense connection will feel like a fated burden.";
                } else {
                    outcomeTitle = "SOULMATES — Cosmic Synchronicity";
                    adviceText = "An otherworldly cosmic synchronization binds " + this.name1 + " and " + this.name2 + ". Two halves of a single spiritual entity walking the path of destiny.";
                }
                break;
        }

        return new FlamesResult(outcomeTitle, imageFileName, score, adviceText, isOdd, N);
    }

    public static void main(String[] args) {
        launch(args);
    }
}