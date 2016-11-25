/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
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
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import mytunes.bll.MusicManager;

/**
 * FXML Controller class
 *
 * @author Andrius
 */
public class MainViewController implements Initializable {

    @FXML
    private TableView<Music> tbAll;
    @FXML
    private TableColumn<Music,String> clTitle;
    @FXML
    private TableColumn<Music,String> clArtist;
    @FXML
    private TableColumn<Music,String> clCat;
    @FXML
    private TableColumn<Music,String> clTime;
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
    
    private BasicPlayer player=new BasicPlayer();
    private MusicManager manager;
    private Music currient;
    private Thread nextSong;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager=MusicManager.getManager();
        tbAll.setItems(FXCollections.observableArrayList(manager.getMusic(new File("D:/muzika"))));//define music folder
        clTitle.setCellValueFactory(new PropertyValueFactory("name"));
        nextSong=new Thread(){
            @Override
            public void run(){
                int i=0;
                while(true){
                    try {
                        Thread.sleep(1000);
                        if(player.getStatus()==2){
                        tbAll.getSelectionModel().select(currient);
                        tbAll.getSelectionModel().selectNext();
                        player.open(tbAll.getSelectionModel().getSelectedItem().getFile());
                        player.play();
                        }
                    } 
                    catch (InterruptedException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (BasicPlayerException ex) {
                        Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                }
        };
        nextSong.start();
    }    

    @FXML
    private void handlePlaying(ActionEvent event) {
        if(check()&&player.getStatus()==0){
            try {
                player.pause();
                btnPlay.setText("Play");
            }
            catch (BasicPlayerException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(check()&&player.getStatus()==1){
            try {
                player.resume();
                btnPlay.setText("Pause");
            }
            catch (BasicPlayerException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                player.open(tbAll.getSelectionModel().getSelectedItem().getFile());
                player.play();
                currient=tbAll.getSelectionModel().getSelectedItem();
                btnPlay.setText("Pause");
            }
            catch (BasicPlayerException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private boolean check(){
        if(tbAll.getSelectionModel().getSelectedItem()==currient)return true;
        else return false; 
    }
    
    @FXML
    private void buttonSet(){
        if(check()){
            btnPlay.setText("Pause");
        }
        else{
            btnPlay.setText("Play");
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        nextSong.stop();
        try {
            player.stop();
        } 
        catch (BasicPlayerException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
}
