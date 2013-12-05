package de.seliger.fxbackup.backup;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;

public class BackupViewController implements Initializable {

    @FXML
    private TreeTableView<File> backupTree;

    @FXML
    private AnchorPane treePane;

    public void initialize(URL location, ResourceBundle resources) {
        new FileBrowserTreeTableView(backupTree).build();
    }
}
