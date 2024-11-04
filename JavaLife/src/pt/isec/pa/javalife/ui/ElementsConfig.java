package pt.isec.pa.javalife.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.data.elements.Element;

public class ElementsConfig extends VBox {
    private Manager gameManager;

    private TextField strengthField;
    private TextField xPosField;
    private TextField yPosField;
    private TextField heightField;
    private TextField widthField;
    private ComboBox<String> elementTypeComboBox;
    private Button addElementButton;
    private Button editElementButton;
    private Button deleteElementButton;

    public ElementsConfig(Manager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        Label strengthLabel = new Label("Strength (1-100):");
        strengthField = new TextField();

        Label xPosLabel = new Label("Position X:");
        xPosField = new TextField();

        Label yPosLabel = new Label("Position Y:");
        yPosField = new TextField();

        Label heightLabel = new Label("Height:");
        heightField = new TextField();

        Label widthLabel = new Label("Width:");
        widthField = new TextField();

        Label typeLabel = new Label("Element Type:");
        elementTypeComboBox = new ComboBox<>();
        elementTypeComboBox.getItems().addAll("Inanimate", "Flora", "Fauna");


        addElementButton = new Button("Add Element");
        editElementButton = new Button("Edit Element");
        deleteElementButton = new Button("Delete Element");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(strengthLabel, 0, 0);
        gridPane.add(strengthField, 1, 0);
        gridPane.add(xPosLabel, 0, 1);
        gridPane.add(xPosField, 1, 1);
        gridPane.add(yPosLabel, 0, 2);
        gridPane.add(yPosField, 1, 2);
        gridPane.add(typeLabel, 0, 3);

        gridPane.add(heightLabel, 3, 1);
        gridPane.add(heightField, 4, 1);
        gridPane.add(widthLabel, 3, 2);
        gridPane.add(widthField, 4, 2);

        gridPane.add(elementTypeComboBox, 1, 3);
        gridPane.add(addElementButton, 0, 4);
        gridPane.add(editElementButton, 1, 4);
        gridPane.add(deleteElementButton, 0, 5);

        this.getChildren().addAll(gridPane);
    }

    private void registerHandlers() {
        this.gameManager.addPropertyChangeListener(evt -> {
            update();
        });
        addElementButton.setOnAction(event -> addElement());
        editElementButton.setOnAction(event -> editElement());
        deleteElementButton.setOnAction(event -> deleteElement());

        elementTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String type = elementTypeComboBox.getValue();
            if (type != null && type.equalsIgnoreCase("inanimate")) {
                strengthField.setEditable(false);
                strengthField.setText("");
            } else {
                strengthField.setEditable(true);
            }
        });
    }

    private void update() {
        this.setVisible(gameManager.isRunning());
    }


    private void addElement() {
        String strengthStr = strengthField.getText();
        String xPosStr = xPosField.getText();
        String yPosStr = yPosField.getText();
        String heightStr = heightField.getText();
        String widthStr = widthField.getText();
        String type = elementTypeComboBox.getValue();

        // Validate inputs
        double strength;
        int xPos, yPos, height, width;

        try {
            xPos = Integer.parseInt(xPosStr);
            yPos = Integer.parseInt(yPosStr);

            height = Integer.parseInt(heightStr);
            width = Integer.parseInt(widthStr);

            if (xPos < 1 && xPos > gameManager.getEcosystemDimension()
                    && yPos < 1 && yPos > gameManager.getEcosystemDimension()) {
                throw new IllegalArgumentException("Position out of limits");
            }

            if (height <= 0 || width <= 0 || xPos + height > gameManager.getRows() || yPos + height > gameManager.getColumns()) {
                return;
            }


        } catch (NumberFormatException e) {
            // Show error message: invalid position input
            return;
        }
        try {
            if (strengthStr.isEmpty() && type.equals("Inanimate")) {
                strength = 0;
            } else {
                strength = Double.parseDouble(strengthStr);
                if (strength < 1 || strength > 100) {
                    throw new IllegalArgumentException("Strength must be between 1 and 100.");
                }
            }
        } catch (NumberFormatException e) {
            // Show error message: invalid strength input
            return;
        }

        gameManager.addElement(xPos, yPos, height, width, strength, type);
    }

    private void editElement() {
        String strengthStr = strengthField.getText();
        String xPosStr = xPosField.getText();
        String yPosStr = yPosField.getText();
        String heightStr = heightField.getText();
        String widthStr = widthField.getText();
        String type = elementTypeComboBox.getValue();

        // Validate inputs
        double strength;
        int xPos, yPos, height, width;

        try {
            xPos = Integer.parseInt(xPosStr);
            yPos = Integer.parseInt(yPosStr);

            height = Integer.parseInt(heightStr);
            width = Integer.parseInt(widthStr);

            if (xPos < 1 && xPos > gameManager.getEcosystemDimension()
                    && yPos < 1 && yPos > gameManager.getEcosystemDimension()) {
                throw new IllegalArgumentException("Position out of limits");
            }

            if (height <= 0 || width <= 0 || xPos + height > gameManager.getRows() || yPos + height > gameManager.getColumns()) {
                return;
            }


        } catch (NumberFormatException e) {
            // Show error message: invalid position input
            return;
        }
        try {
            if (strengthStr.isEmpty() && type.equals("Inanimate")) {
                strength = 0;
            } else {
                strength = Double.parseDouble(strengthStr);
                if (strength < 1 || strength > 100) {
                    throw new IllegalArgumentException("Strength must be between 1 and 100.");
                }
            }
        } catch (NumberFormatException e) {
            // Show error message: invalid strength input
            return;
        }

        gameManager.editElement(xPos, yPos, height, width, strength, type);
    }

    private void deleteElement() {
        // Implement logic to delete selected element
        gameManager.deleteElement();
    }
}
