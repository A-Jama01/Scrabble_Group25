/** Save Class - This class saves the scrabble game using the date and time
 *
 * @author Monishkumar Sivakumar 101115115
 **/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

public class SaveMoves {

    ScrabbleController game;
    //private int num;
    File file;

    public SaveMoves(ScrabbleController _game){
        this.game = _game;
    }

    public void save(int num) {
        System.err.println("SaveMove " + num);

        String file_name = "states/scrabble_" + num + ".bin";
        try{
            new File("states").mkdir();
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }

        try{
            file = new File(file_name);
            Files.write(file.toPath(), Serialization.write_base64(game.getGame()).getBytes());
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }

        Serialization.write_base64(game.getGame());
        file.deleteOnExit();
    }
}