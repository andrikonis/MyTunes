/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mytunes.be.Music;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.JavaLayerException;
import mytunes.bll.player.MyPlayer;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author Andrius
 */
public class MainViewController implements Initializable {

    @FXML
    private TableView<Music> tbAll;
    @FXML
    private TableColumn<Music, String> clTitle;
    @FXML
    private TableColumn<Music, String> clArtist;
    @FXML
    private TableColumn<Music, String> clCat;
    @FXML
    private TableColumn<Music, String> clTime;
    @FXML
    private Slider sldVolume;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnFoward;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblNow;

    private MyPlayer player = new MyPlayer();
    private MainModel model;

    /**
     * Initializes the controller class.
     */
    @Override
        public void initialize(URL url, ResourceBundle rb) {
            model = new MainModel();
            model.getMusic("D:/muzika");//define music folder
            tbAll.itemsProperty().bind(model.getListProperty());
            clTitle.setCellValueFactory(new PropertyValueFactory("name"));
            clArtist.setCellValueFactory(new PropertyValueFactory("artist"));
            clCat.setCellValueFactory(new PropertyValueFactory("category"));
            clTime.setCellValueFactory(new PropertyValueFactory("time"));
            tbAll.getSelectionModel().selectFirst();
        }

        @FXML
        private void handlePlaying(ActionEvent event) throws FileNotFoundException {
            try{
            if (check() && player.getStatus() == 0) {
                    player.pause();
                    buttonSet();
            } 
            else if (check() && player.getStatus() == 1) {
                    player.resume();
                    buttonSet();
            }
            else {
                player.openPlaylist(tbAll.getItems(),tbAll.getSelectionModel().getSelectedItem());
                player.play();
                buttonSet();
            }
            } catch (JavaLayerException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        /**
         * checks if selected song is curriently playing
         * @return 
         */
        private boolean check() {
            if (tbAll.getSelectionModel().getSelectedItem() == player.getCurrient()) {
                return true;
            } else {
                return false;
            }
        }
        /**
         * stets button to "Play" and "Pause" according 
         * to curriently playing song
         */
        @FXML
        private void buttonSet() {
            if (check()) {
                btnPlay.setText("Pause");
            } else {
                btnPlay.setText("Play");
            }
        }

        @FXML
        private void handleClose(ActionEvent event) {
            System.exit(0);
        }

    }
