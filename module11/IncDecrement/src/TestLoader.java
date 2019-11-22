import org.junit.Assert;
import org.junit.Test;

public class TestLoader {
    @Test
    public void TestGetResult() throws InterruptedException {
        Loader loader = new Loader(new SharedRecource());
        int expectedResult = 90000;
        int result = loader.getResult(100000, 10000);
        Assert.assertEquals(expectedResult, result);

        loader = new Loader(new SharedRecource());
        expectedResult = 80000;
        result = loader.getResult(100000, 20000);
        Assert.assertEquals(expectedResult, result);
    }
}
