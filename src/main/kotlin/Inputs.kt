package com.github.starlight.actions

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Get an input for this action.
 *
 * This is a low-level function. Use the [Input] delegate type instead.
 */
public fun getInput(name: String): String? =
    if (IS_LOCAL) {
        Environment[name]
    } else {
        System.getenv("INPUT_${name.uppercase()}")
    }

/**
 * Property Delegate type for Action Inputs.
 *
 * @param name the property name. Defaults to the property name.
 * @param mapper a function that converts the object to a String. Defaults to [toString].
 *
 * If the property name is identical to the input name, and the property is a String; use the
 * Companion object for a cleaner usage syntax.
 */
public open class Input<T>(private val name: String? = null, private val mapper: (String) -> T) :
    ReadOnlyProperty<Any, T> {
    public companion object : Input<String>(null, IDENTITY) {
        /** Get an [Input] String property delegate with the given name. */
        public operator fun invoke(name: String): Input<String> = Input(name, IDENTITY)
    }

    @Throws(IllegalStateException::class)
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val key = name ?: property.name
        val value = getInput(key)
        if (value.isNullOrBlank()) {
            throw IllegalStateException(
                """
                Input property `${key}` is undeclared or empty.
                Please specify a value for it in your workflow YAML.
            """.trimIndent()
            )
        }
        return mapper(value)
    }
}
