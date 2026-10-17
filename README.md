# 🎵 Quackify: Linear Data Structures in Java

**A circular-array Deque, an endlessly iterating linked list, and a music-player backend with undo/redo, all built from scratch in Java.**

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk&logoColor=white)
![Data Structures](https://img.shields.io/badge/Topic-Data%20Structures%20%26%20Algorithms-blue)
![Course](https://img.shields.io/badge/Georgia%20Tech-CS%201332-b3a369)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)

---

## 📖 Overview

This project explores linear data structures and how they combine in a real application. I implemented two core structures, then used only those structures to build **Quackify**, a playlist-based music player backend.

| Part | Focus | What it covers |
|------|-------|----------------|
| **1. ArrayDeque** | Arrays | Circular backing array, wraparound indexing, resizing |
| **2. EndlessLinkedList** | Linked lists | Singly linked list with a never-ending, wrapping iterator |
| **3. Quackify** | Application | Playlist management, shuffle, palindrome check, undo/redo |

---

## ✨ Features

### Part 1: Circular ArrayDeque

- **O(1) add and remove at both ends** (`addFirst`, `addLast`, `removeFirst`, `removeLast`)
- **Circular indexing** using a modular helper, so the front and back wrap around the array and reuse free space with no element shifting on removal
- **Resizing** to double capacity when full, "unwrapping" the data so the front returns to index 0
- **Constructor from a linked list:** builds a Deque directly from an `EndlessLinkedList` by iterating its elements in order

### Part 2: EndlessLinkedList

- **Singly linked list with a tail pointer** to support the required time bounds

| Operation | Complexity |
|-----------|------------|
| `addFirst` / `addLast` | O(1) |
| `removeFirst` | O(1) |
| `removeLast` / `removeValue` | O(n) |
| `get(index)` | O(n) |
| `reverse` | O(n) |

- **Wrapping iterator:** reaching the end seamlessly returns to the head (A → B → C → D → E → A → ...), implemented as a private inner class
- **Input validation** with descriptive `IllegalArgumentException` and `NoSuchElementException` messages

### Part 3: Quackify 🦆

A music player that manages a playlist using **only** my own `ArrayDeque` and `EndlessLinkedList` (no built-in collections or plain arrays).

| Feature | Description |
|---------|-------------|
| **Playback** | `play`, `stop`, `isPlaying`, `currentSong`, `nextSong`, backed by the endless iterator so the playlist loops forever |
| **Playlist editing** | `addSong` (newest song on top) and `removeSong` (first occurrence), disabled while music is playing |
| **Reverse** | Reverses the playlist in O(n) time, in place, with no auxiliary space |
| **Shuffle** | `randomSong` jumps to a random track using a seedable `Random`, so behavior is testable |
| **Palindrome check** | `isPalindrome` uses a Deque to compare songs from both ends in O(n) |
| **Undo / Redo** | Tracks add, remove, and reverse operations in order, restores removed songs to their original position, and discards redo history after a new operation |

State handling is strict: invalid actions (for example adding a song mid-playback, or undoing with no history) throw `IllegalStateException`.

---

## 🧠 Highlights

- **Right structure for the job.** The Deque's two-ended access powers both the palindrome check and the undo/redo history, which behaves like a pair of stacks.
- **Circular buffer logic.** Keeping a front index and using modular arithmetic gives constant-time operations at both ends without ever shifting data.
- **Position-preserving undo.** Undoing a removal puts the song back at its original index, which required recording more than just the song name.
- **Constraint-driven design.** Building the application layer from only my own data structures meant every operation had to fit within the structures' real time and space guarantees.
- **Built for testing.** Injectable randomness and a provided UI harness made it easy to verify behavior incrementally.

---

## 🛠️ Tech Stack

- **Language:** Java
- **Core structures:** Circular array Deque, singly linked list, custom iterators
- **Testing:** JUnit
- **Style:** Checkstyle-compliant

---

## 📂 Project Structure

```
.
├── src/        # ArrayDeque, EndlessLinkedList, Quackify, and supporting classes
├── tst/        # JUnit tests and Quackify UI test harness
└── README.md
```

---

## 🚀 Getting Started

**Prerequisites:** JDK 17+ (or the version your IDE is configured for) and IntelliJ IDEA (recommended).

```bash
# Clone the repository
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

1. Open the project root in IntelliJ.
2. Mark `src/` as the **Sources Root** and `tst/` as the **Test Sources Root**.
3. Run the JUnit tests in `tst/`, or launch the Quackify UI to try the player interactively.

---

## 🔍 Example

```
Playlist:                 [Bags, Amoeba, North, Alewife, Sofia]

removeSong("North")  →    [Bags, Amoeba, Alewife, Sofia]
addSong("Juna")      →    [Juna, Bags, Amoeba, Alewife, Sofia]
undo()               →    [Bags, Amoeba, Alewife, Sofia]
undo()               →    [Bags, Amoeba, North, Alewife, Sofia]   // "North" restored to its original spot
redo()               →    [Bags, Amoeba, Alewife, Sofia]
reverse()            →    [Sofia, Alewife, Amoeba, Bags]
```

---

## 📚 What I Learned

- How circular buffers work and why modular indexing keeps operations O(1)
- Designing custom iterators, including one that never terminates
- Tracking operation history so undo and redo stay correct, including position-aware restores
- Writing in-place algorithms (like list reversal) with no extra memory
- Respecting API contracts through careful state validation and exception handling

---

## 🎓 Academic Note

This project was completed for **CS 1332: Data Structures & Algorithms** at the Georgia Institute of Technology.

---

## 👤 Author

**Shrey**
Computer Engineering @ Georgia Tech

[LinkedIn](https://www.linkedin.com/in/shrey-patel25) · [GitHub](https://github.com/shreyp3)
