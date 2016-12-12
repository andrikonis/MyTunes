/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrius
 */
public class MusicReader {
    /**
     * filters out all non-mp3 format files
     * @param list
     * @return List<Files>
     */
    public static List<File> getMusic(List<File> list){
        List<File> fileList=new ArrayList();
        for (File file : list) {
            if(file.getName().endsWith(".mp3"))fileList.add(file);
        }
        return fileList;
    }
}
