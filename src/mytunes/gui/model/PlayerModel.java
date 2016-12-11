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
    public static PlayerModel gelPlayerModel(){
        if(model==null)model=new PlayerModel();
        return model;
    }
    
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
    public void setVolume(double vol){
        player.setVolume((float)vol);
    }
    public void next(){
        player.handleNextPrevious(player.getCurrientIndex()+1);
    }
    public void previous(){
        player.handleNextPrevious(player.getCurrientIndex()-1);
    }
    public int getStatus(){
        return player.getStatus();
    }
}
