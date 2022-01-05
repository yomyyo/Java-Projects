import java.util.Arrays;

/**
 * @author ADKN
 * @since 09 Mar 2017
 */
public class Node {
    private String name;
    private char gender;
    private int occurrences;
    private String[] values;
    private int rank;

    /**
     * Node Object for HashMap and List
     * @param values
     * @param rank
     */
    public Node(String[] values, int rank){
        name = values[0];
        gender = values[1].charAt(0);
        occurrences = Integer.parseInt(values[2]);
        this.values = values;

        this.rank = rank;
    }

    public String toString() {
        return Arrays.toString(values);
    }

    public String getName() {
        return name;
    }

    public int getAlphabeticalValue(int index) {
        return Character.getNumericValue(name.charAt(index));
    }

    public String[] getValues() {
        return values;
    }

    public char getGender() {
        return gender;
    }

    public int getOccurences() {
        return occurrences;
    }

    public int getRank(){
        return rank;
    }
}
