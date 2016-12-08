/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Music;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author Andrius
 */
public class EditController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private ComboBox<String> comCat;
    @FXML
    private TextField txtTime;
    private Music music;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }  
    public void setMusic(Music music){
        this.music=music;
        txtTitle.setText(music.getName());
        txtArtist.setText(music.getArtist());
        comCat.getItems().setAll(FXCollections.observableArrayList(MainModel.getModel().catList));
        comCat.getSelectionModel().select(music.getCategory());
        txtTime.setText(music.getTime());
    }
    @FXML
    private void handleSave(ActionEvent event) {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure want to save changes?");
        if(alert.showAndWait().get()==ButtonType.CANCEL)return;
        music.setName(txtTitle.getText());
        music.setArtist(txtArtist.getText());
        music.setCategory(comCat.getSelectionModel().getSelectedItem());
        music.setTime(txtTime.getText());
        ((Stage)((Node)event.getTarget()).getScene().getWindow()).close();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage)((Node)event.getTarget()).getScene().getWindow()).close();
    }
    
}
