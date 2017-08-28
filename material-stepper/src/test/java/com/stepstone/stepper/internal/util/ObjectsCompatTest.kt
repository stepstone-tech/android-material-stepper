package com.stepstone.stepper.internal.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class ObjectsCompatTest {

    @Test
    fun `Same non null objects should be equal`() {
        //given
        val object1 = Any()
        val object2 = object1

        //when
        val areEqual = ObjectsCompat.equals(object1, object2)

        //then
        assertTrue(areEqual)
    }

    @Test
    fun `null objects should be equal`() {
        //when
        val areEqual = ObjectsCompat.equals(null, null)

        //then
        assertTrue(areEqual)
    }

    @Test
    fun `Same value objects should be equal`() {
        //given
        val object1 = ValueObject(STRING_VALUE)
        val object2 = ValueObject(STRING_VALUE)

        //when
        val areEqual = ObjectsCompat.equals(object1, object2)

        //then
        assertTrue(areEqual)
    }

    @Test
    fun `null object cannot be equal to a non-null object`() {
        //given
        val object1 = ValueObject(STRING_VALUE)
        val object2: Any? = null

        //when
        val areEqual = ObjectsCompat.equals(object1, object2)

        //then
        assertFalse(areEqual)
    }

    @Test
    fun `Non-null object cannot be equal to null`() {
        //given
        val object1: Any? = null
        val object2 = ValueObject(STRING_VALUE)

        //when
        val areEqual = ObjectsCompat.equals(object1, object2)

        //then
        assertFalse(areEqual)
    }

    @Test
    fun `different non-null objects cannot be equal`() {
        //given
        val object1 = ValueObject(STRING_VALUE)
        val object2 = ValueObject(STRING_VALUE2)

        //when
        val areEqual = ObjectsCompat.equals(object1, object2)

        //then
        assertFalse(areEqual)
    }

    private class ValueObject internal constructor(private val value: String?) {

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false

            val that = o as ValueObject?

            return if (value != null) value == that!!.value else that!!.value == null

        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }
    }

    companion object {

        private val STRING_VALUE = "value"

        private val STRING_VALUE2 = "value2"
    }

}