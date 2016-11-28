/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.mytunes.gui.controller;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dk.easv.mytunes.be.Music;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.cell.PropertyValueFactory;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
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

    private BasicPlayer player = new BasicPlayer();
    private MainModel model;
    private Music currient;

    private Thread nextSong = new Thread() {
        @Override
        public void run() {
            for (int i = tbAll.getSelectionModel().getSelectedIndex(); i < tbAll.getItems().size(); i++) {
                try {
                    currient = tbAll.getItems().get(i);
                    player.open(currient.getFile());
                    player.play();
                    while (player.getStatus() != 2) {
                    }
                    btnPlay.setText("Play");
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

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
        }

        @FXML
        private void handlePlaying(ActionEvent event) {
            if (check() && player.getStatus() == 0) {
                try {
                    player.pause();
                    btnPlay.setText("Play");
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (check() && player.getStatus() == 1) {
                try {
                    player.resume();
                    btnPlay.setText("Pause");
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                nextSong.start();
                btnPlay.setText("Pause");
            }
        }

        private boolean check() {
            if (tbAll.getSelectionModel().getSelectedItem() == currient) {
                return true;
            } else {
                return false;
            }
        }

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
            nextSong.stop();
            try {
                player.stop();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }

    }
