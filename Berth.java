package TheProject;

public class Berth {

    private static int lowerBerth = 1;
    private static int upperBerth = 1;
    private static int sideBerth = 1;
    private static final int rac = 1;
    private static final int waitingList =1;

    public static int getLowerBerth() {
        return lowerBerth;
    }

    public static void setLowerBerth(int lowerBerth) {
        Berth.lowerBerth = lowerBerth;
    }

    public static int getUpperBerth() {
        return upperBerth;
    }

    public static void setUpperBerth(int upperBerth) {
        Berth.upperBerth = upperBerth;
    }

    public static int getSideBerth() {
        return sideBerth;
    }

    public static void setSideBerth(int sideBerth) {
        Berth.sideBerth = sideBerth;
    }

    public static int getRac() { return rac; }

    public static int getWaitingList() { return waitingList; }

}
