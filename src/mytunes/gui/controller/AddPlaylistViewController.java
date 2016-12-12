/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author deividukxxsssss
 */
public class AddPlaylistViewController implements Initializable {

    private Playlist playlist; 
            
    @FXML
    private TextField txtName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * loads the playlist for editing its name
     * @param playlist 
     */
    public void loadPlaylist(Playlist playlist) {
        this.playlist = playlist;
        txtName.setText(playlist.getName());
    }
    /**
     * saves the changes made for playlist name
     * and closes the window
     * @param event 
     */
    @FXML
    public void savePlaylist(ActionEvent event) {
        if (playlist == null){
            try {
                MainModel.getModel().addPlaylist(txtName.getText().trim());
            } catch (Exception ex) {
                Alert alert=new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Unable to create playlist, name already exists");
                alert.show();
                return;
            }
        }
        else {
            try {
                MainModel.getModel().editPlaylist(playlist, txtName.getText().trim());
            } catch (Exception ex) {
                Alert alert=new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Unable to edit playlist, name already exists");
                alert.show();
                return;
            }
        }
        ((Stage)((Node)event.getTarget()).getScene().getWindow()).close();
    }
   
    
}
