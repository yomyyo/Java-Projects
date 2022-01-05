import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Andrew Nguyen & Sam Tang
 * @since 02 Feb 2017
 *
 * CSCI 241, WWU - Professor Ahmed, Winter 2017
 */
public class Routes {

    public static void main(String[] args) {
        //Create graph first
        MyGraph graph = null;
        try {
            graph = createGraph();
        } catch (FileNotFoundException err) {
            System.out.println("One or more of the input files, \"vertex.txt\" and/or" +
                    "\"edge.txt\" was not found!\n" + err);
        }

        //Graph is created successfully? Begin input loop
        Scanner sc = new Scanner(System.in);
        String startVertex, endVertex; int routeSpecification;

        System.out.println("At any time, type \"quit\" to exit the program.");
        while (true) {
            String line = sc.nextLine();
            doQuitProg(line);   //Checks if the user typed "quit"
            String[] input = line.split(" ");
            if (!checkInput(input)) {  //Checks if the input array is of legitimate length and parameters
                continue;
            }

            startVertex = input[0]; endVertex = input[1];
            routeSpecification = Integer.parseInt(input[2]);

            int dtc;
            switch (routeSpecification) {   //use a switch: 1 = distance, 2 = time, 3 = cost, 4 = all
                case 1:
                    dtc = graph.findRoute(startVertex, endVertex, 1);
                    printDistance(startVertex, endVertex, dtc);
                    break;

                case 2:
                    dtc = graph.findRoute(startVertex, endVertex, 2);
                    printTime(startVertex, endVertex, dtc);
                    break;

                case 3:
                    dtc = graph.findRoute(startVertex, endVertex, 3);
                    printCost(startVertex, endVertex, dtc);
                    break;

                case 4:
                    dtc = graph.findRoute(startVertex, endVertex, 1);
                    printDistance(startVertex, endVertex, dtc);

                    dtc = graph.findRoute(startVertex, endVertex, 2);
                    printTime(startVertex, endVertex, dtc);

                    dtc = graph.findRoute(startVertex, endVertex, 3);
                    printCost(startVertex, endVertex, dtc);
                    break;
            }
            //Program simply continues after this and accepts new input;
            System.out.println();
            System.out.println("Ready for next input...");
            System.out.println("========================");
        }
    }

    /**
     * Creation of the graph object
     * @return Returns the graph
     * @throws FileNotFoundException Only if vertex.txt and edge.txt can't be found
     */
    public static MyGraph createGraph() throws FileNotFoundException {
        //*********************************//
        //CHANGE FILENAMES HERE FOR DIFFERENT TEST FILES

        String vertexFileName = "vertex.txt";
        String edgeFileName = "edge.txt";
        //*********************************//
        return new MyGraph(vertexFileName, edgeFileName);
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
     * Used to check the input of a String array. This is used to determine input
     * legitimacy when you type in "ATL JFK 1" for example
     * @param input A String array ["ATL", "JFK" "1"]
     */
    private static boolean checkInput(String[] input) {
        boolean flag = true;

        if (input.length != 3) {
            System.out.println("Error: Must have only 3 arguments!");
            flag = false;
        } else {
            String startVertex = input[0], endVertex = input[1];
            int routeSpecification = 0;

            try {
                routeSpecification = Integer.parseInt(input[2]);
            } catch (NumberFormatException err) {
                System.out.println("The final parameter is not an integer!");
            }

            if (routeSpecification > 4 || routeSpecification < 1) {
                System.out.println("Error: The interger parameter must be 1, 2, 3, or 4");
                flag = false;
            }
            if (startVertex.length() != 3 || endVertex.length() != 3) {
                System.out.println("Error: The string parameter(s) must be 3 letters in length");
                flag = false;
            }
        }
        return flag;
    }

    private static void printDistance(String start, String end, int x) {
        System.out.println("The shortest route from " + start + " to " + end
        + " is " + x + " miles.");
    }

    private static void printTime(String start, String end, int x) {
        System.out.println("The fastest route from " + start + " to " + end
                + " is " + x + " minutes.");
    }

    private static void printCost(String start, String end, int x) {
        System.out.println("The cheapest route from " + start + " to " + end
                + " is " + x + " dollars.");
    }
}
