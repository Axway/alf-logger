package io.axway.alf.assertion;

import java.util.*;
import org.testng.annotations.Test;
import io.axway.alf.exception.IllegalArgumentFormattedException;
import io.axway.alf.exception.IllegalStateFormattedException;
import io.axway.alf.exception.NullPointerFormattedException;

import static io.axway.alf.assertion.Assertion.*;
import static java.util.Collections.*;

public class AssertionTest {
    @Test
    public void testCheckValidArgument() throws Exception {
        checkArgument(true, "Argument validation should not fail");
    }

    @Test(expectedExceptions = IllegalArgumentFormattedException.class, expectedExceptionsMessageRegExp = "Argument validation should fail")
    public void testCheckFailingArgument() throws Exception {
        checkArgument(false, "Argument validation should fail");
    }

    @Test
    public void testCheckValidArgumentWithMessageArguments() throws Exception {
        checkArgument(true, "Argument validation should not fail", a -> a.add("expression", "true"));
    }

    @Test(expectedExceptions = IllegalArgumentFormattedException.class, expectedExceptionsMessageRegExp = "Argument validation should fail \\{\"args\": \\{\"expression\": \"false\"}}")
    public void testCheckFailingArgumentWithMessageArguments() throws Exception {
        checkArgument(false, "Argument validation should fail", a -> a.add("expression", "false"));
    }

    @Test
    public void testCheckValidState() throws Exception {
        checkState(true, "State validation should not fail");
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "State validation should fail")
    public void testCheckFailingState() throws Exception {
        checkState(false, "State validation should fail");
    }

    @Test
    public void testCheckValidStateWithMessageArguments() throws Exception {
        checkState(true, "State validation should not fail", a -> a.add("expression", "true"));
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "State validation should fail \\{\"args\": \\{\"expression\": \"false\"}}")
    public void testCheckFailingStateWithMessageArguments() throws Exception {
        checkState(false, "State validation should fail", a -> a.add("expression", "false"));
    }

    @Test
    public void testCheckNotNull() throws Exception {
        checkNotNull("", "Check not null should not fail");
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null should fail")
    public void testCheckNull() throws Exception {
        checkNotNull(null, "Check not null should fail");
    }

    @Test
    public void testCheckNotNullWithMessageArguments() throws Exception {
        checkNotNull("", "Check not null should not fail", a -> a.add("value", ""));
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null should fail \\{\"args\": \\{\"value\": null}}")
    public void testCheckNullWithMessageArguments() throws Exception {
        checkNotNull(null, "Check not null should fail", a -> a.add("value", null));
    }

    @Test
    public void testCheckNotNullNorEmptyString() throws Exception {
        checkNotNullNorEmpty("test", "Check not null nor empty should not fail");
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail")
    public void testCheckNullString() throws Exception {
        checkNotNullNorEmpty((String) null, "Check not null nor empty should fail");
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail")
    public void testCheckNotNullAndEmptyString() throws Exception {
        checkNotNullNorEmpty("", "Check not null nor empty should fail");
    }

    @Test
    public void testCheckNotNullNorEmptyStringWithMessageArguments() throws Exception {
        checkNotNullNorEmpty("toto", "Check not null  nor empty should not fail", a -> a.add("value", ""));
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail \\{\"args\": \\{\"value\": null}}")
    public void testCheckNullStringWithMessageArguments() throws Exception {
        checkNotNullNorEmpty((String) null, "Check not null nor empty should fail", a -> a.add("value", null));
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail \\{\"args\": \\{\"value\": \"\"}}")
    public void testCheckNotNullButEmptyStringWithMessageArguments() throws Exception {
        checkNotNullNorEmpty("", "Check not null nor empty should fail", a -> a.add("value", ""));
    }

    @Test
    public void testCheckNotNullNorEmptyCollection() throws Exception {
        List<String> collection = new ArrayList<>();
        collection.add("one");
        checkNotNullNorEmpty(collection, "Check not null nor empty should not fail");
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail")
    public void testCheckNullCollection() throws Exception {
        checkNotNullNorEmpty((Collection<?>) null, "Check not null nor empty should fail");
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail")
    public void testCheckNotNullAndEmptyCollection() throws Exception {
        checkNotNullNorEmpty(emptyList(), "Check not null nor empty should fail");
    }

    @Test
    public void testCheckNotNullNorEmptyCollectionWithMessageArguments() throws Exception {
        List<String> collection = new ArrayList<>();
        collection.add("one");
        checkNotNullNorEmpty(collection, "Check not null  nor empty should not fail", a -> a.add("value", ""));
    }

    @Test(expectedExceptions = NullPointerFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail \\{\"args\": \\{\"value\": null}}")
    public void testCheckNullCollectionWithMessageArguments() throws Exception {
        checkNotNullNorEmpty((Collection<?>) null, "Check not null nor empty should fail", a -> a.add("value", null));
    }

    @Test(expectedExceptions = IllegalStateFormattedException.class, expectedExceptionsMessageRegExp = "Check not null nor empty should fail \\{\"args\": \\{\"value\": \\[]}}")
    public void testCheckNotNullButEmptyCollectionWithMessageArguments() throws Exception {
        checkNotNullNorEmpty(emptyList(), "Check not null nor empty should fail", a -> a.add("value", emptyList()));
    }
}
