package de.seliger.fx.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TreeTableDemo extends Application {

    private Parent parent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        parent = FXMLLoader.load(TreeTableDemo.class.getResource("TreeTableDemo.fxml"));
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(parent, 800, 600);
        stage.setScene(scene);
        stage.setTitle("FXBackup preview");
        stage.show();
    }

}
