import java.io.File;
import java.util.Scanner;

/**
 * Created by Jesse on 1/29/2017.
 */
public class Main {
    public static void main(String[] args) {
        boolean printUsage = false;
        if (args == null || args.length < 3 || args.length > 3) {
            printUsage = true;
        } else {
            try {
                if (Integer.parseInt(args[1]) < 2) {
                    printUsage = true;
                }
                else if (Integer.parseInt(args[2]) < 1) {
                    printUsage = true;
                }else if (!(new File(args[0]).exists())){
                    printUsage = true;
                }
            } catch (Exception e) {
                System.out.println("\nUsage: java Main dictionary wordLength guesses\n\n" +
                        "\tdictionary is the path to a text file with whitespace separated words\n\t\t(no numbers, punctuation, etc.)\n" +
                        "\t\twordLength is an integer >= 2.\n" +
                        "\t\tguesses is an integer >= 1.\n");
                return;
            }
        }
        if(printUsage){
            System.out.println("\nUsage: java Main dictionary wordLength guesses\n\n" +
                    "\tdictionary is the path to a text file with whitespace separated words\n\t\t(no numbers, punctuation, etc.)\n" +
                    "\twordLength is an integer >= 2.\n" +
                    "\tguesses is an integer >= 1.\n");
            return;
        }
        try {
            Scanner inputs = new Scanner(System.in);
            File inFile = new File(args[0]);
            int numGuesses = Integer.parseInt(args[2]);
            EvilHangmanGame gameOn = new EvilHangmanGame();
            gameOn.startGame(inFile, Integer.parseInt(args[1]));
            while (numGuesses > 0) {
                System.out.println("\nYou have " + numGuesses + " guesses left\n" +
                        gameOn.getGuessedString() + '\n' +
                        gameOn.getCurrentKey().toString() + '\n' +
                        "Enter guess: ");
                String in = inputs.next().toLowerCase();
                Character checker  = in.charAt(0);
                if ((in.length() > 1) || (!Character.isAlphabetic(checker))) {
                    System.out.println("\nInvalid Input\n");
                } else {
                    try {
                        String tester = gameOn.getCurrentKey().getCurrent();
                        gameOn.makeGuess(checker);
                        if (gameOn.getCurrentKey().getCurrent().equals(tester)) {
                            System.out.println("\nSorry, there are no " + checker + "\'s");
                            numGuesses--;
                        } else {
                            int numLetters = 0;
                            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                                if (gameOn.getCurrentKey().getCurrent().charAt(i) == checker) {
                                    numLetters++;
                                }
                            }
                            System.out.println("Yes, there is " + numLetters + ' ' + checker);
                        }
                    } catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
                        System.out.println("\nYou already used that letter.\n");
                    } catch (Exception e) {
                        System.out.println(e);
                        return;
                    }
                }
                String winConditionCheck = gameOn.getCurrentKey().getCurrent();
                int numHyphens = 0;
                for(int i = 0; i < Integer.parseInt(args[1]); i++){
                    if(winConditionCheck.charAt(i) == '-'){
                        numHyphens++;
                    }
                }
                if(numHyphens ==0){
                    System.out.println("\nYou Win! The word was " + winConditionCheck + ".\n");
                    return;
                }
            }
            System.out.println("\nYou lose!\n" +
                    "The word was: " + gameOn.gameOver());
            return;
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}
