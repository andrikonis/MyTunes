/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Music;
import mytunes.dal.MusicReader;

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
    public List<Music> getMusic(File entry){
        List<Music> list=new ArrayList();
        for (File file : MusicReader.getMusic(entry)){
            list.add(new Music(file));
        }
        return list;
    }
}
