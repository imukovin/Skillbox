import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Loader {
    private static double income = 0;
    private static double outcome = 0;

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("res/movementList.csv"));
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length != 8) {
                    //System.out.println("Wrong: " + lines.get(i));
                    String money = parts[7].replace("\"", "") + "." + parts[8].replace("\"", "");
                    //System.out.println(parts[6] + " " + money);
                    income += Double.parseDouble(parts[6]);
                    outcome += Double.parseDouble(money);

                    System.out.println(parts[5]);
                } else {
                    income += Double.parseDouble(parts[6]);
                    outcome += Double.parseDouble(parts[7]);

                    System.out.println(parts[5]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Приход: " + income);
        System.out.println("Расход: " + outcome);
    }
}
