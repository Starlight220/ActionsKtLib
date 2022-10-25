package com.github.starlight220.actions

import com.github.starlight220.actions.raw.*
import com.github.starlight220.actions.raw.debug as raw_debug
import com.github.starlight220.actions.raw.error as raw_error
import com.github.starlight220.actions.raw.notice as raw_notice
import com.github.starlight220.actions.raw.warning as raw_warning

/** Print a collapsable group of lines to the console. */
public inline fun logGroup(header: String, content: GroupBuilder.() -> Unit) {
    GroupBuilder(header).apply {
        content()
        flush()
    }
}

public fun debug(message: String): Unit = raw_debug(message)()

public fun notice(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
) {
    raw_notice(message, file, title, line, endLine, col, endColumn)()
}

public fun warning(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
) {
    raw_warning(message, file, title, line, endLine, col, endColumn)()
}

public fun error(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
) {
    raw_error(message, file, title, line, endLine, col, endColumn)()
}

/** Backing class for [logGroup]. Do not use directly! */
public class GroupBuilder @PublishedApi internal constructor(private val header: String) {
    private val lines: MutableSet<String> = mutableSetOf()
    public operator fun String.unaryPlus() {
        lines += this
    }

    /**
     * Flushes the group to the console. No need to call this method yourself, it is called by
     * [logGroup].
     */
    public fun flush() {
        startGroup(header)()
        lines.forEach { println(it) }
        lines.clear()
        endGroup()()
    }
}
