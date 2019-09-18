import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {
    private final static int POS_OPERATION_DISCRIPTION = 5;
    private final static int POS_INCOME = 6;
    private final static int POS_OUTCOME = 7;

    private static double totalIncome = 0;
    private static double totalOutcome = 0;

    private static ArrayList<Company> company = new ArrayList<>();

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("res/movementList.csv"));

            for (int i = 1; i < lines.size(); i++) {
                company.add(parseReadLine(lines.get(i)));
            }
            //company.forEach(company1 -> company1.print());

            company.stream().collect(Collectors.groupingBy(Company::getName)).forEach((s, companies) -> {
                System.out.print(s + ": ");
                double income = companies.stream().mapToDouble(company1 -> Double.parseDouble(company1.getIncome())).sum();
                double outcome = companies.stream().mapToDouble(company1 -> Double.parseDouble(company1.getOutcome())).sum();

                totalIncome += income;
                totalOutcome += outcome;

                System.out.println(income + " " + outcome);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Общий приход: " + totalIncome);
        System.out.println("Общий расход: " + totalOutcome);
    }

    private static Company parseReadLine(String line) {
        String[] fragments = line.split(",");
        String companyName = fragments[POS_OPERATION_DISCRIPTION]
                .replaceAll("[\\d+.]", "")
                .replaceAll("[\\s]{2,}", " ")
                .replaceAll("(.UR)(.*)(MCC)", "")
                .replaceAll("(USD)(.*)(MCC)", "")
                .replaceAll("/", "\\\\")
                .replaceAll("(.?\\\\)(.*)(\\\\)", "")
                .replaceAll("CARDCARD", "")
                .trim();

        String income = fragments[POS_INCOME];
        String outcome = fragments[POS_OUTCOME];

        if (income.contains("\"")) {
            income = income.replace("\"", "") + "." + fragments[POS_INCOME + 1].replace("\"", "");
            if (fragments[POS_INCOME + 2].contains("\"")) {
                outcome = fragments[POS_INCOME + 2].replace("\"", "") + "." + fragments[POS_INCOME + 3].replace("\"", "");
            }
        }
        if ((!income.contains("\"")) && (fragments[POS_OUTCOME].contains("\""))) {
            outcome = fragments[POS_OUTCOME].replace("\"", "") + "." + fragments[POS_OUTCOME + 1].replace("\"", "");
        }

        return new Company(companyName, income, outcome);
    }
}