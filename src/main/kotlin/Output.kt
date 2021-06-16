package com.github.starlight.actions

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property Delegate type for Action Outputs.
 *
 * @param name the property name. Defaults to the property name.
 * @param mapper a function that converts the object to a String. Defaults to [toString].
 *
 * If the property name is identical to the input name, and the property is a String; use the
 * Companion object for a cleaner usage syntax.
 */
public open class Output<T>(
    private val name: String? = null,
    private val mapper: (T) -> String = { it.toString() }
) : ReadWriteProperty<Any, T> {
    private var value: T? = null

    public companion object : Output<String>() {
        private val map = mutableMapOf<String, String>()

        @Throws(UnsupportedOperationException::class)
        override fun getValue(thisRef: Any, property: KProperty<*>): String =
            map[property.name]
                ?: throw UnsupportedOperationException(
                    "Reading from an output isn't supported, especially if it's unset."
                )

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            val key = property.name
            map[key] = value
            setOutput(key, value)
        }
    }

    @Throws(UnsupportedOperationException::class)
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        value
            ?: throw UnsupportedOperationException(
                "Reading from an output isn't supported, especially if it's unset."
            )

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
        setOutput(name ?: property.name, mapper(value))
    }
}

/**
 * Set an output for this action.
 *
 * This is a low-level function. Use the [Output] delegate type instead.
 */
public fun setOutput(name: String, value: String) {
    Environment.EnvironmentManager[name] = value
}
