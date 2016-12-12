/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deividukxxsssss
 */
public class Playlist implements Serializable {
    
    private String name;
    private List<Music> playlist;
    /**
     * creates new Playlist entity with given name string
     * @param name 
     */
    public Playlist(String name){
        playlist = new ArrayList();
        this.name=name;
    }
    /**
     * returns name of the playlist
     * @return String
     */
    public String getName() {
        return name;
    }
    /**
     * returns time as h:mm:ss
     * @return String 
     */
    public String getTime() {
        return getPlaylistTime();
    }
    /**
     * tries to put all times to integers
     * and calculate total time of the playlist
     * @return String
     */
    private String getPlaylistTime() {
        int h=0;
        int min=0;
        int sec=0;
        for (Music music : playlist) {
            String[] time;
            if(music.getTime().equals("unknown"));
            else{
                try{
                    time=music.getTime().trim().split(":");
                    min+=Integer.parseInt(time[0]);
                    sec+=Integer.parseInt(time[1]);
                }catch(Exception ex){}
            }
        }
        min += sec/60;
        sec = sec%60;
        h = min/60;
        min = min%60;
        String time = "";
        time += h + ":";
        if (min<10)time+="0"+min+":";
        else time+=min+":";
        
        if (sec<10) time += "0"+sec;
        else time += sec;
        return time;      
    }
    /**
     * gets the playlist
     * @return List<Music>
     */
    public List<Music> getPlaylist() {
        return playlist;
    }
    /**
     * adds list of given music elements to the playlist
     * @param list 
     */
    public void addMusic(List<Music> list){
        for (Music music : list) {
            playlist.add(new Music(music));
        }    
    }
    /**
     * gets the number of songs in the playlist
     * @return 
     */
    public int getPlaylistSize(){
        return playlist.size();
    }
    /**
     * sets the name of playlist to given string value
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * moves music entity of given index up in the queue
     * @param index 
     */
    public void moveUp(int index){
        if(index>0){
            Music temp=playlist.get(index-1);
            playlist.set(index-1,playlist.get(index));
            playlist.set(index, temp);
        }
    }
    /**
     * moves music entity of given index down in the queue
     * @param index 
     */
    public void moveDown(int index){
        if(index<playlist.size()-1){
            Music temp=playlist.get(index+1);
            playlist.set(index+1,playlist.get(index));
            playlist.set(index, temp);
        }
    }
}
    
