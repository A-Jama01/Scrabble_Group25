//Monishkumar Sivakumar - 101115115
//Test class to check whether the Word Class works
public class Test {
    public static void main(String[] args) {
        String word = "BYEBYE";
        Word words = new Word();
        System.out.println("'" + word + "' is worth " + words.score(word) + " points");
    }
}
