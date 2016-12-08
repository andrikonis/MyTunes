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
import mytunes.be.Music;
import mytunes.dal.SaveManager;

/**
 *
 * @author Andrius
 */
public class SaveHandler {
    public static void saveList(List<Music> list,String saveName) throws IOException{
        SaveManager.saveList(new ArrayList(list), new File("save/"+saveName));
    }
    public static List<Music> getSaved(String saveName) throws IOException, ClassNotFoundException{
        return SaveManager.getSaved(new File("save/"+saveName));
    }
}
