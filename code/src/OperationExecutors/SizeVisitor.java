package OperationExecutors;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SizeVisitor extends SimpleFileVisitor<Path> {
    private long size;

    public SizeVisitor() {
        size = 0;
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) throws IOException {
        size += fileAttributes.size();
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(File file, IOException exc) {
        return null;
    }

    public String getSizeOfFile() {
        String sizeValue = null;
        for (int i = 0; ; i++) {
            if (size < 1000) {
                switch (i) {
                    case 0:
                        sizeValue = " B";
                        break;
                    case 1:
                        sizeValue = " KB";
                        break;
                    case 2:
                        sizeValue = " MB";
                        break;
                    case 3:
                        sizeValue = " GB";
                        break;
                }
                break;
            }
            size /= 1024;
        }
        return Long.toString(size) + sizeValue;
    }
}
