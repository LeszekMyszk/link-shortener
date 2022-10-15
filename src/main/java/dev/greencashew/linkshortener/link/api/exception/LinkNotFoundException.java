package dev.greencashew.linkshortener.link.api.exception;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException(final String id) {
        super("Link for " + id +" not found");
    }

    public LinkNotFoundException() {

    }
}
