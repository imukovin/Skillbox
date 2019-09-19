public class Summary {
    double income;
    double outcome;

    Summary(double income, double outcome) {
        this.income = income;
        this.outcome = outcome;
    }

    static Summary fromCompany(Company company) {
        return new Summary(Double.parseDouble(company.getIncome()), Double.parseDouble(company.getOutcome()));
    }

    static Summary merge(Summary s1, Summary s2) {
        return new Summary(s1.income + s2.income, s1.outcome + s2.outcome);
    }
}
