package com.github.starlight220.actions.raw

import kotlin.test.Test
import kotlin.test.assertEquals

class PrintCommandTest {

    @Test
    fun debugTest() {
        debug("Set the Octocat variable") shouldBe """::debug::Set the Octocat variable"""
    }

    @Test
    fun noticeTest() {
        notice("Missing semicolon", file = "app.js", line = 1, col = 5, endColumn = 7) shouldBe
            """::notice file=app.js,line=1,col=5,endColumn=7::Missing semicolon"""
    }

    @Test
    fun warningTest() {
        warning("Missing semicolon", file = "app.js", line = 1, col = 5, endColumn = 7) shouldBe
            """::warning file=app.js,line=1,col=5,endColumn=7::Missing semicolon"""
    }

    @Test
    fun error() {
        error("Missing semicolon", file = "app.js", line = 1, col = 5, endColumn = 7) shouldBe
            """::error file=app.js,line=1,col=5,endColumn=7::Missing semicolon"""
    }

    @Test
    fun startGroupTest() {
        startGroup("My title") shouldBe """::group::My title"""
    }

    @Test
    fun endGroupTest() {
        endGroup() shouldBe """::endgroup::"""
    }

    private infix fun WorkflowCommand.shouldBe(string: String) {
        assertEquals(string, this.command)
    }
}
