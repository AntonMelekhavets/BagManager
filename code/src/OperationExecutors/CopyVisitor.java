package OperationExecutors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyVisitor extends SimpleFileVisitor<Path> {
    private Path source, destinition;

    public CopyVisitor(Path source, Path destinition) {
        this.source = source;
        this.destinition = destinition;
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) throws IOException {
        Path newDestinition = destinition.resolve(source.relativize(path));
        Files.copy(path, newDestinition, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) throws IOException {
        Path newDestinition = destinition.resolve(source.relativize(path));
        Files.copy(path, newDestinition, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }
}
