/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import mytunes.be.Music;

/**
 *
 * @author Andrius
 */
public class SearchManager {
    /**
     * filters given list of music acording to given search query
     * no case sensitivity, compares music.toString()
     * @param list
     * @param query
     * @return List<Music>
     */
    public static List<Music> search(List<Music> list,String query){
        List<Music> filteredList=new ArrayList();
        for (Music music : list) {
            if(music.toString().toLowerCase().contains(query.toLowerCase()))filteredList.add(music);
        }
        return filteredList;
    }
}
