/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import mytunes.gui.model.MainModel;
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
public class Music implements Serializable{
    private File file;
    private String name,artist,category,time;
    /**
     * creates new music entity with given file
     * @param file
     * @throws IOException
     * @throws CannotReadException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException 
     */
    public Music(File file) throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        this.file=file;
        name=getFileTitle();
        artist=getFileArtist();
        category=getFileCategory();
        time=getFileLength();
    }
    /**
     * creates a copy of given music entity
     * @param music 
     */
    public Music(Music music){
        file=new File(music.getFile().getAbsolutePath());
        name=new String(music.getName());
        artist=new String(music.getArtist());
        category=new String(music.getCategory());
        time=new String(music.getTime());
    }
    /**
     * reads the metadata of file and tries to get the time of given file
     * if file lenght if 0 seconds it sets time as "unknown"
     * @return String
     * @throws IOException
     * @throws CannotReadException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException 
     */
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
    /**
     * tries to read tags of given file and get the artist
     * if can't do that it returns an empty string
     * @return String
     * @throws CannotReadException
     * @throws IOException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException
     * @throws TagException 
     */
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
    /**
     * tries to read tags of the given file ant get the title
     * if it can't returns file name without ".mp3"
     * @return String
     * @throws CannotReadException
     * @throws IOException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException 
     */
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
    /**
     * tries to read file tags and get its genre
     * it compares string from tags with strings from defined list
     * in MainModel class
     * returns "unknown" if it can't define its genre
     * @return String
     * @throws CannotReadException
     * @throws IOException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException 
     */
    private String getFileCategory() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        String s=MainModel.getModel().catList.get(0);
        AudioFile audio=AudioFileIO.read(file);
        Tag tag=audio.getTag();
        for (String string : MainModel.getModel().catList) {
            try{
                String mystr=string;
                if(string.contains(" "))mystr=string.split(" ")[0];
                if(tag.getFirst(FieldKey.GENRE).toLowerCase().contains(mystr))s=string;
            }
            catch(Exception e){}
        }
        return s;
    }
    /**
     * returns artist of the file
     * @return String
     */
    public String getArtist() {
        return artist;
    }
    /**
     * sets artist
     * @param artist 
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }
    /**
     * gets category (genre) of file
     * @return String
     */
    public String getCategory() {
        return category;
    }
    /**
     * sets category (genre)
     * @param category 
     */
    public void setCategory(String category) {
        this.category = new String(category);
    }
    /**
     * returns file lengh in minutes and seconds
     * @return String
     */
    public String getTime() {
        return time;
    }
    /**
     * sets time to given string value
     * @param time 
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * returns name and artist of file as one string
     * @return String
     */
    @Override
    public String toString() {
        return name+" - "+artist;
    }
    /**
     * returns title of the song
     * @return String
     */
    public String getName() {
        return name;
    }
    /**
     * sets song title to given string value
     * @param name 
     */
    public void setName(String name){
        this.name=name;
    }
    /**
     * returns file class object of the file
     * @return File 
     */
    public File getFile() {
        return file;
    }
}
