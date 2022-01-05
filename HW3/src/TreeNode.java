
import java.util.Arrays;

/**
 * @author ADKN
 * @since 02 Mar 2017
 */
public class TreeNode {
    private TreeNode leftChild;
    private TreeNode rightChild;
    private String name;
    private char gender;
    private int occurrences;
    private String[] values;

        /*
        In order to sort alphabetically, we will consider the numbers 10 - 35.
        These numbers correspond to Character.getNumericValue(char c), which maps A to 10,
        B to 11... Etc. Case does not matter. A lowercase a and an uppercase A have the same
        numeric value. We can use this value (which is the ASCII value by the way) to sort the tree.
         */

    /**
     * TreeNode object SPECIFICALLY FOR THE BINARY TREE
     * @param readLine
     * @param left
     * @param right
     */
    public TreeNode(String[] readLine, TreeNode left, TreeNode right) {
        leftChild = left;
        rightChild = right;
        name = readLine[0];
        gender = readLine[1].charAt(0);
        occurrences = Integer.parseInt(readLine[2]);

        values = readLine;
    }

    public String toString() {
        return Arrays.toString(values);
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
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

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public void setName(String name) {
        values[0] = name;
        this.name = name;
    }

    public void setGender(char c) {
        //must be M or F!!!
        values[1] = String.valueOf(c);
        gender = c;
    }

    public void setOccurrences(int num) {
        values[2] = String.valueOf(num);
        occurrences = num;
    }
}
