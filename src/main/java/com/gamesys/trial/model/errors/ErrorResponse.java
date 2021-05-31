package com.gamesys.trial.model.errors;

/**
 * Provides a simple error code and description to the caller. The code provides a way to categorise the errors. e.g.
 *
 * <pre>
 * {
 *   "error": {
 *     "code": "ERROR-2",
 *     "description": "Unknown error occurred"
 *   }
 * }
 * </pre>
 *
 * Usage:
 *
 * <pre>
 * return ErrorResponse.of(ErrorCode.VALIDATION_FAILED); // Uses generic description from ErrorCode
 * // or
 * return ErrorResponse.of(ErrorCode.VALIDATION_FAILED, "Validation error");
 * </pre>
 */
public class ErrorResponse {
    private final Error error;

    public ErrorResponse(final Error error)
    {
        this.error = error;
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(new Error(errorCode.getCode(), errorCode.getDescription()));
    }

    public static ErrorResponse of(final ErrorCode errorCode, final String description) {
        return new ErrorResponse(new Error(errorCode.getCode(), description));
    }

    public Error getError()
    {
        return error;
    }

    public static class Error {
        private final String code;
        private final String description;

        public Error(final String code, final String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode()
        {
            return code;
        }

        public String getDescription()
        {
            return description;
        }
    }
}
