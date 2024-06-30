package bg.nbuteam4.myschool.enums;

public enum ActionResultType {
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    ERROR("danger");

    private final String uiClass;

    ActionResultType(String uiClass) {
        this.uiClass = uiClass;
    }

    public String getUiClass() {
        return uiClass;
    }
}
