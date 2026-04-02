# Toy Language Interpreter

An interpreter for a custom imperative programming language, built in Java. Supports static type checking, heap memory with garbage collection, file I/O, and multithreaded execution via `fork`. Includes both a CLI interface and an interactive JavaFX debugger.

## Features

- **Type system** — static type checking before execution; types: `int`, `bool`, `string`, `Ref<T>` (nested references supported)
- **Heap memory** — dynamic allocation via `new`, heap reads/writes via `rH`/`wH`
- **Garbage collector** — mark-sweep GC that transitively follows reference chains across all active threads
- **Concurrency** — `fork` statement spawns child threads sharing the heap and output but with isolated execution stacks and symbol tables; threads are executed concurrently via `ExecutorService`
- **File I/O** — open, read, and close files at runtime
- **Control flow** — `if/else`, `while`
- **Interactive debugger** — JavaFX GUI with single-step execution, live inspection of heap, symbol table, execution stack, output, and file table per thread

## Language Example

```
// Allocate a reference, fork a child thread that modifies shared heap
Ref int a;
new(a, 22);
fork(
    wH(a, 30);
    print(rH(a))    // prints 30
);
print(rH(a))        // may print 22 or 30 depending on scheduling
```

## Architecture

```
src/
├── adt/          # Custom data structures: Stack, Dictionary, Heap, List (with interfaces)
├── model/
│   ├── types/        # IntType, BoolType, StringType, RefType
│   ├── values/       # IntValue, BoolValue, StringValue, RefValue
│   ├── expressions/  # Arithmetic, logical, relational, heap-read expressions
│   └── statements/   # Assign, If, While, Fork, New, wH, File I/O, Print, ...
├── state/        # ProgramState: execution stack, symbol table, heap, file table, output
├── controller/   # Execution engine + garbage collector
├── repo/         # State repository and execution logging
├── examples/     # 10 built-in example programs
├── view/         # CLI text menu
└── gui/          # JavaFX debugger (GuiMain, MainWindow)
```

## Running

### Requirements
- Java 17+
- JavaFX SDK (set `--module-path` to your JavaFX lib directory)

### CLI
Run `Main.java` — select an example program from the text menu.

### GUI Debugger
Run `GuiMain.java` with VM args:
```
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```
Select a program, then step through execution one instruction at a time.

## Example Programs

| # | Description |
|---|---|
| 1 | Variable declaration and print |
| 2 | Arithmetic expressions |
| 3 | Boolean + if/else |
| 4 | File open/read/close |
| 5–6 | Heap allocation and nested references (`Ref Ref int`) |
| 7 | Heap write (`wH`) |
| 8 | Aliasing with heap reallocation |
| 9 | While loop |
| 10 | Fork with shared heap and concurrent execution |
