package com.github.starlight220.actions

import java.io.File
import kotlin.properties.Delegates
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Uses the `CI` environment variable to determine whether the program is running on a local machine
 * or on a GitHub Actions runner.
 */
public val IS_LOCAL: Boolean = System.getenv("CI")?.toBooleanStrictOrNull()?.not() ?: true

/** All outputs will be written to this file */
public val outputFile: File = File("outputs.actions.json")

/**
 * A base interface for config data classes.
 *
 * Requirements:
 * - the class itself is marked with [Serializable]
 * - all backing fields are of the type [String], optionally marked with [JvmField]
 */
public interface Environment {
    /**
     * Companion object behaving as an [Environment] property delegate. [Input]s are read from this
     * object, so it must be initialized before reading any inputs.
     */
    public companion object EnvironmentManager {
        public var instance: Environment by Delegates.notNull()

        @JvmStatic
        public inline fun <reified T : Environment> loadRaw(raw: String): T {
            instance = Json.decodeFromString<T>(raw)

            return instance as T
        }

        /** Load from a file pointed to by an environment variable. */
        @JvmStatic
        public inline fun <reified T : Environment> loadFrom(envVar: String): T =
            loadRaw(
                File(
                        System.getenv("GITHUB_WORKSPACE").takeUnless { it.isNullOrBlank() },
                        System.getenv(envVar).takeUnless { it.isNullOrBlank() }
                            ?: error("Variable $envVar pointing to config file is empty!")
                    )
                    .readText()
            )

        public operator fun get(field: String): String? = instance[field]
        public operator fun set(field: String, value: String) {
            instance[field] = value
        }
    }

    /**
     * @return this object's value of the field named [field] or null if the value isn't a string
     */
    public operator fun get(field: String): String? =
        this::class.java.fields.first { it.name == field }.get(this) as? String

    public operator fun set(name: String, value: String) {
        Logger.debug("Set `$name` to `$value`")
        if (!IS_LOCAL) {
            println("::set-output name=$name::$value")
            return
        }

        val list: MutableMap<String, String> =
            if (outputFile.exists()) {
                Json.decodeFromString(outputFile.readText())
            } else {
                mutableMapOf()
            }
        list += name to value
        outputFile.writeText(Json.encodeToString(list))
    }
}
