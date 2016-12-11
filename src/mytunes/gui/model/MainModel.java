/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import mytunes.be.Music;
import mytunes.be.Playlist;
import mytunes.bll.MusicManager;
import mytunes.bll.SaveHandler;
import mytunes.bll.SearchManager;
import mytunes.gui.controller.MainViewController;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author Andrius
 */
public class MainModel {
    private MusicManager manager;
    private SimpleListProperty<Music> allListProperty;
    private SimpleStringProperty currientProperty;
    private SimpleListProperty<Playlist> playlists;
    private List<Music> currientList;
    private static MainModel model;
    public static List<String> catList;
    private boolean filtered;
    private MainModel() {
        currientList=new ArrayList();
        catList=new ArrayList();
        initCat();
        manager=MusicManager.getManager();
        allListProperty=new SimpleListProperty<>();
        allListProperty.set(FXCollections.observableArrayList());
        currientProperty=new SimpleStringProperty();
        currientProperty.setValue("");
        playlists=new SimpleListProperty<>();
        playlists.set(FXCollections.observableArrayList());
        filtered=false;
    }
    private void initCat(){
        catList.add("unknown");
        catList.add("pop");
        catList.add("rap");
        catList.add("house");
        catList.add("electro");
        catList.add("classic");
        catList.add("rock");
        catList.add("hip hop");
    }
    
    public static MainModel getModel(){
        if(model==null)model=new MainModel();
        return model;
    }
    public SimpleStringProperty getCurrientProperty(){
        return currientProperty;
    }
    public SimpleListProperty<Music> getAllListProperty() {
        return allListProperty;
    }
    public void getMusic(List<File> list,List<Music> currient)throws Exception{
        if(list==null)return;
        try {
            currientList.addAll(manager.getMusic(list,currient));
            allListProperty.setAll(currientList);
        } catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getSavedState(){
        try {
            currientList.addAll((List<Music>)SaveHandler.getSaved("state.ser"));
            allListProperty.setAll(currientList);
        } catch (IOException ex) {
            System.out.println("save file for all loaded songs is missing");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            playlists.addAll((List<Playlist>)SaveHandler.getSaved("playlists.ser"));
        } catch (IOException ex) {
            System.out.println("save file for all loaded playlists is missing");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void saveState() throws Exception{
        try {
            SaveHandler.saveList(currientList,"state.ser");
            SaveHandler.saveList(playlists.get(), "playlists.ser");
        } catch (IOException ex) {
            throw new Exception();
        }
    }
    public void setButton(){
        MainViewController.getController().buttonSet();
    }
    public void addPlaylist(String name) throws Exception{
        boolean add=true;
        for (Playlist cplaylist : playlists) {
            if(cplaylist.getName().equals(name))add=false;
        }
        if(add)playlists.add(new Playlist(name));
        else throw new Exception();
    }
    public void editPlaylist(Playlist playlist,String name) throws Exception{
        boolean add=true;
        for (Playlist cplaylist : playlists) {
            if(cplaylist.getName().equals(name)&&playlist!=cplaylist)add=false;
        }
        if(add)playlist.setName(name);
        else throw new Exception();
    }

    public SimpleListProperty<Playlist> getPlaylists() {
        return playlists;
    }
    public void addToPlaylist(List<Music> list,Playlist playlist){
        playlist.addMusic(manager.filer(list, playlist.getPlaylist()));
    }
    public void search(String query){
        if(!filtered){
            allListProperty.setAll(SearchManager.search(allListProperty.get(), query));
            filtered=true;
        }
        else{
            allListProperty.setAll(currientList);
            filtered=false;
        }
    }
    public boolean getFilterState(){
        return filtered;
    }
}
