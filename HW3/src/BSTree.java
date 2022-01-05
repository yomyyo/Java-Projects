import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ADKN
 * @since 25 Feb 2017
 */
public class BSTree {
    private TreeNode root;
    private int totalOccurrences;
    private int numberOfNames;

    /*
    The tree can be constructed in several ways.
        -It can be made alphabetically, with A's on the left subtree and Z's on the right.
         If I choose this route, each of the letters will have to be assigned a value, 1 - 26
         Perhaps this would implement a hashmap to use as a dictionary (in the form of python's
         dictionary structures)

        -The second route is to simply store the names by number. This is the easiest.
         Names with the most occurrences will be on the left subtree and names with the least
         will be on the right subtree. This makes showing names alphabetically a huge pain
         though, since the entire tree would have to be sorted alphabetically and then
         printed before running showNameAlphabetically().

         -Another option is to have two trees, maleRoot and femaleRoot where each tree is
          organized by one of the above two methods, except that genders are divided into
          two trees. This makes the mostPopularNames() very easy, but complicates the
          other two operations of searching and alphabetically sorting.

     Best option so far looks to be sorting alphabetically. Numerical popularity can be
     determined later by sorting all the names into a 10 space array, and then removing
     or modifying the elements as we encounter more while traversing the tree.

     Searching a name is simple this way. Simply traverse the tree and determine if the name
     matches. Alphabetical printing is also easy this way.
     */

    /*
        String str = "Abigail";

        char[] array = new char[26];
        array[0] = 'A'; array[1] = 'B'; array[2] = 'C'; array[3] = 'D';
        array[4] = 'E'; array[5] = 'F'; array[6] = 'G'; array[7] = 'H';
        array[8] = 'I'; array[9] = 'J'; array[10] = 'K'; array[11] = 'L';

        array[12] = 'M'; array[13] = 'N'; array[14] = 'O'; array[15] = 'P';
        array[16] = 'Q'; array[17] = 'R'; array[18] = 'S'; array[19] = 'T';
        array[20] = 'U'; array[21] = 'V'; array[22] = 'W'; array[23] = 'X';

        array[24] = 'Y'; array[25] = 'Z';

        char[] array1 = new char[26];
        array1[0] = 'a'; array1[1] = 'b'; array1[2] = 'c'; array1[3] = 'd';
        array1[4] = 'e'; array1[5] = 'f'; array1[6] = 'g'; array1[7] = 'h';
        array1[8] = 'i'; array1[9] = 'j'; array1[10] = 'k'; array1[11] = 'l';

        array1[12] = 'm'; array1[13] = 'n'; array1[14] = 'o'; array1[15] = 'p';
        array1[16] = 'q'; array1[17] = 'r'; array1[18] = 's'; array1[19] = 't';
        array1[20] = 'u'; array1[21] = 'v'; array1[22] = 'w'; array1[23] = 'x';

        array1[24] = 'y'; array1[25] = 'z';

        for (char c : array1) {
            System.out.println(Character.getNumericValue(c));
        }
        */


    /**
     * Constructs the BSTree
     * @param inputFile yobXXXX.txt
     * @throws FileNotFoundException
     */
    public BSTree(String inputFile) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(inputFile));
        String str = "";
        while (sc.hasNextLine()) {
            str += sc.nextLine() + "\n";
        }
        String[] arr = str.split("\n");
        Arrays.sort(arr);

        makeTree(arr);
