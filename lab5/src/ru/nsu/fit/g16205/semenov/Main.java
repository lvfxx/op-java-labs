package ru.nsu.fit.g16205.semenov;

import java.util.Arrays;

public class Main {

    private static final int BYTES_IN_LONG = Long.SIZE / Byte.SIZE;

    public static void main(String[] args) {
        System.out.println(countBits(1));
        System.out.println(countBits(2));
        System.out.println(countBits(3));
        System.out.println(countBits(Long.MAX_VALUE));
        System.out.println(countBits(Long.MIN_VALUE));
        System.out.println(countBits(-1));

        long[] la1 = new long[]{258, 4};
        long[] la2 = new long[]{1, -1, 0, 257, Long.MIN_VALUE, Long.MAX_VALUE};

        byte[] ba1 = longArrayToByteArray(la1);
        byte[] ba2 = longArrayToByteArray(la2);
        System.out.println(Arrays.toString(ba1));
        System.out.println(Arrays.toString(ba2));

        long[] la1back = byteArrayToLongArray(ba1);
        long[] la2back = byteArrayToLongArray(ba2);
        System.out.println(Arrays.toString(la1back));
        System.out.println(Arrays.toString(la2back));
    }

    private static int countBits(long l) {
        int count = 0;
        for (int i = 0; i < Long.SIZE; ++i)
            count += (l >> i) & 1;
        return count;
    }

    private static byte[] longArrayToByteArray(long[] longArray) {
        int byteArrayLength = longArray.length * BYTES_IN_LONG;
        byte[] byteArray = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; ++i) {
            int longIndex = i / BYTES_IN_LONG;
            int longShift = Long.SIZE - (i % BYTES_IN_LONG) * Byte.SIZE - Byte.SIZE;
            byteArray[i] = (byte) (longArray[longIndex] >> longShift);
        }
        return byteArray;
    }

    private static long[] byteArrayToLongArray(byte[] byteArray) {
        int longArrayLength = byteArray.length / BYTES_IN_LONG + ((byteArray.length % BYTES_IN_LONG) > 0 ? 1 : 0);
        long[] longArray = new long[longArrayLength];
        for (int i = 0; i < byteArray.length; ++i) {
            int longIndex = i / BYTES_IN_LONG;
            int longShift = Long.SIZE - (i % BYTES_IN_LONG) * Byte.SIZE - Byte.SIZE;
            long toAdd = ((long)(byteArray[i]) & 0xff) << longShift;
            longArray[longIndex] |= toAdd;
        }
        return longArray;
    }
}
