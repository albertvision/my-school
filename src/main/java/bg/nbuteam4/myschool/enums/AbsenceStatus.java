package bg.nbuteam4.myschool.enums;

public enum AbsenceStatus {

    UNPROCESSED(0), // необработено
    UNEXCUSED(1),   // неизвинено
    EXCUSED(2);     // извинено

    private final int value;

    AbsenceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