//        System.out.println(Arrays.binarySearch(arr, "Rayan,F,53"));
//        System.out.println(arr[25979]);
//        System.out.println(arr[25978]);
//        System.out.println(Arrays.toString(arr));
    }

    /**
     * Kicker recursive function to buildTree from an array of lines (parsed in from the input file)
     * @param lines array of lines (parsed in from the input file)
     */
    public void makeTree(String[] lines) {
        root = buildTree(lines, 0, lines.length - 1);
        totalOccurrences = totalOccurrences(root);
        numberOfNames = getNumberOfNames();
    }

    /**
     * Recursively builds the tree from an alphabetically sorted array, which is calculated from the input file
     * @param data sorted array calculated from input file
     * @param start starting index
     * @param end ending index
     * @return returns the root TreeNode
     */
    private TreeNode buildTree(String[] data, int start, int end) {

        //Base case
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode treeNode = new TreeNode(data[mid].split(","), null, null);

        treeNode.setLeftChild(buildTree(data, start, mid - 1));
        treeNode.setRightChild(buildTree(data, mid + 1, end));

        return treeNode;
    }

    //METHOD DEPRECATED

    /**
     * Used to add a Node to the tree. Recursive kicker function DEPRECATED
     * @param values String[] of values for the node [Name, gender, occurrences]
     */
    private void addNode(String[] values) {
        if (root == null) {
            root = new TreeNode(values, null, null);
        } else {
            addNode(root, values, 0);
        }
    }

    //METHOD DEPRECATED

    /**
     * Recursive function called by addNode(String[] values)
     * @param parent parent node
     * @param values values for the new node
     * @param charIndex Used to determine alphabetical order. eg: Andy and Andrew have the same 3 first letters,
     *                  so it would iterate to the fourth letter before determining which one takes priority
     */
    private void addNode(TreeNode parent, String[] values, int charIndex) {
        String childName = values[0];
        String parentName = parent.getName();

        int childLength = childName.length();
        int parentLength = parentName.length();

        int childValue = Character.getNumericValue(values[0].charAt(charIndex));
        int parentValue = parent.getAlphabeticalValue(charIndex);

        if (childValue < parentValue) {

            if (parent.getLeftChild() == null) {
                parent.setLeftChild(new TreeNode(values, null, null));
            } else {
                addNode(parent.getLeftChild(), values, 0);
            }

        } else if (childValue > parentValue) {

            if (parent.getRightChild() == null) {
                parent.setRightChild(new TreeNode(values, null, null));
            }
            else {
                addNode(parent.getRightChild(), values, 0);
            }
        } else if (childValue == parentValue) {
            if (charIndex + 1 < childLength && charIndex + 1 < parentLength) {
                addNode(parent, values, charIndex + 1);
            } else { //ran out of index
                //incomplete
            }
        }
    }

    /**
     * Recursive kicker function for searchName(String name, Treenode treeNode)
     * @param name Name to search
     * @return returns an array of two nodes if there is a male + female of that name, one node if not
     */
    public TreeNode[] searchName(String name) {
        //need to find out if there are two genders, end up searching tree anyway
        //First node encountered will be male or female, if there is a duplicate
        //Name of the opposite gender, it will be a child node of that parent node
        TreeNode[] treeNodes = new TreeNode[2];

        treeNodes[0] = searchName(name, root);
        treeNodes[1] = searchName(name, treeNodes[0].getLeftChild());   //any duplicate name must be a child of the first
                                                                        //instance of the name in the tree. IF SORTED ALPHABETICALLY

        if (treeNodes[1] == null) {
            treeNodes[1] = searchName(name, treeNodes[0].getRightChild());
        }

        for (TreeNode n: treeNodes) {
            printNodePlusPercent(n);
        }
        return treeNodes;
    }

    /**
     * recursive search function
     * @param name name to search
     * @param treeNode root node
     * @return Returns the first instance of the name found
     */
    private TreeNode searchName(String name, TreeNode treeNode) {
        if (treeNode == null) return null;
        if (treeNode.getName().equalsIgnoreCase(name)) {
            return treeNode;
        } else {
            if (isAlphabetical(name, treeNode.getName())) {
                return searchName(name, treeNode.getLeftChild());
            } else {
                return searchName(name, treeNode.getRightChild());
            }
        }
    }

    /**
     * Determines which name, given two, take alphabetical priority
     * @param name1
     * @param name2
     * @return Returns the higher priority name
     */
    private boolean isAlphabetical(String name1, String name2) {
        String[] arr = {name1, name2};
        Arrays.sort(arr);
        return (arr[0]).equals(name1);
    }

    /**
     * prints the 10 most popular male and female names by occurrences
     * @return has capability to return the resulting array of 20 names.
     */
    public TreeNode[] mostPopularName() {
        //collect 10 male names
        TreeNode[] male = new TreeNode[10];
//        male[0] = extractMax(root, 0, 'F');
        TreeNode mTreeNode;
        ArrayList<TreeNode> mIgnoreValues = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mTreeNode = extractMax(root, mIgnoreValues, 'F');
            male[i] = mTreeNode;
            mIgnoreValues.add(mTreeNode);
        }

        //collect 10 female names
        TreeNode[] female = new TreeNode[10];
