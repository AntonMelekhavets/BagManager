package Model;

public class DirectoryMemento {
    private final String lastDirectory;

    public DirectoryMemento(String lastDirectory) {
        this.lastDirectory = lastDirectory;
    }

    public String getLastDirectory() {
        return lastDirectory;
    }
}
