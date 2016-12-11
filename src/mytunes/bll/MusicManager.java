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
    public List<Music> getMusic(List<File> list,List<Music> currient) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        return createMusicList(filerExisting(MusicReader.getMusic(list), currient));
    }
    private List<Music> createMusicList(List<File> list)throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        List<Music> musicList=new ArrayList();
        for (File file : list) {
            musicList.add(new Music(file));
        }
        return musicList;
    }
    private List<File> filerExisting(List<File> list,List<Music> currient){
        List<File> fileList=new ArrayList();
            for (File file : list) {
                boolean add=true;
                for (Music music : currient) {
                    if(music.getFile().getName().equals(file.getName()))add=false;
                }
                if(add)fileList.add(file);
            }
        return fileList;
    }
    public List<Music> filer(List<Music> addList,List<Music> currientList){
        List<Music> list=new ArrayList();
        for (Music amusic : addList) {
           boolean add=true;
            for (Music cmusic : currientList) {
                if(cmusic.getFile().getName().equals(amusic.getFile().getName()))add=false;
            }
            if(add)list.add(amusic);
        }
        return list;
    }
}
