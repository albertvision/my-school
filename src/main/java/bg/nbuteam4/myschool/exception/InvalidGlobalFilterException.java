package bg.nbuteam4.myschool.exception;

public class InvalidGlobalFilterException extends RuntimeException {
    private final String filterName;

    public InvalidGlobalFilterException(String filterName) {
        super("Invalid global filter");

        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }
}
