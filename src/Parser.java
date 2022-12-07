import java.util.Scanner;
import java.io.Serializable;
/**
 * The Parser class to get inputs
 *
 * @author Christina Dang 101141609
 */

public class Parser implements Serializable{
    private String line;

    /**
     * A method to get the input of the user
     *
     * @return line string, the input
     */
    public String getInput(){
        Scanner scan = new Scanner(System.in);
        line = scan.nextLine();
        return line;
    }
}
