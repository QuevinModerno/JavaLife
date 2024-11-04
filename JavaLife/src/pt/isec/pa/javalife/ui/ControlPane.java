package pt.isec.pa.javalife.ui;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import pt.isec.pa.javalife.model.Manager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class ControlPane extends HBox {
    private Manager gameManager;
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button resumeButton;
    private TextField stepField;
    private Button stepButton;

    public ControlPane(Manager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.gameManager.addPropertyChangeListener(evt -> {
            update();
            Platform.runLater(this::update);
        });
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        startButton = new Button("Start");
        stopButton = new Button("Stop");
        pauseButton = new Button("Pause");
        resumeButton = new Button("Resume");
        stepField = new TextField("1000");
        stepButton = new Button("Set time step");

        this.getChildren().addAll(startButton, stopButton, pauseButton, resumeButton, stepField, stepButton);
    }

    private void registerHandlers() {
        startButton.setOnAction(event -> gameManager.start());
        stopButton.setOnAction(event -> gameManager.stop());
        pauseButton.setOnAction(event -> gameManager.pause());
        resumeButton.setOnAction(event -> gameManager.resume());
        stepButton.setOnAction(event -> {
            String stepTxt = stepField.getText();
            Long step = Long.valueOf(stepTxt);
            if (step != null)
                gameManager.setStep(step);
        });
    }

    private void update() {
        this.setVisible(gameManager.isRunning());
    }
}

