import org.junit.Assert;
import org.junit.Test;

public class TestLoader {
    int c = 0;
    MyLock lock = new MyLock();
    @Test
    public void TestGetResult() throws InterruptedException {
        /*MyLock l = new MyLock();
        l.lock();
        System.out.println("Hi");
        l.unlock();*/

        /*Loader loader = new Loader(new SharedRecource());
        int expectedResult = 90000;
        int result = loader.getResult(100000, 10000);
        Assert.assertEquals(expectedResult, result);

        loader = new Loader(new SharedRecource());
        expectedResult = 80000;
        result = loader.getResult(100000, 20000);
        Assert.assertEquals(expectedResult, result);*/

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                lock.lock();
                c++;
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                lock.lock();
                c--;
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assert.assertEquals(0, c);
    }
}
