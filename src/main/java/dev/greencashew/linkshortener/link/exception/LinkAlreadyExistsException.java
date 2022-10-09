package dev.greencashew.linkshortener.link.exception;

public class LinkAlreadyExistsException extends RuntimeException{
    public LinkAlreadyExistsException(final String id) {

        super("Link already exist");
    }

}
