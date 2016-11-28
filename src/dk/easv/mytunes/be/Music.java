/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.mytunes.be;

import java.io.File;

/**
 *
 * @author Andrius
 */
public class Music {
    private File file;
    private String name,artist,category,time;
    public Music(File file){
        this.file=file;
        name=file.getName();
        artist="";
        category="";
        time="";
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return file.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
    
    
}
