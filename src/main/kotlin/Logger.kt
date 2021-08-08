package com.github.starlight220.actions

public object Logger {
    /** Print a collapsable group of lines to the console. */
    public inline fun group(header: String, content: GroupBuilder.() -> Unit) {
        GroupBuilder(header).apply {
            content()
            flush()
        }
    }

    /** Backing class for [group]. Do not use directly! */
    public class GroupBuilder @PublishedApi internal constructor(private val header: String) {
        private val lines: MutableSet<String> = mutableSetOf()
        public operator fun String.unaryPlus() {
            lines += this
        }

        /**
         * Flushes the group to the console. No need to call this method yourself, it is called by
         * [group].
         */
        public fun flush() {
            println("""::group::${header}""")
            lines.forEach { println(it) }
            lines.clear()
            println("""::endgroup::""")
        }
    }

    public fun debug(str: String) {
        println(str)
    }
}
