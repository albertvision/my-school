package bg.nbuteam4.myschool.enums;

public enum AbsenceStatus {

    UNPROCESSED(0, "необработено"),
    UNEXCUSED(1, "неизвинено"),
    EXCUSED(2, "извинено");

    private final int value;
    private final String label;

    AbsenceStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
