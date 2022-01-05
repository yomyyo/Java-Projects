import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ADKN
 * @since 25 Feb 2017
 */
public class NameData {
    private static BSTree tree;
    private static Hash hash;
    private static NameList list;

    /**
     * constructs a NameData object. Created for simplicity's sake of having all three data structures
     * @param inputFile
     * @throws FileNotFoundException
     */
    public NameData(String inputFile) throws FileNotFoundException{
//        testHash(inputFile);
//        testList(inputFile);
//        testTree(inputFile);
        System.out.println("Please wait, building data structures...");

        createTree(inputFile);
        createHashMap(inputFile);
        createArrayList(inputFile);

        System.out.println("Build complete.");
        System.out.println();


    }

    public static void createTree(String inputFile) throws FileNotFoundException {
        long startTime = System.nanoTime();
        tree = new BSTree(inputFile);
        long endTime = System.nanoTime();
        System.out.println("...BSTree build completed in "+ (endTime - startTime) / 1000000 + "ms");
    }

    public static void createHashMap(String inputFile) {
        long startTime = System.nanoTime();
        hash = new Hash(inputFile);
        long endTime = System.nanoTime();
        System.out.println("...HashMap build completed in "+ (endTime - startTime) / 1000000 + "ms");
    }

    public static void createArrayList(String inputFile) {
        long startTime = System.nanoTime();
        list = new NameList(inputFile);  //Make sure this uses arraylist and not an array!
        long endTime = System.nanoTime();
        System.out.println("...ArrayList build completed in "+ (endTime - startTime) / 1000000 + "ms");
    }

    public BSTree getTree() {
        return tree;
    }

    public Hash getHash() {
        return hash;
    }

    public NameList getList() {
        return list;
    }

    /*public void testTree(String inputFile) throws FileNotFoundException{
        createTree(inputFile);
        System.out.println(tree.getNumberOfNames());
        tree.searchName("Rayan"); //returns Node[]
        tree.mostPopularName();         //returns Node[]
        tree.showNameAlphabetically();  //Void function
        System.out.println(tree.getTotalOccurrences());
    }

    public void testHash(String inputFile) {
        createHashMap(inputFile);
        hash.searchName("Rayan");
        hash.mostPopularName();
        hash.showNameAlphabetically();
    }

    public void testList(String inputFile) {
        createArrayList(inputFile);
        list.searchName("Rayan"); //return arraylist
        list.mostPopularName();   //return arraylist
        list.showNameAlphabetically();
    }*/
}
