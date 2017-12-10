package OperationExecutors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteVisitor extends SimpleFileVisitor<Path> {
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) throws IOException {
        path.toFile().setWritable(true);
        path.toFile().delete();
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }
}
