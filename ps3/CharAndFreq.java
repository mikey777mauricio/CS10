/**
 * Name: Mikey Mauricio
 * Date: 10/14/2021
 * Purpose: class to store frequency and character values
 * Override compareTo to compare frequency values
 * PS3
 */


public class CharAndFreq implements Comparable<CharAndFreq> {
    private Integer frequency;
    private Character character;

    public CharAndFreq(Character character, Integer frequency){
        this.character = character;
        this.frequency = frequency;
    }

    public Integer getFrequency(){
        return frequency;
    }

    public Character getCharacter(){
        return character;
    }

    public String toString(){
        return character + ", " + frequency;
    }

    public int compareTo(CharAndFreq o) {
        return frequency - o.frequency;
    }

}
