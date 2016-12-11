/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mytunes.be.Music;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.MainModel;
import mytunes.gui.model.PlayerModel;

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
    private TableView<Playlist> tbPlay;
    @FXML
    private TableColumn<Playlist, String> clPlayName;

    private PlayerModel player = PlayerModel.gelPlayerModel();
    private MainModel model;
    private static MainViewController controller;
    @FXML
    private TableColumn<Playlist, String> clPlayCount;
    @FXML
    private TableColumn<Playlist, String> clPlayTime;
    @FXML
    private ListView<Music> lwPlay;
    @FXML
    private Label lblNow;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller=this;
        model = MainModel.getModel();
        tbAll.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbAll.itemsProperty().bind(model.getAllListProperty());
        clTitle.setCellValueFactory(new PropertyValueFactory("name"));
        clArtist.setCellValueFactory(new PropertyValueFactory("artist"));
        clCat.setCellValueFactory(new PropertyValueFactory("category"));
        clTime.setCellValueFactory(new PropertyValueFactory("time"));
        lblNow.textProperty().bind(model.getCurrientProperty());
        model.getSavedState();
        tbAll.getSelectionModel().selectFirst();
        tbPlay.itemsProperty().bind(model.getPlaylists());
        tbPlay.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        clPlayName.setCellValueFactory(new PropertyValueFactory("name"));
        clPlayCount.setCellValueFactory(new PropertyValueFactory("playlistSize"));
        clPlayTime.setCellValueFactory(new PropertyValueFactory("time"));
        tbPlay.getSelectionModel().selectFirst();
        lwPlay.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        handleListRefresh();
    }

    @FXML
    private void handlePlaying(ActionEvent event) {
        if(tbPlay.isFocused())player.play(tbPlay.getSelectionModel().getSelectedItem().getPlaylist());
        else if(lwPlay.isFocused())player.play(tbPlay.getSelectionModel().getSelectedItem().getPlaylist(),lwPlay.getSelectionModel().getSelectedIndex());
        else player.play(tbAll.getItems(),tbAll.getSelectionModel().getSelectedIndex());
        buttonSet();
        handleVolume();
    }

    /**
     * stets button to "Play" and "Pause" according to curriently playing song
     */
    @FXML
    public void buttonSet() {
        if(tbPlay.isFocused()){
            btnPlay.setGraphic(new ImageView("file:images/play.png"));
        }
        else if(lwPlay.isFocused()){
            if (player.check(lwPlay.getSelectionModel().getSelectedItem())&&player.getStatus()!=1)btnPlay.setGraphic(new ImageView("file:images/pause.png"));
            else btnPlay.setGraphic(new ImageView("file:images/play.png"));
        }
        else{
            if (player.check(tbAll.getSelectionModel().getSelectedItem())&&player.getStatus()!=1)btnPlay.setGraphic(new ImageView("file:images/pause.png"));
            else btnPlay.setGraphic(new ImageView("file:images/play.png"));
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        try {
            model.saveState();
        } catch (Exception ex) {
            Alert alert=new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Program was unable to save your currient state");
            alert.showAndWait();
        }
        System.exit(0);
    }

    @FXML
    public void handleAddMusic(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Music", "*.mp3"));
        fc.getExtensionFilters().add(new ExtensionFilter("All", "*.*"));
        fc.setTitle("Select music");
        try {
            model.getMusic(fc.showOpenMultipleDialog(((Node) event.getTarget()).getScene().getWindow()), tbAll.getItems());
        } catch (Exception ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error loading music");
            alert.setContentText("The selected files may be corrupted or missing");
            alert.show();
        }
        tbAll.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void handleVolume(){
        player.setVolume(sldVolume.getValue());
    }
    @FXML
    public void handleNext(){
        player.next();
        buttonSet();
    }
    @FXML
    public void handlePrevious(){
        player.previous();
        buttonSet();
    }
    @FXML
    public void handleEditSong(ActionEvent event){
        try {
            FXMLLoader loader=new FXMLLoader(new URL("file:src/mytunes/gui/view/EditView.fxml"));
            Parent root=loader.load();
            ((EditController)loader.getController()).setMusic(tbAll.getSelectionModel().getSelectedItem());
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Edit");
            stage.showAndWait();
            tbAll.refresh();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleDeleteSong(){
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conformation");
        alert.setContentText("Do you really want to remove song from the list");
        if(alert.showAndWait().get()==ButtonType.CANCEL)return;
        model.getAllListProperty().removeAll(tbAll.getSelectionModel().getSelectedItems());
        tbAll.getSelectionModel().selectFirst();
    }
    public static MainViewController getController(){
        return controller;
    }

    @FXML
    private void handleAddToPlaylist(ActionEvent event) {
        model.addToPlaylist(tbAll.getSelectionModel().getSelectedItems(), tbPlay.getSelectionModel().getSelectedItem());
        handleListRefresh();
        tbPlay.refresh();
    }

    @FXML
    private void handelAddPlaylist(ActionEvent event) {
        try {
            Parent root=FXMLLoader.load(new URL("file:src/mytunes/gui/view/AddPlaylistView.fxml"));
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Add playlist");
            stage.showAndWait();
            tbPlay.getSelectionModel().selectFirst();
            handleListRefresh();
        }catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEditPlaylist(ActionEvent event) {
        System.out.println(tbPlay.getSelectionModel().selectedItemProperty().get());
        try {
            FXMLLoader loader=new FXMLLoader(new URL("file:src/mytunes/gui/view/AddPlaylistView.fxml"));
            Parent root=loader.load();
            ((AddPlaylistViewController)loader.getController()).loadPlaylist(tbPlay.getSelectionModel().getSelectedItem());
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Add playlist");
            stage.showAndWait();
            tbPlay.refresh();
            handleListRefresh();
        }catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleDeletePlaylist(ActionEvent event) {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conformation");
        alert.setContentText("Do you really want to remove playlist from the list");
        if(alert.showAndWait().get()==ButtonType.CANCEL)return;
        model.getPlaylists().remove(tbPlay.getSelectionModel().getSelectedItem());
        tbPlay.getSelectionModel().selectFirst();
        handleListRefresh();
    }

    @FXML
    private void handleUp(ActionEvent event) {
        tbPlay.getSelectionModel().getSelectedItem().moveUp(lwPlay.getSelectionModel().getSelectedIndex());
        handleListRefresh();
    }

    @FXML
    private void handleDown(ActionEvent event) {
        tbPlay.getSelectionModel().getSelectedItem().moveDown(lwPlay.getSelectionModel().getSelectedIndex());
        handleListRefresh();
    }

    @FXML
    private void handleDeleteFromPlaylist(ActionEvent event) {
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conformation");
        alert.setContentText("Do you really want to remove songs playlist from the list");
        if(alert.showAndWait().get()==ButtonType.CANCEL)return;
        tbPlay.getSelectionModel().getSelectedItem().getPlaylist().removeAll(lwPlay.getSelectionModel().getSelectedItems());
        handleListRefresh();
        tbPlay.refresh();
        lwPlay.refresh();
        lwPlay.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleListRefresh() {
        try{
            lwPlay.setItems(FXCollections.observableArrayList(tbPlay.getSelectionModel().getSelectedItem().getPlaylist()));
        }catch(Exception ex){}
    }
    @FXML
    private void handleSearch() {
        model.search(txtSearch.getText().trim());
        setSearchButton();
        tbAll.getSelectionModel().selectFirst();
    }
    @FXML
    private void setSearchButton(){
        if(txtSearch.getText().trim().length()==0&&!model.getFilterState())btnSearch.setDisable(true);
        else btnSearch.setDisable(false);
        if(model.getFilterState())btnSearch.setGraphic(new ImageView("file:images/clear.png"));
        else btnSearch.setGraphic(new ImageView("file:images/search.png"));
    }
}
