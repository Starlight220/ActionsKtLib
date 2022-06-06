# ActionsKtLib
Kotlin Library for GitHub Actions

### Adding the dependency
Add [JitPack](https://jitpack.io/) as a repository:
```gradle
repositories {
  // other repositories first
    
  maven { url 'https://jitpack.io' }
}
```
And then add the dependency:
```gradle
dependencies {
  implementation 'com.github.Starlight220:ActionsKtLib:v1.01'
}
```

### Comparison to similar projects
- [rnett/kotlin-js-action](https://github.com/rnett/kotlin-js-action) is a very similar library, running on Kotlin/JS to wrap the [actions/toolkit](https://github.com/actions/toolkit) JS packages. In comparison, ActionsKtLib runs on Kotlin/JVM, which is a more stable platform and allows native access to the Java standard library.
- [krzema12/github-actions-kotlin-dsl](https://github.com/krzema12/github-actions-kotlin-dsl) provides a Kotlin DSL for writing [workflows](https://docs.github.com/en/actions/using-workflows/about-workflows); ActionsKtLib provides a library to write the actions themselves.
