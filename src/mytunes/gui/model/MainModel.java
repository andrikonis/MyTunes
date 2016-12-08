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
import mytunes.bll.MusicManager;
import mytunes.bll.SaveHandler;
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
    private static MainModel model;
    public static List<String> catList;
    private MainModel() {
        catList=new ArrayList();
        initCat();
        manager=MusicManager.getManager();
        allListProperty=new SimpleListProperty<>();
        allListProperty.set(FXCollections.observableArrayList());
        currientProperty=new SimpleStringProperty();
        currientProperty.setValue("");
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
            allListProperty.addAll(manager.getMusic(list,currient));
        } catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getSavedState(){
        try{
            allListProperty.addAll(SaveHandler.getSaved("state.ser"));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void saveState() throws Exception{
        try {
            SaveHandler.saveList(allListProperty.get(),"state.ser");
        } catch (IOException ex) {
            throw new Exception();
        }
    }
    public void setButton(){
        MainViewController.getController().buttonSet();
    }
}
