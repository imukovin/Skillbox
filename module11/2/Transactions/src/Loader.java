public class Loader {
    private static final long MAX_MONEY = 10_000_000L;
    private static final int NUM_OF_CLIENTS = 1000;

    public static void main(String[] args) {

        Bank bank = new Bank();

        for (int i = 0; i < NUM_OF_CLIENTS; i++){
            bank.getAccounts().put(
                    Integer.toString(i),
                    new Account(((long) (Math.random() / i * MAX_MONEY)), Integer.toString(i)));
        }

        BankGetAmountTest bg = new BankGetAmountTest(bank, NUM_OF_CLIENTS);
        bg.start();

        BankTransferTest bt = new BankTransferTest(bank, NUM_OF_CLIENTS);
        bt.start();
    }
}