package com.github.starlight220.actions.raw

public fun debug(message: String): WorkflowCommand = buildCommand("debug", message)

public fun notice(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
): WorkflowCommand =
    buildCommand(
        "notice",
        message,
        *setOfNotNull(
                "file" to file,
                title?.let { "title" to it },
                line?.let { "line" to it.toString() },
                endLine?.let { "endLine" to it.toString() },
                col?.let { "col" to it.toString() },
                endColumn?.let { "endColumn" to it.toString() },
            )
            .toTypedArray()
    )

public fun warning(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
): WorkflowCommand =
    buildCommand(
        "warning",
        message,
        *setOfNotNull(
                "file" to file,
                title?.let { "title" to it },
                line?.let { "line" to it.toString() },
                endLine?.let { "endLine" to it.toString() },
                col?.let { "col" to it.toString() },
                endColumn?.let { "endColumn" to it.toString() },
            )
            .toTypedArray()
    )

public fun error(
    message: String,
    file: String,
    title: String? = null,
    line: Int? = null,
    endLine: Int? = null,
    col: Int? = null,
    endColumn: Int? = null
): WorkflowCommand =
    buildCommand(
        "error",
        message,
        *setOfNotNull(
                "file" to file,
                title?.let { "title" to it },
                line?.let { "line" to it.toString() },
                endLine?.let { "endLine" to it.toString() },
                col?.let { "col" to it.toString() },
                endColumn?.let { "endColumn" to it.toString() },
            )
            .toTypedArray()
    )

public fun startGroup(title: String): WorkflowCommand = buildCommand("group", title)

public fun endGroup(): WorkflowCommand = buildCommand("endgroup", "")
