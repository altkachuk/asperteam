package atproj.cyplay.com.asperteamapi.model.exception;

/**
 * Created by andre on 11-Apr-18.
 */

public class BaseException extends RuntimeException {

    private ExceptionType _exceptionType;

    public BaseException(ExceptionType exceptionType) {
        _exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return _exceptionType;
    }
}
