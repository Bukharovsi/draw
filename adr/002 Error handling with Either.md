Error handling with Either (experiment)

arrow.kt Either look awesome for typical business logic. Instead of ugly 
```
try {
 something
} catch (e: Exception) {
    handle
}
```

we can use procedural style 
```
Something
    .todo()
    .map { it -> process() }
    .map { it -> process2() }
    .fold(
        ifLeft = errorr handling,
        ifRight = business processing
    )
```

NOTES
 - need to take a look how this app will look with Either
 - need to assess if it is useful for the app 
 - Fully supported by Kotlin oop-functional paradigm