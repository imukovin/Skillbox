public class BankGetAmountTest extends Thread {
    private Bank bank;
    private int numberOfClients;

    BankGetAmountTest(Bank bank, int numberOfClients){
        this.bank = bank;
        this.numberOfClients = numberOfClients;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10; i++){
            String client = Long.toString((long) (Math.random() * numberOfClients) + 1);
            System.out.println("Client number: " + client + " get balance - " + bank.getBalance(client));

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
