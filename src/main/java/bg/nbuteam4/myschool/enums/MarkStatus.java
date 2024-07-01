package bg.nbuteam4.myschool.enums;

public enum MarkStatus {

    CURRENT(0), // текуща
    SEMESTRIAL(1),   // срочна
    YEARLY(9);     // годишна
    //0 - текуща, 1 - срочна, 9 - годишна
    private final int value;

    MarkStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
