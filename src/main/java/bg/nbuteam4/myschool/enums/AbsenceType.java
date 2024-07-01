package bg.nbuteam4.myschool.enums;

public enum AbsenceType {

    SICK(1, "Болест"), // болест
    FAMILY_REASONS(2, "Домашни причини"), // домашни причини
    LATE(3, "Закъснение"), // закъснение
    ABSENT(9, "Отсъствие"); // отсъствие

    private final int value;
    private final String label;

    AbsenceType(int value, String label) {
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
