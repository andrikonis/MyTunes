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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import mytunes.be.Music;

/**
 *
 * @author Andrius
 */
public class MyPlayer implements PlayerController, Runnable{
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
    private Music currient;
    private List<Music> playlist;
    
    private void reset(){
        currient=null;
        playlist=null;
        player=null;
        thread=null;
        status=UNKNOWN;
        
    }
    
    public List<Music> getPlaylist(){
        return playlist;
    }

    @Override
    public void openPlaylist(List<Music> list,Music song)throws FileNotFoundException,JavaLayerException{
        playlist=list;
        currient=song;
        status=OPENED;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public Music getCurrient() {
        return currient;
    }

    @Override
    public void seek(long bytes) {
    }

    @Override
    public void play() {
        if(thread!=null)thread.stop();
        thread=new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void stop() {
        if(thread!=null)thread.stop();
        reset();
    }

    @Override
    public void pause() {
        thread.suspend();
        status=PAUSED;
    }

    @Override
    public void resume() {
        thread.resume();
        status=PLAYING;
    }
    
    @Override
    public void run() {
        try{
            for(int i=playlist.indexOf(currient);i<playlist.size();i++){
                player=new Player(new FileInputStream(playlist.get(i).getFile()));
                status=PLAYING;
                player.play();
                status=STOPPED;
            }
        } 
        catch (JavaLayerException | FileNotFoundException ex) {
            Logger.getLogger(MyPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
