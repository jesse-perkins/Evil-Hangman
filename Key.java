/**
 * Created by Jesse on 1/29/2017.
 */
public class Key {
    private String current = "";
    Key() {}

    public void resetKey(int wordLength)
    {
        current = "";
        for(int i = 0; i< wordLength; i++)
        {
            current += '-';
        }
    }
    Key(String current){
        this.current = current;
    }
    public String toString(){
        return "Word: " + current;
    }
    public String getCurrent(){return current;}

    public Key combineKey(Key bestKey)
    {
        StringBuilder makeNewCurrentWord = new StringBuilder(this.current);
        for(int i = 0; i < this.current.length(); i++)
        {
            if(bestKey.getCurrent().charAt(i) != '-')
            {
                makeNewCurrentWord.setCharAt(i, bestKey.getCurrent().charAt(i));
            }
        }
        return new Key(makeNewCurrentWord.toString());
    }
}
