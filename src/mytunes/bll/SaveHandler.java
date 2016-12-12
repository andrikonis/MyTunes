/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mytunes.dal.SaveManager;

/**
 *
 * @author Andrius
 */
public class SaveHandler {
    /**
     * creates new array list from given list
     * creates save path from given string
     * sends those to SaveManager
     * @param list
     * @param saveName
     * @throws IOException 
     */
    public static void saveList(List list,String saveName) throws IOException{
        SaveManager.saveList(new ArrayList(list), new File("save/"+saveName));
    }
    /**
     * asks SaveManager to read save file with given name
     * and returns list of saved objects which needs to be typecasted to List<Music> of List<Playlist>
     * @param saveName
     * @return List
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static List getSaved(String saveName) throws IOException, ClassNotFoundException{
        return SaveManager.getSaved(new File("save/"+saveName));
    }
}
