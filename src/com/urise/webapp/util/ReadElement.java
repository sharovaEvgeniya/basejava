package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface ReadElement {
    void read() throws IOException;
}
