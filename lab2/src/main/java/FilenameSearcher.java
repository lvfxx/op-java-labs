import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class FilenameSearcher {

    public static Set<Path> find(Path source, String pattern) throws IOException {
        Set<Path> result = new HashSet<>();
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().contains(pattern))
                    result.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }

}
