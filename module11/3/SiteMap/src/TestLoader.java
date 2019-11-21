import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestLoader {
    @Test
    public void testLoader() {
        Loader loader = new Loader();

        String result = "";
        try(FileReader reader = new FileReader("outElem/siteMap.txt"))
        {
            int c;
            while((c = reader.read())!=-1){
                result = result + Character.toString(c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        String expectedResult = "";
        try(FileReader reader = new FileReader("outElem/expectedSiteMap.txt"))
        {
            int c;
            while((c = reader.read())!=-1){
                expectedResult = expectedResult + Character.toString(c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        Assert.assertEquals(expectedResult, result);
    }
}
