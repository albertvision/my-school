package bg.nbuteam4.myschool.enums;

public enum AbsenceType {

    SICK(1), // болест
    FAMILY_REASONS(2), // домашни причини
    LATE(3), // закъснение
    ABSENT(9); // отсъствие

    private final int value;

    AbsenceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
