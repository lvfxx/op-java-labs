package nsu.fit.javaperf.lab3;

public class Lab3 {
    int coins = 1;

    void multiplyCoins(int ratio) {
        int newCoins = coins * ratio;
        coins = newCoins;
    }

    public static void main(String[] args) {
        Lab3 l2 = new Lab3();
        l2.multiplyCoins(10);
        l2.multiplyCoins(20);
    }
}
