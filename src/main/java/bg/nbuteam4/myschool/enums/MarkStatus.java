package bg.nbuteam4.myschool.enums;

public enum MarkStatus {

    CURRENT(0, "текуща"),
    SEMESTRIAL(1, "срочна"),
    YEARLY(9, "годишна");

    private final int value;
    private final String label;

    MarkStatus(int value, String label) {
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
