/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.be.Music;
import mytunes.bll.MusicManager;
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
        try {
            listProperty.addAll(manager.getMusic(new File(entry)));
        } catch (IOException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
