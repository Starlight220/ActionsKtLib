package com.github.starlight.actions

import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Get an input for this action.
 *
 * This is a low-level function. Use the [Input] delegate type instead.
 * @throws InputMismatchException if no value for [name] exists.
 */
@Throws(InputMismatchException::class)
public fun getInput(name: String): String =
    cache.getOrPut(name) {
        System.getenv("INPUT_${name.uppercase()}").takeUnless { it.isNullOrBlank() }
            ?: Environment[name]
                ?: throw InputMismatchException(
                """
                Input property `${name}` is undeclared or empty.
                Please specify a value for it in your workflow YAML or config JSON.
            """
            )
    }

/**
 * Property Delegate type for Action Inputs.
 *
 * Reads first from the 'INPUT_${[name]}' environment variable, falling back to the [Environment]
 * (which must be initialized before reading any inputs!).
 *
 * @param name the property name. Defaults to the property name.
 * @param mapper a function that converts the object to a String. Defaults to [toString].
 *
 * If the property name is identical to the input name, and the property is a String; use the
 * Companion object for a cleaner usage syntax.
 */
public open class Input<T>(private val name: String? = null, private val mapper: (String) -> T) :
    ReadOnlyProperty<Any, T> {
    public companion object : Input<String>(mapper = IDENTITY) {
        /** Get an [Input] String property delegate with the given name. */
        public operator fun invoke(name: String): Input<String> = Input(name, IDENTITY)
    }

    @Throws(IllegalStateException::class)
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        mapper(getInput(name ?: property.name))
}

internal val cache: MutableMap<String, String> = mutableMapOf()
