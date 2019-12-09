import java.util.HashMap;
import java.util.Random;

public class Bank {
    private static final int MIN_AMOUNT_AS_FRAUD = 50_000;

    private volatile HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public String transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        boolean result = false;
        if (amount > MIN_AMOUNT_AS_FRAUD) {
            result = ((accounts.get(fromAccountNum).isIsblocked()) || (accounts.get(toAccountNum).isIsblocked())) || isFraud(fromAccountNum, toAccountNum, amount);
        }
        if (result) {
            accounts.get(fromAccountNum).setIsblocked(true);
            accounts.get(toAccountNum).setIsblocked(true);
            return "The amount is block!!!!";
        } else{
            if (accounts.get(fromAccountNum).getMoney() >= amount) {
                if (fromAccountNum.equals(toAccountNum)) {
                    return "You can't transfer money yourself!";
                } else {
                    Account firstBlock;
                    Account secondBlock;
                    if (Integer.parseInt(accounts.get(toAccountNum).getAccNumber()) < Integer.parseInt(accounts.get(fromAccountNum).getAccNumber())) {
                        firstBlock = accounts.get(toAccountNum);
                        secondBlock = accounts.get(fromAccountNum);
                    } else {
                        firstBlock = accounts.get(fromAccountNum);
                        secondBlock = accounts.get(toAccountNum);
                    }
                    synchronized (firstBlock) {
                        accounts.get(fromAccountNum).setMoney(accounts.get(fromAccountNum).getMoney() - amount);
                        synchronized (secondBlock) {
                            accounts.get(toAccountNum).setMoney(accounts.get(toAccountNum).getMoney() + amount);
                        }
                    }
                    return "Client: " + fromAccountNum + " transfer amount: " + amount + " to " + toAccountNum + "------ current money (from account)" + accounts.get(fromAccountNum).getMoney();
                }
            } else {
                return "Client: " + fromAccountNum + "  don't have enough money!";
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
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
