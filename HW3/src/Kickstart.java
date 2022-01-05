import java.io.FileNotFoundException;
import java.util.Scanner;

public class Kickstart {

    /**
     * main function
     * @param args in the form [structure type] [function] [name if applicable]
     */
    public static void main(String[] args) {

        NameData dataStructures = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a year in the form XXXX, from 1880 - 2015.");
        System.out.println("Note that the source data is derived from the US Social Security Administration.");
        System.out.println("================================================================================");
        String year = sc.nextLine();

        try {
            year = "yob" + year + ".txt";
            dataStructures = new NameData(year);
        } catch (FileNotFoundException err) {
            System.out.println("Input file is invalid\n Please check that the year is within the range 1880 - 2015." + err);
        }


        sc = new Scanner(System.in);
        System.out.println("At any time, type \"quit\" to exit the program.");
        System.out.println("Enter input in the form: [structure type] [function] [name if applicable]");
        System.out.println();
        System.out.println("Binary Tree is type 1, HashMap is type 2, ArrayList is type 3.");
        System.out.println("searchName is function 1, mostPopularNames is function 2, showAlphabetical is function 3.");
        System.out.println("e.g., \"1 1 John\" will search the binary tree for the name \"John\".");
        System.out.println("================================================================================");

        while (true) {
            String line = sc.nextLine();
            doQuitProg(line);
            String[] input = line.split(" ");

            int structure;
            int function;
            String name;

            if (checkInput(input)) {
                structure = Integer.parseInt(input[0]);
                function = Integer.parseInt(input[1]);
                if (function == 1) {
                    name = input[2];
                } else {
                    name = null;
                }
            } else {
                System.out.println("Input file is invalid or does not exist.");
                System.exit(0);
                break;
            }

            switch (structure) {
                case 1:
                    calculateTree(dataStructures, function, name);
                    break;
                case 2:
                    calculateHash(dataStructures, function, name);
                    break;
                case 3:
                    calculateList(dataStructures, function, name);
                    break;
            }
            //Program simply continues after this and accepts new input;
            System.out.println();
            System.out.println("Ready for next input...");
            System.out.println("========================");

        }

//        dataStructures.getTree().searchName("Michael");
//        System.out.println(dataStructures.getHash().mostPopularName().toString());
//        System.out.println(dataStructures.getList().mostPopularName().toString());


        //user input from terminal
        //parse.... list.mostPopular()
        //parse... tree.searchName()
        ///...etc.



    }

    /**
     * Quits the program if the inputted String = "quit"
     * @param s A String
     */
    public static void doQuitProg(String s) {
        if (s.equalsIgnoreCase("quit")) {
            System.out.println("Program exiting...");
            System.exit(0);
        }
    }

    /**
     * check input to see if it is valid
     * @param input
     * @return
     */
    private static boolean checkInput(String[] input) {
        if (!(input.length < 4 && input.length > 0)) {
            System.out.println("Error: Must have 2 or 3 arguments!");
            return false;
        } else {
            int whichStructure = Integer.parseInt(input[0]);
            int whichFunction = Integer.parseInt(input[1]);

            if (whichStructure < 1 || whichStructure > 3) {
                return false;
            }

            if (whichFunction < 1 || whichFunction > 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * Any function dealing with the tree
     * @param data
     * @param functionSelection
     * @param name
     */
    private static void calculateTree(NameData data, int functionSelection, String name) {
        long startTime = System.nanoTime();
        long endTime;
        switch (functionSelection) {
            case 1:
                data.getTree().searchName(name);
                endTime = System.nanoTime();
                System.out.println("...BSTree searchName completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 2:
                data.getTree().mostPopularName();
                endTime = System.nanoTime();
                System.out.println("...BSTree mostPopular completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 3:
                data.getTree().showNameAlphabetically();
                endTime = System.nanoTime();
                System.out.println("...BSTree showAlphabetical completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
        }
    }

    /**
     * any function dealing with the hashmap
     * @param data
     * @param functionSelection
     * @param name
     */
    private static void calculateHash(NameData data, int functionSelection, String name) {
        long startTime = System.nanoTime();
        long endTime;
        switch (functionSelection) {
            case 1:
                data.getHash().searchName(name);
                endTime = System.nanoTime();
                System.out.println("...HashMap searchName completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 2:
                data.getHash().mostPopularName();
                endTime = System.nanoTime();
                System.out.println("...HashMap mostPopular completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 3:
                data.getHash().showNameAlphabetically();
                endTime = System.nanoTime();
                System.out.println("...HashMap showAlphabetical completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
        }
    }

    /**
     * any function dealing with the list
     * @param data
     * @param functionSelection
     * @param name
     */
    private static void calculateList(NameData data, int functionSelection, String name) {
        long startTime = System.nanoTime();
        long endTime;
        switch (functionSelection) {
            case 1:
                data.getList().searchName(name);
                endTime = System.nanoTime();
                System.out.println("...ArrayList searchName completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 2:
                data.getList().mostPopularName();
                endTime = System.nanoTime();
                System.out.println("...ArrayList mostPopular completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
            case 3:
                data.getList().showNameAlphabetically();
                endTime = System.nanoTime();
                System.out.println("...ArrayList showAlphabetical completed in "+ (endTime - startTime) / 1000000 + "ms");
                break;
        }
    }

}
