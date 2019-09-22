public class Station {
    private String name;
    private String line;
    private String numOnLine;
    private String connection;

    Station(String name, String line, String numOnLine, String connection) {
        this.name = name;
        this.line = line;
        this.numOnLine = numOnLine;
        this.connection = connection;
    }

    public void print() {
        System.out.println("Name: " + name + " Line and num: " + line + " " + numOnLine + " Connec: " + connection);
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public String getNumOnLine() {
        return numOnLine;
    }

    public String getConnection() {
        return connection;
    }
}
