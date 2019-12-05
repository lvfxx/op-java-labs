import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class GrepEnhanced {

    public static Set<Result> find(Path source, byte[] pattern) throws IOException {
        GrepWorker worker = new GrepWorker(pattern);
        Set<Result> result = new HashSet<>();
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                int res = worker.search(new FileInputStream(file.toFile()));
                if (res != GrepWorker.NOT_FOUND)
                    result.add(new Result(file, res));
                return FileVisitResult.CONTINUE;
            }

        });
        return result;
    }

    public static class Result {
        final Path path;
        final int position;

        public Result(Path path, int position) {
            this.path = path;
            this.position = position;
        }
    }

}
