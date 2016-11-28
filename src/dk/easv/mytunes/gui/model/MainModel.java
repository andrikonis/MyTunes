/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.mytunes.gui.model;

import java.io.File;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import dk.easv.mytunes.be.Music;
import dk.easv.mytunes.bll.MusicManager;

/**
 *
 * @author Andrius
 */
public class MainModel {
    private MusicManager manager;
    private SimpleListProperty<Music> listProperty;

    public MainModel() {
        manager=MusicManager.getManager();
        listProperty=new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList());
    }

    public SimpleListProperty<Music> getListProperty() {
        return listProperty;
    }
    public void getMusic(String entry){
        listProperty.addAll(manager.getMusic(new File(entry)));
    }
    
}
