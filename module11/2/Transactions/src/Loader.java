import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Loader {
    private static final int MAX_MONEY = 1_000_000;
    private static final int NUM_OF_CLIENTS = 3;
    private static Bank bank;

    public static void main(String[] args) throws InterruptedException {
        bank = new Bank();
        long allClientsMoney = 0L;

        ExecutorService service = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= NUM_OF_CLIENTS; i++){
            long money = (long)((Math.random() + 0.1) / i * MAX_MONEY);
            allClientsMoney += money;
            bank.getAccounts().put(
                    Integer.toString(i),
                    new Account(money, Integer.toString(i)));
        }

        System.out.println("All clients money: " + allClientsMoney);

        for (int i = 0; i < 2; i++) {
            service.submit(new BankTransferTest(bank, NUM_OF_CLIENTS));
        }
        service.shutdown();
        service.awaitTermination(100000, TimeUnit.SECONDS);
        /*BankGetAmountTest bg = new BankGetAmountTest(bank, NUM_OF_CLIENTS);
        bg.start();

        BankTransferTest bt = new BankTransferTest(bank, NUM_OF_CLIENTS);
        bt.start();

        bg.join();
        bt.join();*/

        AtomicLong allMoney = new AtomicLong();
        bank.getAccounts().forEach((s, account) -> allMoney.addAndGet(account.getMoney()));
        System.out.println("---->>>  Money after transaction: " + allMoney);
    }
}