import gnu.trove.TCollections;
import gnu.trove.map.TByteIntMap;
import gnu.trove.map.hash.TByteIntHashMap;

public class ShiftsTable { // thread-safe

    private static final int NOT_FOUND = -1;

    private final TByteIntMap shiftsMap;
    private final int patternLength;

    public ShiftsTable(byte[] pattern) {
        assert pattern.length > 0;

        patternLength = pattern.length;
        byte[] patternInit = new byte[patternLength - 1];
        System.arraycopy(pattern, 0, patternInit, 0, patternLength - 1);

        final TByteIntMap shifts = new TByteIntHashMap();
        for (byte b : pattern) {
            if (shifts.containsKey(b)) continue;
            int lastIndex = lastIndexOf(b, patternInit);
            if (lastIndex != NOT_FOUND)
                shifts.put(b, patternLength - (lastIndex + 1));
        }
        shiftsMap = TCollections.unmodifiableMap(shifts);
    }

    public int getShift(byte b) {
        return shiftsMap.containsKey(b) ? shiftsMap.get(b) : patternLength;
    }

    private static int lastIndexOf(byte b, byte[] arr) {
        for (int i = arr.length; i > 0; --i) {
            if (arr[i - 1] == b)
                return i - 1;
        }
        return NOT_FOUND;
    }
}
