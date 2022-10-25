package com.github.starlight220.actions

import com.github.starlight220.actions.raw.getInputOrNull
import kotlin.jvm.Throws
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public class NoSuchInputException(input: String) :
    NoSuchElementException("Input <$input> not found!")

/**
 * Reads from the 'INPUT_${[name]}' environment variable, throwing if no such an input is defined.
 */
@Throws(NoSuchInputException::class)
public fun getInputOrThrow(name: String): String =
    getInputOrNull(name) ?: throw NoSuchInputException(name)

/**
 * Property Delegate type for Action Inputs.
 *
 * Reads from the 'INPUT_${[name]}' environment variable, throwing if no such an input is defined.
 *
 * @param name the property name. Defaults to the property name.
 * @param mapper a function that converts the object from a String. Defaults to the identity
 * function.
 *
 * If the property name is identical to the input name, and the property is a String; use the
 * Companion object for a cleaner usage syntax.
 */
public open class Input<T>(private val name: String, private val mapper: (String) -> T) :
    ReadOnlyProperty<Any, T> {
    public companion object : ReadOnlyProperty<Any, String> {
        private val inputs: MutableMap<String, String> = mutableMapOf()

        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            val key = property.name
            return inputs.getOrPut(key) { getInputOrNull(key) ?: throw NoSuchInputException(key) }
        }

        /** Get an [Input] String property delegate with the given name. */
        public operator fun invoke(name: String): Input<String> = Input(name) { it }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T = mapper(getInputOrThrow(name))
}
