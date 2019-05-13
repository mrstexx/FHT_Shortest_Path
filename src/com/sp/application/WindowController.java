package com.sp.application;

import java.io.File;

import com.sp.graph.GraphHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class WindowController {

    private String fileName = "";

    @FXML
    private MenuItem menuImportNewData;
    @FXML
    private MenuItem menuCloseWindow;

    @FXML
    private MenuItem menuAboutUs;

    @FXML
    private Label labelFileName;

    @FXML
    private TextField fieldStartStation;

    @FXML
    private TextField fieldEndStation;

    @FXML
    private Button btnGetPath;

    @FXML
    private TextArea resultArea;

    @FXML
    void closeWindow(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void importNewData(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);
        this.fileName = selectedFile.getAbsolutePath();
        labelFileName.setText("Imported from: " + selectedFile.getAbsolutePath());
    }

    @FXML
    void menuAbout(ActionEvent event) {
        String title = "About us";
        String message = "FH Technikum Wien 2019\nFinal Project Algorithms and Data Structures\n\nAuthors:"
                + "\nStefan Miljevic - BIF2C\nDaniel Krottendorfer - BIF2C";
        showAlertMessage(message, title, AlertType.INFORMATION);
    }

    @FXML
    void execGetPath(ActionEvent event) {
        if (this.fieldStartStation.getText() == "" || this.fieldEndStation.getText() == "" || this.fileName == "") {
            String title = "Missing data";
            String message = "Something went wrong. Check you entered all fields.";
            showAlertMessage(message, title, AlertType.ERROR);
            return;
        }
        String result = GraphHelper.getShortestPath(this.fileName);
        this.resultArea.setText(result);
    }

    private void showAlertMessage(String message, String title, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
