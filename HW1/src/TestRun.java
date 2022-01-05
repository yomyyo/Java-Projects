import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * @author ADKN
 * @since 21 Jan 2017
 */
public class TestRun {
    public static void main(String[] args) throws FileNotFoundException {
        String[] array1 = {"peterpan.txt", "10", "0.25", "peterpan.dictionary"};
        String[] array2 = {"peterpan.txt", "3",  "0.15", "peterpan.dictionary"};
        String[] array3 = {"peterpan.txt", "5", "0.03", "peterpan.dictionary"};

        String[] array4 = {"test1.txt", "3", "0.2", "dict1.txt"};
        String[] array5 = {"test2.txt", "3", ".3", "dict2.txt"};

        System.out.println("COUNT : " + array1[1] + ", PROB : " + array1[2]);
        Program1.main(array1);
        System.out.println();

        System.out.println("COUNT : " + array2[1] + ", PROB : " + array2[2]);
        Program1.main(array2);
        System.out.println();

        System.out.println("COUNT : " + array3[1] + ", PROB : " + array3[2]);
        Program1.main(array3);
        System.out.println();

//        System.out.println("COUNT : " + array4[1] + ", PROB : " + array4[2]);
//        Program1.main(array4);
//        System.out.println();
//
//        System.out.println("COUNT : " + array5[1] + ", PROB : " + array5[2]);
//        Program1.main(array5);
//        System.out.println();
    }
}
