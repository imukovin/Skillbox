import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private final static int POS_OPERATION_DISCRIPTION = 5;
    private final static int POS_INCOME = 6;
    private final static int POS_OUTCOME = 7;

    private static double totalIncome = 0;
    private static double totalOutcome = 0;

    private static ArrayList<String> visitedCompany = new ArrayList<>();

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("res/movementList.csv"));
            lines.remove(0);

            lines.stream().forEach(str -> {
                String company = str.split(",")[POS_OPERATION_DISCRIPTION]
                        .replaceAll("[\\d+.]", "")
                        .replaceAll("[\\s]{2,}", " ")
                        .replaceAll("(.UR)(.*)(MCC)", "")
                        .replaceAll("(USD)(.*)(MCC)", "")
                        .replaceAll("/", "\\\\")
                        .replaceAll("(.?\\\\)(.*)(\\\\)", "")
                        .replaceAll("CARDCARD", "")
                        .trim();

                Double income = .0;
                Double outcome = .0;

                if (!visitedCompany.contains(company)) {
                    visitedCompany.add(company);
                    income = lines.stream()
                            .filter(s -> s.contains(company))
                            .mapToDouble(s1 -> {
                                String[] fragment = s1.split(",");
                                if (fragment.length != 8) {
                                    String come = s1.split("\"")[1].replace(",", ".");
                                    return Double.parseDouble(come);
                                } else {
                                    return Double.parseDouble(s1.split(",")[POS_INCOME]);
                                }
                            }).sum();
                    outcome = lines.stream()
                            .filter(s -> s.contains(company))
                            .mapToDouble(s1 -> {
                                String[] fragment = s1.split(",");
                                if (fragment.length != 8) {
                                    String come = fragment[fragment.length - 2].replace("\"", "") + "."
                                            + fragment[fragment.length - 1].replace("\"", "");
                                    return Double.parseDouble(come);
                                } else {
                                    return Double.parseDouble(s1.split(",")[POS_OUTCOME]);
                                }
                            }).sum();

                    totalIncome += income;
                    totalOutcome += outcome;
                    System.out.println(company + ": " + income + " " + outcome);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Общий приход: " + totalIncome);
        System.out.println("Общий расход: " + totalOutcome);
    }
}
