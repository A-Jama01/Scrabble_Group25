
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Load implements ActionListener {

    ScrabbleController game;

    public Load(ScrabbleController _game){
        game = _game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Load");
        game.loadGame();
    }

}