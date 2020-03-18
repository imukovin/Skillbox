public class Main
{
    private static final String HADOOP_NODE_ADDRESS = "hdfs://0.0.0.0:9000";

    public static void main(String[] args) {
        try (FileAccess fileAccess = new FileAccess(HADOOP_NODE_ADDRESS)) {
            fileAccess.create("/test");
            fileAccess.append("/file.txt", "apll");
            System.out.println(fileAccess.read("/file.txt"));
            fileAccess.delete("/f");
            System.out.println("isDirectory: " + fileAccess.isDirectory("/test"));
            System.out.println("------File List-----");
            for (String f : fileAccess.list("/")) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
