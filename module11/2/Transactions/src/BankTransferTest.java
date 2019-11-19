public class BankTransferTest extends Thread {
    private Bank bank;
    private int numberOfClients;

    private long clientA;
    private long clientB;
    private long moneyForTransfer;

    BankTransferTest(Bank bank, int numberOfClients, long clientA, long clientB, long money){
        this.bank = bank;
        this.numberOfClients = numberOfClients;
        this.clientA = clientA;
        this.clientB = clientB;
        moneyForTransfer = money;
    }

    @Override
    public void run() {
        super.run();
        try {
            String transferRes = bank.transfer(
                    Long.toString(clientA),
                    Long.toString(clientB),
                    moneyForTransfer);
            System.out.println(transferRes + " Deadlock");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*for (int i = 0; i < 100; i++){
            try {
                String transferRes = bank.transfer(
                        Long.toString((long) (Math.random() * numberOfClients)),
                        Long.toString((long) (Math.random() * numberOfClients)),
                        (long) (Math.random() * 1_000_000));
                System.out.println(transferRes);

                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}

