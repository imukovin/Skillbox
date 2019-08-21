import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args)
    {
        ArrayList<Employee> staff = loadStaffFromFile();

        staff.sort((o1, o2) -> o1.getSalary().compareTo(o2.getSalary()));
        staff.sort((o1, o2) -> {
            int compareSalary = o1.getSalary().compareTo(o2.getSalary());
            int compareName = o1.getName().compareTo(o2.getName());
            if ((compareSalary == 0) && (compareName != 0)) {
                return compareName;
            }
            if ((compareSalary == 0) && (compareName == 0)) {
                return 0;
            }
            return -1;
        });

//        Collections.sort(staff, Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));

        for (Employee empl: staff) {
            System.out.println(empl);
        }
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}