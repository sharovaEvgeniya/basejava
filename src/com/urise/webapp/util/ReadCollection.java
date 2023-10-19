package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ReadCollection<T> {
    T read() throws IOException;
}
