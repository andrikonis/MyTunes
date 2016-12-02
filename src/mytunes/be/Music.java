/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author Andrius
 */
public class Music {
    private File file;
    private String name,artist,category,time;
    private static final List<String> catList=new ArrayList();
    public Music(File file) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        initCat();
        this.file=file;
        name=getFileTitle();
        artist=getFileArtist();
        category=getFileCategory();
        time=getFileLength();
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
    private String getFileLength() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        String s="";
        AudioFile audio=AudioFileIO.read(file);
        AudioHeader header=audio.getAudioHeader();
        int sec=header.getTrackLength();
        if(sec==0)return "unknown";
        s+=sec/60+":";
        if(sec%60<10)s+="0"+sec%60;
        else s+=sec%60;
        return s;
    }
    private String getFileArtist() throws CannotReadException, IOException, ReadOnlyFileException, InvalidAudioFrameException, TagException {
        String s="";
        AudioFile audio=AudioFileIO.read(file);
        Tag tag=audio.getTag();
        try{
            s+=tag.getFirst(FieldKey.ARTIST);
        }
        catch(Exception e){}
        return s;
    }
    private String getFileTitle() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        String s="";
        AudioFile audio=AudioFileIO.read(file);
        Tag tag=audio.getTag();
        try{
            s+=tag.getFirst(FieldKey.TITLE);
            if(s.trim().length()==0)throw new Exception();
        }
        catch(Exception e){
            s=file.getName();
            s=s.substring(0,s.length()-4);
        }
        return s;
    }
    private String getFileCategory() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        String s=catList.get(0);
        AudioFile audio=AudioFileIO.read(file);
        Tag tag=audio.getTag();
        for (String string : catList) {
            try{
                String mystr=string;
                if(string.contains(" "))mystr=string.split(" ")[0];
                if(tag.getFirst(FieldKey.GENRE).toLowerCase().contains(mystr))s=string;
            }
            catch(Exception e){}
        }
        return s;
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
