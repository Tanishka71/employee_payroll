package com.bridgelabz.jdbc;
/**
 * @desc: Custom exception class for database-related errors.
 */
@SuppressWarnings("serial")
public class CustomDatabaseException extends Exception {
    
    /**
     * @desc: Constructs a new custom database exception with the specified detail message and cause.
     * @params:
     *   - message: The detail message (which is saved for later retrieval by the getMessage() method).
     *   - cause: The cause (which is saved for later retrieval by the getCause() method).
     */
    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
