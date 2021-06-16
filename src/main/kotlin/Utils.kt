package com.github.starlight.actions

internal val IDENTITY: (String) -> String = { it }

/**
 * GitHub Actions doesn't like multiline strings, so this escapes the newlines into something that
 * GH Actions accepts.
 *
 * Source: https://github.community/t/set-output-truncates-multiline-strings/16852/5
 */
public fun String.escaped(): String = replace("%", "%25").replace("\n", "%0A").replace("\r", "%0D")

/** @see [escaped] */
public fun printlnEscaped(str: String) {
    println(str.escaped())
}
