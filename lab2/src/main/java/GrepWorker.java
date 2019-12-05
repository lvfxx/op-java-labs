import java.io.IOException;
import java.io.InputStream;

public class GrepWorker {

    public static final int NOT_FOUND = -1;

    private final byte[] pattern;
    private final ShiftsTable shiftsTable;

    public GrepWorker(byte[] pattern) {
        this.pattern = pattern;
        shiftsTable = new ShiftsTable(pattern);
    }

    // with pre-calculated shifts table
    public GrepWorker(byte[] pattern, ShiftsTable shiftsTable) {
        this.pattern = pattern;
        this.shiftsTable = shiftsTable;
    }

    public int search(InputStream fis) throws IOException {
        assert pattern.length > 0 && fis != null;

        byte[] text = new byte[pattern.length];
        int shift = pattern.length;
        int currentPosition = 0;

        while(readBytes(fis, text, shift) == shift) {
            currentPosition += shift;
            int index = lastIndexOfDifference(text, pattern);
            if (index == NOT_FOUND)
                return currentPosition - pattern.length;

            shift = shiftsTable.getShift(text[index]);
        }
        return NOT_FOUND;
    }

    /**
     * Shifts left @param dest to @param count bytes
     * and then tries to read @param count bytes from @param src and write them to the end of @param dest
     *
     * @return number of bytes read into the buffer or -1 if there is no more data
     */
    private static int readBytes(InputStream src, byte[] dest, int count) throws IOException {
        assert dest.length >= count && count > 0;
        if (dest.length > count)
            System.arraycopy(dest, count, dest, 0, dest.length - count);
        return src.read(dest, dest.length - count, count);
    }

    private static int lastIndexOfDifference(byte[] arr1, byte[] arr2) {
        assert arr1.length == arr2.length;
        for (int i = arr1.length; i > 0; --i) {
            if (arr1[i - 1] != arr2[i - 1])
                return i - 1;
        }
        return NOT_FOUND;
    }
}
