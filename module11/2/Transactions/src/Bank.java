import java.util.HashMap;
import java.util.Random;

public class Bank {
    private static final int MIN_AMOUNT_AS_FRAUD = 50_000;
    private static final int MONEY_FOR_BLOCKED = -100;

    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: ����������� �����. ����� ��������� ������ ����� �������.
     * ���� ����� ���������� > 50000, �� ����� ���������� ����������,
     * ��� ������������ �� �������� ������ ������������ � ����������
     * ����� isFraud. ���� ������������ true, �� �������� ����������
     * ������ (��� � �� ���� ����������)
     */
    public String transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        boolean result = false;
        if (amount > MIN_AMOUNT_AS_FRAUD) {
            result = isFraud(fromAccountNum, toAccountNum, amount);
        }
        if (result) {
            accounts.get(fromAccountNum).setMoney(MONEY_FOR_BLOCKED);
            accounts.get(toAccountNum).setMoney(MONEY_FOR_BLOCKED);
            return "The amount is block!!!!";
        }else{
            if (accounts.get(fromAccountNum).getMoney() >= amount) {
                if (fromAccountNum.equals(toAccountNum)) {
                    return "You can't transfer money yourself!";
                }else {
                    accounts.get(fromAccountNum).setMoney(accounts.get(fromAccountNum).getMoney() - amount);
                    accounts.get(toAccountNum).setMoney(accounts.get(toAccountNum).getMoney() + amount);
                    return "Client: " + fromAccountNum + " transfer amount: " + amount + " to " + toAccountNum;
                }
            }else {
                return "Client: " + fromAccountNum + "  don't have enough money!";
            }
        }
    }

    /**
     * TODO: ����������� �����. ���������� ������� �� �����.
     */
    public long getBalance(String accountNum)
    {
        return accounts.get(accountNum).getMoney();
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }
}