//        female[0] = extractMax(root, 0, 'M');
        TreeNode fTreeNode;
        ArrayList<TreeNode> fIgnoreValues = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            fTreeNode = extractMax(root, fIgnoreValues, 'M');
            female[i] = fTreeNode;
            fIgnoreValues.add(fTreeNode);
        }

        for (TreeNode n : male) {
            printNodePlusPercent(n);

        }
        System.out.println();
        for (TreeNode n : female) {
            printNodePlusPercent(n);
        }

        TreeNode[] result = new TreeNode[20];
        for (int i = 0; i < 10; i++) {
            result[i] = male[i];
            result[i+10] = female[i];
        }

        return result;
    }

    /**
     * Extracts the node with the maximum occurrences from the tree, ignoring any name in the ignore value list
     * @param treeNode Root/parent
     * @param ignoreVal ignores nodes that have been found already
     * @param ignoreGender ignores this gender (to search for just male and just female names)
     * @return returns the node with the largest num of occurrences not in the ignore val list
     */
    public static TreeNode extractMax(TreeNode treeNode, ArrayList<TreeNode> ignoreVal, char ignoreGender) {
        int max = treeNode.getOccurences();
        TreeNode maxTreeNode = treeNode;

        // only check if this treeNode is not a leaf
        if (treeNode.getRightChild() != null) {
            TreeNode maxRight = extractMax(treeNode.getRightChild(), ignoreVal, ignoreGender);
            if (maxRight.getOccurences() > max &&
                    !ignoreVal.contains(maxRight) &&
                    maxRight.getGender() != ignoreGender) {
                max = maxRight.getOccurences();
                maxTreeNode = maxRight;
            }
        }

        if (treeNode.getLeftChild() != null) {
            TreeNode maxLeft = extractMax(treeNode.getLeftChild(), ignoreVal, ignoreGender);
            if (maxLeft.getOccurences() > max &&
                    !ignoreVal.contains(maxLeft.getOccurences()) &&
                    maxLeft.getGender() != ignoreGender) {
//                max = maxLeft.getOccurences();
                maxTreeNode = maxLeft;

            }
        }

        return maxTreeNode;
    }

    /**
     * determines if a node is in an array. DEPRECATED
     * @param arr TreeNode array
     * @param n TreeNode to find
     * @return boolean
     */
    private boolean contains(TreeNode[] arr, TreeNode n) {
        for (TreeNode treeNode : arr) {
            if (n == treeNode) {
                return true;
            }
        }
        return false;
    }

    /**
     * recursive kicker function
     */
    public void showNameAlphabetically() {
        showNameAlphabetically(root);
    }

    /**
     * prints tree in order traversal recursively. Tree is built alphabetically in order
     * @param treeNode
     */
    private void showNameAlphabetically(TreeNode treeNode) {
        if (treeNode != null) {
            showNameAlphabetically(treeNode.getLeftChild());
            printNodePlusPercent(treeNode);
            showNameAlphabetically(treeNode.getRightChild());
        }
    }

    public int getTotalOccurrences() {
        return totalOccurrences;
    }

    private int totalOccurrences(TreeNode treeNode) {
        if (treeNode != null) {
            return treeNode.getOccurences() + totalOccurrences(treeNode.getRightChild())
                    + totalOccurrences(treeNode.getLeftChild());
        } else return 0;
    }

    public int getNumberOfNames() {
        return getNumberOfNames(root);
    }

    private int getNumberOfNames(TreeNode treeNode) {
        if (treeNode != null) {
            return 1 + getNumberOfNames(treeNode.getRightChild())
                    + getNumberOfNames(treeNode.getLeftChild());
        } else return 0;
    }

    /**
     * simple print function to keep all printed data consistent. Pads the node data and the percent occurrences data
     * @param n
     */
    private void printNodePlusPercent(TreeNode n) {
        System.out.println(String.format("%20s", n) + " " +
                String.format("%.6f", (double)n.getOccurences() / totalOccurrences * 100) + "%");
    }
}
