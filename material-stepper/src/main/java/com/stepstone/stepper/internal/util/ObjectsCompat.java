package com.stepstone.stepper.internal.util;

import android.support.annotation.Nullable;

/**
 * This class consists of {@code static} utility methods for operating
 * on objects.
 *
 * This backports {@link java.util.Objects} which is available since API 19.
 *
 * @author Piotr Zawadzki
 */
public final class ObjectsCompat {

    private ObjectsCompat() {
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     * Consequently, if both arguments are {@code null}, {@code true}
     * is returned and if exactly one argument is {@code null}, {@code
     * false} is returned.  Otherwise, equality is determined by using
     * the {@link Object#equals equals} method of the first
     * argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     * @see Object#equals(Object)
     */
    @SuppressWarnings("PMD.SuspiciousEqualsMethodName")
    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
