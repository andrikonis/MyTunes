/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import mytunes.be.Music;
import mytunes.gui.model.MainModel;

/**
 *
 * @author Andrius
 */
public class MyPlayer implements Runnable{
    /**
     * These variables are used to distinguish stopped, paused, playing states.
     * We need them to control Thread.
     */
    public static final int UNKNOWN = -1;
    public static final int PLAYING = 0;
    public static final int PAUSED = 1;
    public static final int STOPPED = 2;
    public static final int OPENED = 3;
    public static final int SEEKING = 4;
    private int status = UNKNOWN;

    private Player player;
    private Thread thread;
    private int currientIndex;
    private List<Music> playlist;
    /**
     * resets the player to its initial state
     */
    private void reset(){
        currientIndex=0;
        playlist=null;
        player=null;
        thread=null;
        status=UNKNOWN;
    }
    /**
     * gets curriently opened list of music
     * @return List<Music>
     */
    public List<Music> getPlaylist(){
        return playlist;
    }
    /**
     * sets given list of songs as playlist
     * and chooses given index as starting point for the play queue
     * of the playlist
     * @param list
     * @param index 
     */
    public void openPlaylist(List<Music> list,int index){
        playlist=list;
        currientIndex=index;
        status=OPENED;
    }
    /**
     * gets the status of playlist
     * @return int
     */
    public int getStatus() {
        return status;
    }
    /**
     * return curriently playing (paused) song or null
     * if nothing is playing
     * @return Music
     */
    public Music getCurrient() {
        if(playlist==null)return null;
        else return playlist.get(currientIndex);
    }
    /**
     * starts the playback of song
     */
    public void play() {
        if(thread!=null)thread.stop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        thread=new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * stops (resets) player
     */
    public void stop() {
        if(thread!=null)thread.stop();
        reset();
    }
    /**
     * pauses playing song
     */
    public void pause() {
        thread.suspend();
        status=PAUSED;
    }
    /**
     * resumes to paused song
     */
    public void resume() {
        thread.resume();
        status=PLAYING;
    }
    /**
     * main loop responsible for playing and switching
     * between songs
     */
    @Override
    public void run() {
        synchronized(playlist){
            try{
                for(int i=currientIndex;i<playlist.size();i++){
                    player=new Player(new FileInputStream(playlist.get(i).getFile()));
                    status=PLAYING;
                    currientIndex=i;
                    synchronized(MainModel.getModel().getCurrientProperty()){
                        String currient=playlist.get(i).toString();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                MainModel.getModel().getCurrientProperty().setValue(currient);
                                MainModel.getModel().setButton();
                            }
                        });
                    }
                    player.play();
                    status=STOPPED;
                }
            } 
            catch (JavaLayerException | FileNotFoundException ex) {
                Logger.getLogger(MyPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * sets volume
     * @param vol 
     */
    public void setVolume(float vol){
        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();
                    if (port.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        volume.setValue(vol / 100);
                    }
                    port.close();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * gets index of curriently playing song
     * @return int
     */
    public int getCurrientIndex() {
        return currientIndex;
    }
    /**
     * starts playing song of given index
     * @param i 
     */
    public void handleNextPrevious(int i){
        if(i>=0&&i<playlist.size()){
            currientIndex=i;
            play();
        }
    }
}
