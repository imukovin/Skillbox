import java.io.*;

public class Runrun implements Runnable {
    public static final int NUM_OF_NUMBERS = 1000;
    private String fileName;
    private int regionCode;

    Runrun(String fileName, int regionCode) {
        this.fileName = fileName;
        this.regionCode = regionCode;
    }

    @Override
    public void run() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            StringBuilder sb;
            for (int number = 1; number < NUM_OF_NUMBERS; number++) {
                sb = new StringBuilder();
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            sb.append(firstLetter)
                                    .append(padNumber(number, 3))
                                    .append(secondLetter)
                                    .append(thirdLetter)
                                    .append(padNumber(regionCode, 2))
                                    .append('\n');
                        }
                    }
                }
                printWriter.write(sb.toString());
            }
            fileOutputStream.getChannel().force(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static StringBuffer padNumber(int number, int numberLength)
    {
        StringBuffer numberStr = new StringBuffer();
        int padSize = numberLength - Integer.toString(number).length();
        for(int i = 0; i < padSize; i++) {
            numberStr.append(0);
        }
        return numberStr.append(number);
    }
}
