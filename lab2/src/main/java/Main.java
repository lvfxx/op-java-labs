import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            printHelp();
            return;
        }
        switch (args[0]) {
            case "--data":
                if (args.length < 3) printHelp();
                else findData(args[1], args[2]);
                break;
            case "--name":
                if (args.length < 3) printHelp();
                else findName(args[1], args[2]);
                break;
            case "-h":
            default:
                printHelp();
                break;
        }
    }

    private static void findName(String filename, String pattern) {
        try {
            FilenameSearcher.find(Path.of(filename), pattern).forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void findData(String filename, String pattern) {
        try {
            Set<GrepEnhanced.Result> result = GrepEnhanced.find(Path.of(filename), pattern.getBytes());
            result.forEach(res -> System.out.println(res.path + " (starting position: " + res.position + ")"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar prog.jar <option>\n" +
                "Options:\n" +
                "-h                      print help\n" +
                "--data <path> <pattern> find text pattern in specified file or directory including subdirectories\n" +
                "--name <path> <pattern> find text pattern in file names in specified directory including subdirectories");
    }
}
