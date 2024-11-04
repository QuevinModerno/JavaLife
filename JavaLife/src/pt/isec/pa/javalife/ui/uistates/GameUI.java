package pt.isec.pa.javalife.ui.uistates;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.UiElement;
import pt.isec.pa.javalife.ui.resources.ImageManager;

public class GameUI extends AnchorPane{

    int rows, columns;
    Manager GameManager;
    TilePane tilePane;
    int Pixels;

    public GameUI(Manager gameManager, int pixels)
    {
        this.GameManager = gameManager;
        this.rows = gameManager.getRows();
        this.columns = gameManager.getColumns();
        this.Pixels = pixels;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews()
    {
        tilePane = new TilePane(Orientation.HORIZONTAL);
        tilePane.setPrefColumns(3);
        tilePane.setPrefTileHeight(20);
        tilePane.setPrefTileWidth(20);
        FlowPane auxPane = new FlowPane(tilePane);
        auxPane.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(auxPane,0.0);
        AnchorPane.setBottomAnchor(auxPane,0.0);
        AnchorPane.setLeftAnchor(auxPane,0.0);
        AnchorPane.setRightAnchor(auxPane,0.0);
    }
    private void registerHandlers()
    {
        this.GameManager.addPropertyChangeListener(evt -> {
            update();
            Platform.runLater(this::update);
        });
        this.setFocusTraversable(true);
        this.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.P)
            {
                GameManager.Pause();
            }
        });
    }
    private void update()
    {

        double cellSize = 500 / (double) GameManager.getEcosystemDimension();
        Platform.runLater(() -> {
            getChildren().clear();
            for (UiElement element : GameManager.getElements() ) {

                Rectangle rectangle = getRectangle(element, cellSize);

              /*  if(element.getType() == Element.ANIMAL)
                {
                    ProgressBar strengthBar = getProgressBar(element, cellSize);


                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(rectangle, strengthBar);
                    stackPane.setLayoutX(element.getArea().esquerda() * cellSize);
                    stackPane.setLayoutY(element.getArea().cima() * cellSize);

                    this.getChildren().add(stackPane);
                }
                else{

               */
                this.getChildren().add(rectangle);
            }


        });
        this.setVisible(GameManager.isRunning());

    }

    private static ProgressBar getProgressBar(UiElement element, double cellSize) {
        ProgressBar strengthBar = new ProgressBar();
        strengthBar.setProgress(element.getStrength() / 100.0);
        strengthBar.setStyle("-fx-accent: green;");
        strengthBar.setTranslateY((element.getArea().height()) * cellSize / 2 + 10);
        strengthBar.setPrefWidth(50);

        return strengthBar;
    }

    private static Rectangle
    getRectangle(UiElement element, double cellSize) {
        double x = element.getArea().esquerda() * cellSize;
        double y = element.getArea().baixo() * cellSize;
        double width = (element.getArea().width()) * cellSize;
        double height = (element.getArea().height()) * cellSize;

        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setStroke(Color.color(1, 1, 0.95));
        rectangle.setFill(Color.BLACK);

        if(element.getType() == Element.FAUNA)
        {
            Image image = ImageManager.getImage("Animal2.png");
            if(image != null)
                rectangle.setFill(new ImagePattern(image));

        }
        else if(element.getType() == Element.FLORA)
        {
            double Strength = element.getStrength() ;

            if(Strength <= 30)
            {
                rectangle.setFill(Color.RED);
            }
            else
                if(Strength < 70)
                {
                    rectangle.setFill(Color.YELLOW);
                }
                else
                {
                    rectangle.setFill(Color.GREEN);
                }
        }
        return rectangle;
    }

    private ImageView getImageForSymbol(IElement element)
    {
        if(element == null){
            return null;
        }
        Image image;
        if (element.getType() == Element.FAUNA) {
            image = ImageManager.getImage("Animal.png");
        } else if (element.getType() == Element.FLORA) {
            image = ImageManager.getImage("Grass_good.png");
        } else {
            image = ImageManager.getImage("wall.png");
        }

        // Redimensionar a imagem para garantir que cabe na célula
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        return imageView;

    }
}


