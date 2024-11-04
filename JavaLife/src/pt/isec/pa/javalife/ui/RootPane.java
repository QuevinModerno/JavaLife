package pt.isec.pa.javalife.ui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.ui.resources.CSSManager;
import pt.isec.pa.javalife.ui.uistates.BeginUI;
import pt.isec.pa.javalife.ui.uistates.GameUI;

public class RootPane extends BorderPane {
    Manager GameManager;
    GameMenu gameMenu;
    int Pixels;

    public RootPane(Manager GameManager, int pixels) {
        this.gameMenu = new GameMenu(GameManager);
        this.GameManager = GameManager;
        this.Pixels = pixels;
        createViews();
    }

    private void createViews() {
        setTop(
                new VBox( new GameMenu(GameManager))
        );
        CSSManager.applyCSS(this, "styles.css");

        StackPane stackPane = new StackPane(
                new BeginUI(this.GameManager),
                new GameUI(this.GameManager,Pixels)
        );
        /*stackPane.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1, 1, true, true, true, false)
                        )
                )
        );

         */
        this.setCenter(stackPane);

        ControlPane controlPane = new ControlPane(this.GameManager);
        this.setBottom(controlPane);

    }
}
