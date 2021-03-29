package org.energygrid.east.userservice.errormessages;

import org.springframework.lang.Nullable;

public class DuplicatedNameException extends RuntimeException {
    public DuplicatedNameException(@Nullable String message) {
        super(message);
    }
}
