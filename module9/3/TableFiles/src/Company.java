public class Company {
    private String name;
    private String income;
    private String outcome;

    Company(String name, String income, String outcome) {
        this.name = name;
        this.income = income;
        this.outcome = outcome;
    }

    public void print() {
        System.out.println(name + " " + income + " " + outcome);
    }
}