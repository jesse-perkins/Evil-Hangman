import java.io.IOException;
import java.util.TreeSet;

/**
 * Created by Jesse on 1/29/2017.
 */
public class GuessedLetters {
    private TreeSet<Character> guessedLetters= new TreeSet<>();

    //Might only need 'return guessedLetters.add(A); not 100% on that
    // This returns false if the character is already in the TreeSet. Error checking to make sure only alpha chars
    // get in will need to be implemented the next step up. Converts inputs to lowercase.
    public boolean addLetter(Character alpha)
    {
        alpha = Character.toLowerCase(alpha);
        for (Character letter: guessedLetters) {
            if(letter.equals(alpha))
            {
                return false;
            }
        }
        guessedLetters.add(alpha);
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder usedLetters = new StringBuilder("Used letters:");
        for (Character letter: guessedLetters) {
            usedLetters.append(" " + letter);
        }
        return usedLetters.toString();
    }
}
