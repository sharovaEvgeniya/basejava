package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface WriteCollection<T> {
    void write(T t) throws IOException;
}
