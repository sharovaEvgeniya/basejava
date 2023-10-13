package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface WriteElement<T> {
    void write(T t) throws IOException;
}
