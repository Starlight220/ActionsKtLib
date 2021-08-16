package com.github.starlight220.test
import com.github.starlight220.actions.*
import kotlinx.serialization.Serializable

fun main() {
    Environment.loadRaw<EmptyEnvironment>("{}")


    TestMultilineOutput()
}

@Serializable
private data class EmptyEnvironment(private val __ignored: String = "") : Environment

class TestMultilineOutput {
    init {
        setOutput("multiline", "Line 0\nLine 1\nLine 2")
    }
}
