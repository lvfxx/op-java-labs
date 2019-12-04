import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BMHAlgorithmWorker worker = new BMHAlgorithmWorker("d".getBytes());
        try {
            int result = worker.search(
                    new ByteArrayInputStream("ssd".getBytes())
            );
            System.out.println(result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
