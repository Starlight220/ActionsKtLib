package com.github.starlight220.actions

import com.github.starlight220.actions.raw.setOutput
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property Delegate type for Action Outputs.
 *
 * @param name the property name. Defaults to the property name.
 * @param mapper a function that converts the object to a String. Defaults to [toString].
 *
 * If the property name is identical to the output name and the property is a String; use the
 * Companion object for a cleaner usage syntax.
 */
public open class Output<T>(
    private val name: String,
    private val mapper: (T) -> String = { it.toString() }
) : ReadWriteProperty<Any, T> {
    private var value: T? = null

    public companion object : ReadWriteProperty<Any, String> {
        private val outputs: MutableMap<String, String> = mutableMapOf()

        @Throws(UnsupportedOperationException::class)
        override fun getValue(thisRef: Any, property: KProperty<*>): String =
            outputs[property.name]
                ?: throw UnsupportedOperationException(
                    "Reading from an output isn't supported, especially if it's unset."
                )

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            val key = property.name
            outputs[key] = value
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
        setOutput(name, mapper(value))
    }
}
