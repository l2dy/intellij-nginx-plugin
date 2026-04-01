# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

IntelliJ Platform plugin providing nginx configuration file support (syntax highlighting, completion, inspections, documentation, structure view, folding, and Lua language injection for OpenResty).

Plugin ID: `io.github.l2dy.ide.ic.nginx`

## Build Commands

```bash
./gradlew build              # Full build (includes code generation, compile, test)
./gradlew test               # Run all tests
./gradlew runIde             # Launch IDE sandbox with plugin installed

# Run a single test class
./gradlew test --tests "dev.meanmail.lexer.NginxLexerTest"

# Run a single test method
./gradlew test --tests "dev.meanmail.parsing.ParsingTest.testServer"

# Code generation only (usually not needed; runs automatically before compilation)
./gradlew generateNginxLexer   # Nginx.flex -> NginxLexer.java
./gradlew generateNginxParser  # Nginx.bnf -> NginxParser.java + PSI classes
```

## Architecture

### Two-module Gradle project

- **Root project** (`nginx-intellij-plugin`): The IntelliJ plugin — lexer, parser, PSI, all IDE features.
- **`:directive-catalog`** subproject: Pure Kotlin library with no IntelliJ dependencies. Contains the complete catalog of nginx directives, their parameters, allowed contexts, and module metadata.

### Code generation (GrammarKit)

The lexer and parser are **generated** from grammar source files:

- `src/main/kotlin/dev/meanmail/Nginx.flex` → generates `src/main/java/dev/meanmail/NginxLexer.java` (JFlex)
- `src/main/kotlin/dev/meanmail/Nginx.bnf` → generates `src/main/java/dev/meanmail/psi/parser/NginxParser.java` and all PSI interfaces/impls in `src/main/java/dev/meanmail/psi/` (Grammar-Kit)

**Never edit generated Java files under `src/main/java/`** — edit the `.flex` or `.bnf` sources instead, then regenerate.

Generation runs automatically before `JavaCompile` and `KotlinCompile` tasks.

### Directive catalog system

Each nginx module is a Kotlin file in `directive-catalog/src/main/kotlin/dev/meanmail/directives/catalog/nginx/` (or `openresty/`). Core types are in `Common.kt`:

- `Directive` — name, description, parameters, allowed parent contexts, module. On construction, automatically registers itself as a child of its context directives.
- `DirectiveParameter` — typed parameter with validation (ValueType enum, allowedValues, regex, min/max).
- `ToggleDirective` — convenience subclass for on/off directives.
- `NginxModule` — groups directives by nginx module.

Sentinel directives: `main` (top-level context), `any` (valid everywhere), `self` (recursive nesting like `location` in `location`).

Lookup: `findDirectives(name, path)` returns directives matching a name, optionally filtered by a nesting path.

New directive modules must be added to the `initDirectives()` function in `Common.kt` to be included in `Directive.standardDirectives`.

### PSI layer

Generated PSI interfaces live in `src/main/java/dev/meanmail/psi/`, implementations in `psi/impl/`. Hand-written PSI base classes are in `src/main/kotlin/dev/meanmail/psi/`:

- `DirectiveStmtElement` — base for directive statements; provides `getName()`, `getNameIdentifier()`, and `path` (full nesting path).
- `Reference` / `ReferenceElement` — file reference resolution for `include` directives.
- `WithPathElement` — interface for elements that expose their directive nesting path.

### Key plugin components (all registered in `plugin.xml`)

| Component | Class | Purpose |
|-----------|-------|---------|
| Completion | `NginxCompletionContributor` | Context-aware directive name completion using catalog |
| Inspections | `NginxDirectiveInspection`, `NginxGeoInspection` | Validates directive names, contexts, and geo block semantics |
| Documentation | `NginxDocumentationProvider` | Renders directive docs from catalog data |
| Lua injection | `NginxLuaInjector` | Injects Lua language into `*_by_lua_block` content |
| Structure view | `NginxStructureViewElement` | Tree with special rendering for server, location, etc. |

### Lexer states

The JFlex lexer uses a stack-based state machine with 15+ states. Key ones: `DIRECTIVE_STATE`, `IF_PAREN_STATE`, `LUA_BLOCK_STATE`/`LUA_STATE`, `MAP_STATE`/`MAP_BLOCK_STATE`, `GEO_STATE`/`GEO_BLOCK_STATE`, `STRING_STATE`/`DQSTRING_STATE`. The synthetic `CONCAT_JOIN` token represents adjacent tokens with no whitespace (for variable/string concatenation).

## Testing

Tests use JUnit 4 with IntelliJ test infrastructure:

- **Lexer tests** (`NginxLexerTest`): Direct lexer instantiation, verifies `(tokenType, tokenText)` pairs.
- **Parser tests** (`ParsingTest`): File pairs in `src/test/resources/dev/meanmail/parsing/testData/` — `Xxx.nginx.conf` input + `Xxx.txt` expected AST. Method name `testXxx` maps to file name `Xxx`.
- **Completion tests** (`NginxCompletionContributorTest`): Inline config with `<caret>` marker, asserts expected completions.
- **Inspection tests** (`NginxDirectiveInspectionTest`, `NginxGeoInspectionTest`): Fixture `.nginx` files with `<error>` markers for expected diagnostics.

## Debugging Tests

`println()` output from tests is suppressed by default in Gradle. Use `--info` to see it:

```bash
./gradlew test --tests "dev.meanmail.lexer.NginxLexerTest" --info --rerun
```

### Reading actual/expected from failed `checkResult` tests

IntelliJ's `FileComparisonFailedError` stores expected/actual in object fields — Gradle CLI just shows "TEXT". Add a temporary `println` before `checkResult` and run with `--info`:

```kotlin
println("ACTUAL: ${myFixture.editor.document.text.replace("\n", "\\n")}")
myFixture.checkResult(after)
```

### Reading IntelliJ Platform source code

The platform sources JAR is in the Gradle cache (`platformType` and `platformVersion` come from `gradle.properties`):

```bash
# 1. Locate the sources JAR
find ~/.gradle/caches -name "pycharmPC-*-sources.jar" 2>/dev/null

# 2. Search for a class by name
jar tf ~/.gradle/caches/.../pycharmPC-2025.3.2-sources.jar | grep -i "SmartEnter"

# 3. Extract and read a specific source file
unzip -p ~/.gradle/caches/.../pycharmPC-2025.3.2-sources.jar \
    com/intellij/lang/SmartEnterProcessorWithFixers.java

# 4. Inspect a class hierarchy without source (use the non-sources JAR)
javap -cp ~/.gradle/caches/.../pycharmPC-2025.3.2.jar \
    com.intellij.platform.testFramework.core.FileComparisonFailedError
```

## Key Details

- JVM target: 21
- Target platform: PyCharm (`PY`) 2025.3.2, sinceBuild=252
- Optional dependency on `com.tang` (EmmyLua) or `com.cppcxy.Intellij-EmmyLua` (EmmyLua2) for Lua injection support
- Supported file associations: `nginx.conf` filename, `.conf` and `.nginx` extensions
- `CatalogFacade.determineFileContext()` infers the file-level nginx context (http/stream/mail) by analyzing top-level directives
