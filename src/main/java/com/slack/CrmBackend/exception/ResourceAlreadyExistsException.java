package com.slack.CrmBackend.exception;

/**
 * Resource Already Exists Exception
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
