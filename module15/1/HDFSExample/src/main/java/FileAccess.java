import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileAccess implements AutoCloseable {
    private static final String HADOOP_USER_NAME = "Aleksey";

    private FileSystem hdfs = null;

    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     * for example, hdfs://localhost:32771
     */
    FileAccess(String rootPath) throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        //configuration.set("dfs.support.append", "true");
        System.setProperty("HADOOP_USER_NAME", HADOOP_USER_NAME);

        hdfs = FileSystem.get(
                new URI(rootPath), configuration
        );
    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public  void create(String path) throws IOException {
        Path file = new Path(path);
        if (!hdfs.exists(file)) {
            int count = path.split("/").length;
            boolean isFile = path.split("/")[count - 1].contains(".");
            if (isFile) {
                hdfs.createNewFile(file);
            } else {
                hdfs.mkdirs(file);
            }
        }
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws IOException {
        hdfs.setReplication(new Path(path), (short) 1);
        if (hdfs.isFile(new Path(path))) {
            try (FSDataOutputStream fsDataOutputStream = hdfs.append(new Path(path), content.getBytes().length);
                 BufferedWriter bufferedWriter =
                         new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8))) {
                bufferedWriter.write(content);
                bufferedWriter.flush();
                hdfs.setReplication(new Path(path), (short) 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Попытка дозаписи к директории!!!");
        }
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path) throws IOException {
        String text = "";
        try (FSDataInputStream inputStream = hdfs.open(new Path(path))) {
            text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        return text;
    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) throws IOException {
        hdfs.deleteOnExit(new Path(path));
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) throws IOException {
        return hdfs.isDirectory(new Path(path));
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path) throws IOException {
        ArrayList<String> files = new ArrayList<>();
        FileStatus[] fileStatus = hdfs.listStatus(new Path(path));
        for(FileStatus status : fileStatus){
            files.add(status.getPath().toString());
        }
        return files;
    }

    @Override
    public void close() throws Exception {
        hdfs.close();
    }
}
