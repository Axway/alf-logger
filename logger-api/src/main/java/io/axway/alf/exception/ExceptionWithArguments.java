package io.axway.alf.exception;

import java.util.function.*;
import javax.annotation.*;
import io.axway.alf.Arguments;

public interface ExceptionWithArguments {
    /**
     * @return the message without the arguments
     */
    String getRawMessage();

    /**
     * @return the arguments consumer or null is none
     */
    @Nullable
    Consumer<Arguments> getArguments();
}
