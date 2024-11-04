package pt.isec.pa.javalife.ui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.javalife.model.Manager;

public class BeginUI extends BorderPane {

    Manager GameManager;
    Button btnStart, btnExit;
    TextField txtDimension;
    Label lblDimension;

    public BeginUI(Manager gameManager) {
        this.GameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStart = new Button("Iniciar");
        btnStart.setMinWidth(52);
        btnExit = new Button("Sair");
        btnExit.setMinWidth(100);

        lblDimension = new Label("Dimensão do Ecossistema:");
        txtDimension = new TextField();
        txtDimension.setPromptText("Digite a dimensão");

        HBox hBoxButtons = new HBox(btnStart, btnExit);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(10);

        VBox vBoxMain = new VBox(lblDimension, txtDimension, hBoxButtons);
        vBoxMain.setAlignment(Pos.CENTER);
        vBoxMain.setSpacing(10);

        this.setCenter(vBoxMain);
    }

    private void registerHandlers() {
        GameManager.addPropertyChangeListener(evt -> {
            update();
        });
        btnStart.setOnAction(event -> {
            String dimensionText = txtDimension.getText();
            if (!dimensionText.isEmpty()) {
                try {
                    int dimension = Integer.parseInt(dimensionText);
                    GameManager.setEcosystemDimension(dimension);
                } catch (NumberFormatException e) {
                    // Handle invalid number input, maybe show a warning to the user
                    System.out.println("Invalid dimension entered: " + dimensionText);
                }
            }
            GameManager.Setup();
        });
        btnExit.setOnAction(event -> {
            Platform.exit();
        });
    }

    private void update() {
        this.setVisible(!GameManager.isRunning());
    }
}

