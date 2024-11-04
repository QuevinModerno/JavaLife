package pt.isec.pa.javalife.ui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.model.UiElement;

public class ListElements extends ListView<UiElement>
{
        Manager gameManager;
        MenuItem delete;
        MenuItem change;
        MenuItem select;

        public ListElements(Manager gameManager) {
                this.gameManager = gameManager;
                createViews();
                registerHandlers();
                update();
        }

        private void createViews() {
                select = new MenuItem("Select Element");
                change = new MenuItem("Edit element");
                delete = new MenuItem("Delete");
                this.setContextMenu(
                        new ContextMenu(select, delete, change)
                );
        }

        private void registerHandlers() {
                gameManager.addPropertyChangeListener(
                        evt -> {
                                Platform.runLater(this::update);
                        }
                );
                this.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) { // double-click
                            gameManager.removeElement(this.getSelectionModel().getSelectedItem());
                        }
                });

                select.setOnAction(
                        actionEvent ->
                                gameManager.selectElement(
                                        this.getSelectionModel()
                                                .getSelectedItem()
                                ));

                delete.setOnAction(
                        actionEvent ->
                                gameManager.removeElement(
                                        this.getSelectionModel()
                                        .getSelectedItem()
                        )
                );

                change.setOnAction( actionEvent -> {
                        Stage dlg = new Stage();
                        Button changeBtn = new Button("Ok");
                        changeBtn.setOnAction(evt -> {
                                UiElement element = this.getSelectionModel().getSelectedItem();
                                if (element==null)
                                        return;

                                dlg.close();
                        });
                        VBox rootPane = new VBox(changeBtn);
                        rootPane.setAlignment(Pos.CENTER);
                        Scene scene = new Scene(rootPane,200,60);
                        dlg.setScene(scene);
                        dlg.initModality(Modality.APPLICATION_MODAL);
                        dlg.initOwner(this.getScene().getWindow());
                        dlg.setAlwaysOnTop(true);
                        dlg.showAndWait();
                });

        }

        private void update()
        {
            this.getItems().clear();
            this.getItems().addAll(gameManager.getElements());
            this.setVisible(gameManager.isRunning());
        }
}