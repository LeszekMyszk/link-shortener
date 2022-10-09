package dev.greencashew.linkshortener.link.exception;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException(final String id) {
        super("Link for " + id +" not found");
    }

    public LinkNotFoundException() {

    }
}
