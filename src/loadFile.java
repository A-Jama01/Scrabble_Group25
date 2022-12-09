/** LoadFile Class - This class access the directory to locate files
 *
 * @author Monishkumar Sivakumar 101115115
 **/
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class loadFile {
    public static loadFile self;

    public static boolean debug_mode = false;
    public static String result_file = ".";
    public static loadFile getInstance(){
        if(self == null){
            self = new loadFile();
        }
        return self;
    }


    public String show_file_dialog(Component parentComponent, String root){
        if(!debug_mode){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int option = fileChooser.showOpenDialog(parentComponent);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                return file.getAbsolutePath();
            }else{
                return null;
            }
        }else{
            return result_file;
        }
    }

}