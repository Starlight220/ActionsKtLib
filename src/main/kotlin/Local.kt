package com.github.starlight.actions

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.contracts.ExperimentalContracts
import kotlin.properties.Delegates

/** Whether the program is running on a local machine or on a GitHub Actions runner. */
public val IS_LOCAL: Boolean = System.getenv("CI")?.toBooleanStrictOrNull()?.not() ?: true

private const val outputFilePath = "outputs.inspect_rli.json"

/**
 * A base interface for config data classes.
 *
 * Requirements:
 * - the class itself is marked with [Serializable]
 * - all backing fields are of the type [String], optionally marked with [JvmField]
 */
public interface Environment {
    public companion object EnvironmentManager {
        public var instance: Environment by Delegates.notNull()

        private fun <T> T?.notNull(): T = this ?: throw UninitializedPropertyAccessException()

        @JvmStatic
        public inline operator fun <reified T : Environment> invoke(
            raw: String
        ): T {
            instance = Json.decodeFromString<T>(raw)

            return instance as T
        }

        public operator fun get(field: String): String? = instance.notNull()[field]
        public operator fun set(field: String, value: String) {
            instance.notNull()[field] = value
        }
    }

    /**
     * @return this object's value of the field named [field] or null if the value isn't a string
     */
    public operator fun get(field: String): String? =
        this::class.java.fields.first { it.name == field }.get(this) as? String

    public operator fun set(name: String, value: String) {
        val file = File(outputFilePath)
        val list: MutableMap<String, String> =
            if (file.exists()) {
                Json.decodeFromString(file.readText())
            } else {
                mutableMapOf()
            }
        list += name to value
        file.writeText(Json.encodeToString(list))
    }
}
