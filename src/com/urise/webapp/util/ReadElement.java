package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ReadElement<T> {
    T read() throws IOException;
}
