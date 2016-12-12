/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.util.List;
import mytunes.be.Music;
import mytunes.bll.player.MyPlayer;

/**
 *
 * @author Andrius
 */
public class PlayerModel {
    protected static MyPlayer player=new MyPlayer();
    private static PlayerModel model;
    private PlayerModel(){}
    /**
     * returns PlayerModel, singleton pattern
     * @return PlayerModel
     */
    public static PlayerModel gelPlayerModel(){
        if(model==null)model=new PlayerModel();
        return model;
    }
    /**
     * sets list of music and index of currient song on player
     * @param list
     * @param index 
     */
    public void play(List<Music> list,int index) {
        if (check(list.get(index)) && player.getStatus() == 0) {
            player.pause();
        } else if (check(list.get(index)) && player.getStatus() == 1) {
            player.resume();
        } else {
            player.openPlaylist(list,index);
            player.play();
        }
    }
    /**
     * plays given playlist from start
     * @param list 
     */
    public void play(List<Music> list){
        player.openPlaylist(list, 0);
        player.play();
    }
    /**
     * checks if selected song is curriently playing
     *
     * @return
     */
    public boolean check(Music selected) {
        if(selected == player.getCurrient())return true;
        else return false;
    }
    /**
     * slider value from controller to player
     * @param vol 
     */
    public void setVolume(double vol){
        player.setVolume((float)vol);
    }
    /**
     * foward to next song
     */
    public void next(){
        player.handleNextPrevious(player.getCurrientIndex()+1);
    }
    /**
     * goes back to previous song
     */
    public void previous(){
        player.handleNextPrevious(player.getCurrientIndex()-1);
    }
    /**
     * passes the status from player to controller
     * @return int
     */
    public int getStatus(){
        return player.getStatus();
    }
}
