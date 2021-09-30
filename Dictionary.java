import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by Jesse on 1/29/2017.
 */
public class Dictionary {
    private TreeSet<String> allWords = new TreeSet<>();

    public void loadDictionary(File inFile, int wordLength) throws Exception {
        String holder;
        Scanner readInFile = new Scanner(inFile);
        while (readInFile.hasNext()) {
            holder = readInFile.next();
            if (holder.matches("[a-z A-Z]+") && (holder.length() == wordLength)) {
                holder = holder.toLowerCase();
                allWords.add(holder);
            }
        }
    }
    public Key partition(Character guessedLetter, Key currentWord)
    {
        HashMap<String, TreeSet<String>> partitioner = new HashMap<>();
        StringBuilder holder = new StringBuilder();
        int bestSize = 0;
        Key bestKey = new Key();
        TreeSet<String> bestSet = new TreeSet<>();
        // Iterates through the dictionary and partitions it based the positions of the quessed
        // letter in a word. EX: guess = 'e'  help => -e--
        for (String key: allWords)
        {
            for(int i = 0; i <currentWord.getCurrent().length(); i++ )
            {
                if(key.charAt(i) == guessedLetter)
                {
                    holder.append(guessedLetter);
                }
                else
                {
                    holder.append('-');
                }
            }
            TreeSet<String> tempSet = partitioner.get(holder.toString());
            if(tempSet == null)
            {
                tempSet = new TreeSet<>();
                partitioner.put(holder.toString(), tempSet);
            }
            tempSet.add(key);
            holder.setLength(0);
        }
        // Figure out which partition is the best partition
        for (HashMap.Entry<String, TreeSet<String>> current : partitioner.entrySet())
        {
            // Winner is the largest partition
            if(bestSize < current.getValue().size())
            {
                bestSet = current.getValue();
                bestKey = new Key(current.getKey());
                bestSize = current.getValue().size();
            }
            //Break ties based on the number of hyphens in a key.
            else if (bestSize == current.getValue().size())
            {
                int numHyphensBest = 0;
                int numHyphensChallenge = 0;
                for(int i = 0; i < currentWord.getCurrent().length(); i++)
                {
                    if(current.getKey().charAt(i) == '-')
                    {
                        numHyphensChallenge++;
                    }
                    if(bestKey.getCurrent().charAt(i) == '-')
                    {
                        numHyphensBest++;
                    }
                }
                // More hyphens wins!
                if(numHyphensBest < numHyphensChallenge)
                {
                    bestSet = current.getValue();
                    bestKey = new Key(current.getKey());
                    bestSize = current.getValue().size();
                }
                // Second level tiebreaker , the key with the the most guessed letter positions
                // furthest to the right.
                else if(numHyphensChallenge == numHyphensBest)
                {
                    boolean keepRunning = true;
                    int i = currentWord.getCurrent().length()-1;
                    while(keepRunning)
                    {
                        if(bestKey.getCurrent().charAt(i) == current.getKey().charAt(i)) {}
                        else if(current.getKey().charAt(i) == guessedLetter)
                        {
                            bestKey = new Key(current.getKey());
                            bestSet = current.getValue();
                            bestSize = current.getValue().size();
                            keepRunning = false;
                        }
                        else
                        {
                            keepRunning = false;
                        }
                        i--;
                    }
                }

            }
        }
        allWords = bestSet;
        return bestKey;
    }

    public TreeSet<String> getAllWords(){ return allWords;}
}
