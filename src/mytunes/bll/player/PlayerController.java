
package mytunes.bll.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javazoom.jl.decoder.JavaLayerException;
import mytunes.be.Music;

/**
 * This interface defines player controls available.  
 */
public interface PlayerController
{
    /**
     * starts playing list of songs
     * @param list
     * @throws PlayerException 
     */
    public void openPlaylist(List<Music> list,Music song)throws FileNotFoundException,JavaLayerException;
    /**
     * get currient status of player
     * -1 = UNKNOWN(nothing was opened)
     * 0 = PLAYING
     * 1 = PAUSE
     * 2 = STOPED (end of song)
     * @return 
     */
    public int getStatus();
    /**
     * returns curriently playing song
     * @return 
     */
    public Music getCurrient();
    /**
     * Skip bytes.
     * @param bytes
     * @return bytes skipped according to audio frames constraint.
     * @throws PlayerException
     */
    public void seek(long bytes);

    /**
     * Start playback.
     * @throws PlayerException
     */
    public void play();

    /**
     * Stop playback. 
     * @throws PlayerException
     */
    public void stop();

    /**
     * Pause playback. 
     * @throws PlayerException
     */
    public void pause();

    /**
     * Resume playback. 
     * @throws PlayerException
     */
    public void resume();
}
