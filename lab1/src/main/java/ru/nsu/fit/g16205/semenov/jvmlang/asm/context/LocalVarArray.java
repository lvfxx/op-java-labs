package ru.nsu.fit.g16205.semenov.jvmlang.asm.context;

class LocalVarArray {
    private static final int MAX_VAR_ARRAY_SIZE = 1000;
    private static final int EMPTY = 0;
    private static final int OCCUPIED = 1;

    private int[] array = new int[MAX_VAR_ARRAY_SIZE];
    private int tailIndex = 0;

    int takeFree() {
        for (int i = 0; i < tailIndex; ++i) {
            if (array[i] == EMPTY) {
                array[i] = OCCUPIED;
                return i;
            }
        }
        if (tailIndex < MAX_VAR_ARRAY_SIZE) {
            array[tailIndex] = OCCUPIED;
            return tailIndex++;
        } else {
            // TODO resize var array?
            throw new IllegalStateException("Unable to find free position");
        }
    }

    void free(int index) {
        if (index >= MAX_VAR_ARRAY_SIZE || index < 0)
            throw new IllegalArgumentException("Invalid index specified");
        array[index] = EMPTY;
    }
}
