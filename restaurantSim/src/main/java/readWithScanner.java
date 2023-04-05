import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readWithScanner {
    public static void main(String[] args) {
        try {
//            File file = new File("/restaurantSim/src/main/resources/data1.txt");
            File file = new File(System.getProperty("user.dir") + "/restaurantSim/src/main/resources/data1.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
