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

        for (int i = 0; i < 20; i++){
            try {
                /*String transferRes = bank.transfer(
                        Long.toString((long) (Math.random() * numberOfClients) + 1),
                        Long.toString((long) (Math.random() * numberOfClients) + 1),
                        (long) (Math.random() * 10_000));
                System.out.println(transferRes);
*/
                String transferRes = bank.transfer("3", "1",
                        (long) (Math.random() * 10_000));
                System.out.println(transferRes + " Num thread: " + Thread.currentThread().getId());
                transferRes = bank.transfer("3", "2",
                        (long) (Math.random() * 10_000));
                System.out.println(transferRes + " Num thread: " + Thread.currentThread().getId());

            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("Account doesn't exist!");
            }
        }
    }
}

