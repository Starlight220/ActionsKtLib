package com.github.starlight220.actions

internal val IDENTITY: (String) -> String = { it }

/**
 * GitHub Actions doesn't like multiline strings, so this escapes the newlines into something that
 * GH Actions accepts.
 *
 * Source: https://github.community/t/set-output-truncates-multiline-strings/16852/5
 */
@Deprecated("It seems that this issue has been fixed", ReplaceWith("this"))
public fun String.escaped(): String = this
//    if (IS_LOCAL) this else replace("%", "%25").replace("\n", "%0A").replace("\r", "%0D")

/** @see [escaped] */
@Deprecated("This is a workaround for a bug that was fixed.", ReplaceWith("println(str)"))
public fun printlnEscaped(str: String) {
    println(str)
}
