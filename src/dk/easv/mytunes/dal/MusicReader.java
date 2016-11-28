/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.mytunes.dal;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Andrius
 */
public class MusicReader {
    public static File[] getMusic(File entry){
        return entry.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".mp3"); }
        });
    }
}
