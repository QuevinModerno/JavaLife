package pt.isec.pa.javalife.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.javalife.Main;
import pt.isec.pa.javalife.model.Manager;

public class MainJfx extends Application {
    private Manager GameManager;

    @Override
    public void init() throws Exception {
        super.init();
        GameManager = Main.gameManager;
    }


    @Override
    public void start(Stage stage) throws Exception {
        newStageForMain(stage, "JavaLife");
        newStageForListing(stage.getX() + stage.getWidth(), stage.getY());
        newStageForConfig(stage.getX() - stage.getWidth(), stage.getY());
    }
    private void newStageForMain(Stage stage, String title) {
        RootPane root = new RootPane(GameManager,500);
        Scene scene = new Scene(root,500,500);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(680);
        stage.setMinHeight(630);
        stage.show();
    }

    private void newStageForListing(double x, double y) {
        Stage stage = new Stage();
        ListElements listPane = new ListElements(GameManager);
        Scene scene = new Scene(listPane,300,400);
        stage.setScene(scene);
        stage.setTitle("Drawing List");
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }
    private void newStageForConfig(double x, double y) {
        Stage stage = new Stage();
        ElementsConfig config = new ElementsConfig(GameManager);
        Scene scene = new Scene(config,300*2,400);
        stage.setScene(scene);
        stage.setTitle("Config");
        stage.setX(0);
        stage.setY(y);
        stage.show();
    }
}
