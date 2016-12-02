/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import mytunes.be.Music;
import mytunes.dal.MusicReader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author Andrius
 */
public class MusicManager {
    private static MusicManager manager;
    private MusicManager(){}
    public static MusicManager getManager(){
        if(manager==null)manager=new MusicManager();
        return manager;
    }
    public List<Music> getMusic(File entry) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        List<Music> list=new ArrayList();
        for (File file : MusicReader.getMusic(entry)){
            list.add(new Music(file));
        }
        return list;
    }
}
