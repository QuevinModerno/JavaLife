package pt.isec.pa.javalife.ui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import pt.isec.pa.javalife.model.Manager;

import java.io.File;

public class GameMenu extends MenuBar {
    Manager GameManager;
    Menu mnFile;
    Menu mnEdit;
    Menu mnSnapshot;
    Menu mnEvents;
    MenuItem mnUndo;
    MenuItem mnRedo;
    MenuItem mnNew;
    MenuItem mnSave;
    MenuItem mnLoad;
    MenuItem mnExit;
    MenuItem mnSun;
    MenuItem mnHerbicid;
    MenuItem mnStrength;
    MenuItem mnSaveSnapshot;
    MenuItem mnLoadSnapshot;

    public GameMenu(Manager gameManager) {
        this.GameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnFile = new Menu("File");
        mnEdit = new Menu("Edit");
        mnSnapshot = new Menu("Snapshot");
        mnEvents = new Menu("Events");
        this.getMenus().addAll(mnFile, mnEdit, mnSnapshot, mnEvents);

        // _* => permite utilizar o item atravez de alt + inicial
        mnNew = new MenuItem("_New");
        mnLoad = new MenuItem("_Load");
        mnSave = new MenuItem("_Save");
        mnExit = new MenuItem("_Exit");
        mnFile.getItems().addAll(mnNew, mnLoad, mnSave, mnExit);

        mnSaveSnapshot = new MenuItem("Save");
        mnLoadSnapshot = new MenuItem("Restore");
        mnSnapshot.getItems().addAll(mnSaveSnapshot, mnLoadSnapshot);

        mnUndo = new MenuItem("_Undo");
        mnRedo = new MenuItem("_Redo");
        mnEdit.getItems().addAll(mnUndo, mnRedo);

        mnSun = new MenuItem("_Apply Sun");
        mnHerbicid = new MenuItem("_Apply Herbicid");
        mnStrength = new MenuItem("_Inject Strength");
        mnEvents.getItems().addAll(mnSun, mnHerbicid, mnStrength);
    }

    private void registerHandlers() {
        this.GameManager.addPropertyChangeListener(evt -> {
            update();
            Platform.runLater(this::update);
        });

        mnNew.setOnAction(e -> {
            GameManager.reset(10, 10);
        });

        mnExit.setOnAction(e -> {
            Platform.exit();
        });

        mnSave.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File save...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Drawing (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) {
                GameManager.save(hFile);
            }
        });

        mnLoad.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Drawing (*.dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );
            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (hFile != null) {
                GameManager.load(hFile);
            }
        });

        mnSun.setOnAction(e -> {
            GameManager.applySun();
        });
         mnUndo.setOnAction(e -> {
             GameManager.undo();
         });
         mnRedo.setOnAction(e -> {
             GameManager.redo();
         });

        mnSaveSnapshot.setOnAction(e -> {
            GameManager.saveSnapshot();
        });

        mnLoadSnapshot.setOnAction(e -> {
            GameManager.restoreSnapshot();
        });

        mnHerbicid.setOnAction(e -> {
            GameManager.applyHerbicide();
        });

        mnStrength.setOnAction(e -> {
            GameManager.injectStrength();
        });
    }

    private void update() {
        this.setVisible(GameManager.isRunning());
    }
}
