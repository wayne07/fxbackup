package de.seliger.fxbackup;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxBackupMain extends Application {


    private Parent parent;

    @Override
    public void init() throws Exception {
        loadViewResource();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(parent, 800, 600);
        stage.setScene(scene);
        stage.setTitle("FXBackup preview");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadViewResource() {
        try {
            parent = FXMLLoader.load(FxBackupMain.class.getResource("FxBackupMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
