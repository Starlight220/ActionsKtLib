package com.github.starlight220.actions.raw

import java.io.File
import java.io.FileNotFoundException

/**
 * Uses the `GITHUB_ACTIONS` environment variable to determine whether the program is running on a
 * local machine or on a GitHub Actions runner.
 */
public fun isCI(): Boolean = System.getenv("GITHUB_ACTIONS") == true.toString()

/**
 * Uses the `GITHUB_ACTIONS` environment variable to determine whether the program is running on a
 * local machine or on a GitHub Actions runner.
 */
public fun isDebug(): Boolean = System.getenv("RUNNER_DEBUG") == 1.toString()

/**
 * Raw function for getting an action's input.
 *
 * @param name
 * @return the raw string content of the input, or null if it doesn't exist.
 * @see System.getenv
 */
public fun getInputOrNull(name: String): String? {
    return System.getenv("INPUT_$name")
}

/**
 * Raw function for getting an environment variable.
 *
 * @param name
 * @return the raw string content of the env variable, or null if it doesn't exist.
 * @see System.getenv
 */
public fun getEnvOrNull(name: String): String? {
    return System.getenv(name)
}

internal fun findFile(name: String): Result<File> {
    val path =
        System.getenv(name).takeUnless { it.isEmpty() }
            ?: return Result.failure(NoSuchElementException("Env variable <$name> not found!"))
    val file = File(path)
    return when {
        !file.isFile -> Result.failure(FileNotFoundException("Couldn't find file <$path>"))
        !file.canWrite() -> Result.failure(FileNotFoundException("Can't write to file <$path>"))
        else -> Result.success(file)
    }
}

/**
 * Write an output.
 *
 * @param key the name of the output to set
 * @param value the output value.
 * [Multiline escaping](https://docs.github.com/en/actions/using-workflows/workflow-commands-for-github-actions#multiline-strings)
 * is handled internally.
 */
public fun setOutput(key: String, value: String): Result<Unit> {
    return findOutputFile().mapCatching { file -> write(file, escapeMultiline(key, value)) }
}

/**
 * Write an env variable.
 *
 * @param key the name of the output to set
 * @param value the env value.
 * [Multiline escaping](https://docs.github.com/en/actions/using-workflows/workflow-commands-for-github-actions#multiline-strings)
 * is handled internally.
 */
public fun setEnv(key: String, value: String): Result<Unit> {
    return findEnvFile().mapCatching { file -> write(file, escapeMultiline(key, value)) }
}

/**
 * Set the Markdown [summary] for this step. For more info, see the
 * [docs](https://docs.github.com/en/actions/using-workflows/workflow-commands-for-github-actions#adding-a-job-summary)
 * .
 */
public fun setSummary(summary: String): Result<Unit> {
    return findStepSummaryFile().mapCatching { file -> write(file, summary) }
}

public fun findEnvFile(): Result<File> = findFile("GITHUB_ENV")

public fun findOutputFile(): Result<File> = findFile("GITHUB_OUTPUT")

public fun findStepSummaryFile(): Result<File> = findFile("GITHUB_STEP_SUMMARY")

public fun findPathFile(): Result<File> = findFile("GITHUB_PATH")

internal fun write(file: File, content: String) {
    file.appendText(text = content, charset = Charsets.UTF_8)
}
