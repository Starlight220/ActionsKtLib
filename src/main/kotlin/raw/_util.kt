package com.github.starlight220.actions.raw

/**
 * A workflow command. NOTE: if a method returns this object, the command was NOT run!! Call
 * [invoke] to run the command!
 */
@JvmInline
public value class WorkflowCommand(@PublishedApi internal val command: String) {
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun invoke() {
        println(command)
    }
}

internal fun buildCommand(
    command: String,
    value: String,
    vararg kwargs: Pair<String, String>
): WorkflowCommand =
    WorkflowCommand(
        buildString {
            append("::$command")
            if (kwargs.isNotEmpty()) {
                val paramString = kwargs.joinToString(separator = ",") { (k, v) -> "$k=$v" }
                append(" ").append(paramString)
            }
            append("::$value")
        }
    )

public fun escapeMultiline(
    name: String,
    value: String,
    delimiter: String = delimiterGenerator()
): String = """
    $name<<$delimiter
    $value
    $delimiter
""".trimIndent()

public var delimiterGenerator: () -> String = { "EOF" }
