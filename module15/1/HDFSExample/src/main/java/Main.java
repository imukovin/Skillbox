public class Main
{
    private static final String HADOOP_NODE_ADDRESS = "hdfs://0.0.0.0:9000";

    public static void main(String[] args) throws Exception
    {
        FileAccess fileAccess = new FileAccess(HADOOP_NODE_ADDRESS);
        fileAccess.create("hdfs://0.0.0.0:9000/test");
        fileAccess.append("hdfs://0.0.0.0:9000/file.txt", "apll");
        //System.out.println(fileAccess.read("hdfs://0.0.0.0:9000/file.txt"));
        fileAccess.delete("hdfs://0.0.0.0:9000/te");
        System.out.println("isDirectory: " + fileAccess.isDirectory("hdfs://0.0.0.0:9000/test"));
        System.out.println("------File List-----");
        for (String f : fileAccess.list("hdfs://0.0.0.0:9000/")) {
            System.out.println(f);
        }
        fileAccess.close();
    }
}
