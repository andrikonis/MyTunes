/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Music;

/**
 *
 * @author Andrius
 */
public class SaveManager {
    
    public static List<Music> getSaved(File save)throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(save));
        List<Music> list=new ArrayList((List<Music>)in.readObject());
        in.close();
        return list;
    }
    public static void saveList(List<Music> list,File save)throws IOException{
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(save));
        out.writeObject(list);
        out.close();
    }
}
