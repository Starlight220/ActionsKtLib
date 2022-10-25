package com.github.starlight220.actions

import com.github.starlight220.actions.raw.isCI
import com.github.starlight220.actions.raw.isDebug

/**
 * Uses the `GITHUB_ACTIONS` environment variable to determine whether the program is running on a
 * local machine or on a GitHub Actions runner.
 */
public val isCI: Boolean by lazy { isCI() }

/**
 * Uses the `RUNNER_DEBUG` environment variable to determine whether
 * [debug logging](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/enabling-debug-logging)
 * is enabled.
 */
public val isDebug: Boolean by lazy { isDebug() }
