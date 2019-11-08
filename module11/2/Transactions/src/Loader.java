import java.util.concurrent.atomic.AtomicLong;

public class Loader {
    private static final int MAX_MONEY = 1_000_000;
    private static final int NUM_OF_CLIENTS = 1000;

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        long allClientsMoney = 0L;

        for (int i = 1; i <= NUM_OF_CLIENTS; i++){
            long money = (long)(Math.random() / i * MAX_MONEY);
            allClientsMoney += money;
            bank.getAccounts().put(
                    Integer.toString(i),
                    new Account(money, Integer.toString(i)));
        }

        System.out.println("All clients money: " + allClientsMoney);

        BankGetAmountTest bg = new BankGetAmountTest(bank, NUM_OF_CLIENTS);
        bg.start();

        long deadlockClientA = (long) (Math.random() * NUM_OF_CLIENTS);
        long deadlockClientB = (long) (Math.random() * NUM_OF_CLIENTS);
        long moneyForTransfer = 10;

        BankTransferTest bt = new BankTransferTest(bank, NUM_OF_CLIENTS, deadlockClientA, deadlockClientB, moneyForTransfer);
        bt.start();
        BankTransferTest bt1 = new BankTransferTest(bank, NUM_OF_CLIENTS, deadlockClientB, deadlockClientA, moneyForTransfer);
        bt1.start();

        bg.join();
        bt.join();
        bt1.join();

        AtomicLong allMoney = new AtomicLong();
        bank.getAccounts().forEach((s, account) -> allMoney.addAndGet(account.getMoney()));
        System.out.println("Money after transaction: " + allMoney);
    }
}