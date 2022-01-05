import java.io.*;
import java.util.*;
/**
 * Created by SammyTang on 3/8/17.
 * Modified by Andrew Nguyen 3/9/17
 */
public class NameList {
    private String name;
    private char gender;
    private int occurrences;
    private int totalOccurrences = 0;
    ArrayList<Node> peopleList;

    public NameList(String filename){
        peopleList = new ArrayList<>();
        try{
            loadArrayList(filename);
        }
        catch(FileNotFoundException err){
            System.out.println(err);
        }
//        System.out.println(peopleList);
    }

    //Prints out the entire list of names in alphabetical order, change the print statement to correct data as needed

    private void loadArrayList(String fileName)throws FileNotFoundException{
        int count = 0;
        Scanner scan = new Scanner(new File(fileName));
        while(scan.hasNext()){
            totalOccurrences++;
            String line = scan.nextLine();

            /*
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            name = lineScan.next();
            gender = lineScan.next().charAt(0);
            occurrences = lineScan.nextInt();
            */

            String[] data = line.split(",");
            if(data[1].charAt(0) == 'F') {
                Node person = new Node(data, totalOccurrences);
                peopleList.add(person);
            }
            else {
                count++;
                Node person = new Node(data, count);
                peopleList.add(person);
            }
        }
    }

    //Used to sort for ranking in mostPopularName
    private ArrayList<Node> sortRank(ArrayList<Node> aList){
        Node temp;
        for(int i = 1; i < aList.size(); i++){
            for(int j = i; j > 0; j--){
                if(aList.get(j).getRank() < aList.get(j - 1).getRank()){
                    temp = aList.get(j);
                    aList.set(j, aList.get(j-1));
                    aList.set(j-1, temp);
                }
            }
        }
        return aList;
    }

    //Sorts the entire ArrayList alphabetically

    private ArrayList<Node> sortAlphabetically(ArrayList<Node> aList){
        Collections.sort(aList, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        //Inefficient insertion sort with large data sets, use Collections.sort class instead as it likely uses a quick/merge sort instead
//        Node temp;
//        for(int i = 1; i < aList.size(); i++){
//            for(int j = i; j > 0; j--){
//                if(aList.get(j - 1).getName().compareTo(aList.get(j).getName()) > 0){
//                    temp = aList.get(j);
//                    aList.set(j, aList.get(j - 1));
//                    aList.set(j - 1, temp);
//                }
//            }
//        }
        return aList;
    }

    /*Returns an ArrayList of nodes containing the female and male versions of each name, will need to read
    the ArrayList in kickstarter and print out data such as gender and occurrences for each name.
    */

    public ArrayList<Node> searchName(String aName){
        ArrayList<Node> people = new ArrayList<>();
        for(Node node : peopleList){
            if(aName.equalsIgnoreCase(node.getName())){
                people.add(node);
            }
        }

        for (Node n : people) {
            printNodePlusPercent(n);
        }
        return people;
    }

    /*Returns an ArrayList containing first the top 10 girl names, and then the top 10 boy names in the correct order
    will need to read into the ArrayList in Kickstarter to display needed information
    */

    public ArrayList<Node> mostPopularName() {
        ArrayList<Node> people = new ArrayList<>();
        ArrayList<Node> girls = new ArrayList<>();
        ArrayList<Node> boys = new ArrayList<>();


        for(Node node : peopleList){
            if(node.getGender() == 'F'){
                if(node.getRank() <= 10){
                    girls.add(node);
                }
            }
        }

        for(Node node : peopleList){
            if(node.getGender() == 'M'){
                if(node.getRank() <= 10){
                    boys.add(node);
                }
            }
        }
        ArrayList<Node> sortedGirls = sortRank(girls);
        ArrayList<Node> sortedBoys = sortRank(boys);

        for(Node node : sortedBoys){
            printNodePlusPercent(node);
            people.add(node);
        }
        System.out.println();
        for(Node node : sortedGirls){
            printNodePlusPercent(node);
            people.add(node);
        }

        return people;
    }

    //Prints out the entire list of names in alphabetical order, change the print statement to correct data as needed
    public ArrayList<Node> showNameAlphabetically() {
        ArrayList<Node> sortedList = sortAlphabetically(peopleList);

        for(Node node : sortedList) {
            printNodePlusPercent(node);
        }

        return sortedList;
    }

    private void printNodePlusPercent(Node n) {
        System.out.println(String.format("%20s", n) + " " +
                String.format("%.6f", (double)n.getOccurences() / totalOccurrences * 100) + "%");
    }
}
