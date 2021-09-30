import java.io.File;
import java.util.Set;

/**
 * Created by Jesse on 1/29/2017.
 */
public class EvilHangmanGame implements IEvilHangmanGame {
    private Dictionary dict = new Dictionary();
    private Key currentKey = new Key();
    private GuessedLetters guessed = new GuessedLetters();
    @Override
    public void startGame(File dictionary, int wordLength){
        try{
            dict.loadDictionary(dictionary, wordLength);
            currentKey.resetKey(wordLength);
        }catch(Exception e){
            System.out.println(e);
            return;
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        //check for non-alpha in main
        if (!guessed.addLetter(guess)) {
            throw new GuessAlreadyMadeException();
        }
        String guessIn = Character.toString(guess);
        guessIn = guessIn.toLowerCase();
        guess = guessIn.charAt(0);
        currentKey = currentKey.combineKey(dict.partition(guess, currentKey));
        return dict.getAllWords();
    }
    public String getGuessedString(){return guessed.toString();}
    public Key getCurrentKey(){return currentKey;}
    public String gameOver(){return dict.getAllWords().first();}
}
