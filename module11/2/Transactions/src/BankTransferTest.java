public class BankTransferTest extends Thread {
    private Bank bank;
    private int numberOfClients;

    BankTransferTest(Bank bank, int numberOfClients){
        this.bank = bank;
        this.numberOfClients = numberOfClients;
    }

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 100; i++){
            try {
                String transferRes = bank.transfer(
                        Long.toString((long) (Math.random() * numberOfClients)),
                        Long.toString((long) (Math.random() * numberOfClients)),
                        (long) (Math.random() * 1_000_000));
                System.out.println(transferRes);

                sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("Account doesn't exist!");
            }
        }
    }
}

