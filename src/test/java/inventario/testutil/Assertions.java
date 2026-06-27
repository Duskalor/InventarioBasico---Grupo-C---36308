package inventario.testutil;

public class Assertions {
    public static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) throw new AssertionError(message);
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) throw new AssertionError(message + " Expected=" + expected + " Actual=" + actual);
    }

    public static void assertEquals(String expected, String actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " Expected=" + expected + " Actual=" + actual);
        }
    }

    public static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(message + " Expected=" + expected + " Actual=" + actual);
        }
    }

    public static void assertContains(String value, String expectedPart, String message) {
        if (value == null || !value.contains(expectedPart)) {
            throw new AssertionError(message + " Expected part=" + expectedPart + " Value=" + value);
        }
    }

    public static void assertThrows(Class<? extends Throwable> expected, Runnable code, String message) {
        try {
            code.run();
        } catch (Throwable ex) {
            if (expected.isInstance(ex)) return;
            throw new AssertionError(message + " Expected exception=" + expected.getName() + " Actual=" + ex.getClass().getName());
        }
        throw new AssertionError(message + " Expected exception=" + expected.getName());
    }
}
