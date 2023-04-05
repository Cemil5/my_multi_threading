import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\bilgi-999\\IdeaProjects\\thread-safety\\my_multi_threading\\restaurantSim\\src\\main\\resources\\data1.txt");
        File file2 = new File("\\src\\main\\resources\\data1.txt");
        File file4 = new File("\\data1.txt");

        BufferedReader file3 = new BufferedReader(new FileReader(file));
        System.out.println(file3.readLine());

        System.out.println(file.getAbsolutePath());
        System.out.println(file2.getPath());


    }
}
