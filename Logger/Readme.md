# Low models.Level Design — Logger Framework

## Problem Statement

Design and implement a production-grade logging framework that can be used as a library by any application.

---

## Functional Requirements

### 1. Log Levels
- Support log levels: `DEBUG` < `INFO` < `WARN` < `ERROR` < `FATAL` (in increasing order of severity)
- Each log message must be tagged with exactly one level
- The framework must support a **configurable minimum log level** — messages below the minimum must be silently dropped before any processing

### 2. Log Destinations (Sinks)
- Support multiple output destinations: `Console`, `File`, `Database`
- Each destination must have its **own independently configured minimum log level**
    - Example: Console accepts `INFO` and above, File accepts `WARN` and above, S3 accepts everything from `DEBUG`
- A single log message may be written to multiple destinations
- New destinations must be addable **without modifying existing code**

### 3. Log Message Format
- Every log entry must include:
    - Timestamp
    - Log level
    - Thread / source identifier
    - Message content
- Format: `[2024-01-01 10:00:00] [INFO] [Thread-1] :: User logged in`
- Format must be configurable per sink

### 4. Logger Interface
```java
Logger logger = Logger.getInstance();

logger.debug("message");
logger.info("message");
logger.warn("message");
logger.error("message");
logger.fatal("message");
```

---

## Non-Functional Requirements

### Thread Safety
- Multiple threads must be able to log concurrently without data corruption
- No log message should be lost due to a race condition
- Log ordering must be consistent within a single thread

### Singleton
- Only one instance of `Logger` should exist across the entire application

### Extensibility (Open-Closed Principle)
- Adding a new log level → no modification to existing classes
- Adding a new sink (Kafka, Slack, S3) → no modification to existing classes
- Adding a new filter (rate limiting, PII redaction, sampling) → no modification to existing classes

### Performance
- Logging must not significantly block the caller thread
- Async logging via an internal queue is preferred for high-throughput scenarios
- Unnecessary processing (formatting a message that will be dropped) must be avoided

---

## Expected Components

| Component | Responsibility |
|---|---|
| `LogLevel` | Enum with severity ordering |
| `LogMessage` | Data class: timestamp, level, thread, message |
| `LogSink` / `LogAppender` | Interface for output destinations |
| `ConsoleAppender` | Writes to stdout |
| `FileAppender` | Writes to a file |
| `DatabaseAppender` | Writes to a database |
| `Logger` | Singleton entry point — exposes `debug/info/warn/error/fatal` |
| `LoggerConfig` | Holds configurable min level and registered sinks |
| `LogFormatter` | Formats `LogMessage` into a string |
| `AsyncLogQueue` | Bounded blocking queue for async logging (bonus) |

---

## Design Decisions to Discuss

### Pattern Choice
- **Strategy** — for formatting (each sink can have its own formatter)
- **Observer / per-sink filtering** — each sink independently decides based on its own min level whether to process an incoming message
- **CoR** — justified only if you add pipeline stages that can transform or gate messages (e.g. PII redaction → rate limiter → sink). Not needed for simple level-based dispatch.

### Sync vs Async Logging
- **Sync**: caller thread writes directly to sink. Simple but blocks on slow sinks (file I/O, DB writes)
- **Async**: caller thread puts message on a `BlockingQueue`. Background writer thread drains the queue and writes to sinks. Caller never blocks.

### Queue Overflow Policy (Async mode)
- `offer()` — drop the message silently if queue is full (low latency, possible loss)
- `put()` — block the caller until space is available (no loss, backpressure)
- Overflow handler — route overflow messages to a fallback sink (e.g. stderr)

---

## Concurrency Design (Async Queue)

```
Caller Threads          Background Writer Thread
──────────────          ────────────────────────
logger.info()  ──┐
logger.error() ──┼──► BlockingQueue<LogMessage> ──► Sink 1 (Console)
logger.debug() ──┘       (bounded, e.g. 1000)    ──► Sink 2 (File)
                                                  ──► Sink 3 (Database)
```

**Queue implementation:** `LinkedBlockingQueue` (two separate locks for head and tail — producers and consumers don't block each other unless queue is full or empty)

---

## Follow-up Questions (Almost Always Asked)

1. **Async logging** — "How would you make logging non-blocking for the caller?"
    - Bounded `BlockingQueue` + background writer thread draining it

2. **Queue overflow** — "What if the File sink is slow and logs back up?"
    - Choose between drop (`offer`), backpressure (`put`), or overflow handler

3. **Rate limiting** — "Only allow 100 ERROR logs per second"
    - Per-sink filter with a token bucket or sliding window counter

4. **PII redaction** — "Mask emails and phone numbers before writing"
    - Message transformer stage before reaching the sink

5. **Thread interleaving** — "Can two thread messages mix mid-write?"
    - Sink `write()` must be synchronized or use a channel that guarantees atomic writes

6. **Structured logging** — "Support JSON output alongside plain text"
    - Configurable `LogFormatter` per sink (`JsonFormatter`, `PlainTextFormatter`)

7. **Logger hierarchy** — "Support package-level loggers like Log4j"
    - Parent-child logger chain with level inheritance

---

## Time Expectation

| Time | Expected Output |
|---|---|
| 0–10 min | Requirements clarification, component identification |
| 10–25 min | Core implementation: LogLevel, LogMessage, LogSink, Logger singleton |
| 25–35 min | Multiple sinks with independent min levels, thread-safe writes |
| 35–45 min | Async queue (bonus), demo/main class |

**Minimum viable submission:** Working Logger with Console + File sinks, configurable min level per sink, thread-safe, and a `main()` demonstrating it.

---

## Key Interview Signals

| Signal | What It Shows |
|---|---|
| Per-sink min level (not global) | You understand heterogeneous configuration |
| `while` not `if` around `await()` | You know spurious wakeups |
| Bounded queue with overflow policy | You think about production failure modes |
| Try-finally around lock release | You write correct concurrent code |
| Asking "sync or async?" before coding | You clarify requirements before designing |