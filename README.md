# Java Data Structures Projects

A collection of three Java assignments from Western Washington University's
CSCI 241 course. The projects explore tokenization, graph routing, and
data-structure performance using command-line programs and real datasets.

## Projects

### HW1: Iterative Word Discovery

Learns candidate words from character-segmented text by counting adjacent
tokens, calculating bigram scores, merging pairs that pass configurable
thresholds, and comparing the resulting vocabulary with a dictionary.

Entry point: `HW1/src/Program1.java`

### HW2: Multi-Criteria Route Finder

Builds a directed graph from vertex and edge files and uses Dijkstra's
algorithm to find routes optimized for distance, travel time, or cost.

Entry point: `HW2/src/Routes.java`

### HW3: Baby Name Data Structures

Loads U.S. Social Security baby-name data into a binary search tree, hash map,
and array list. The interactive program searches names, shows popular names,
prints alphabetical results, and reports operation timing.

Entry point: `HW3/src/Kickstart.java`

## Requirements

- JDK 8 or newer
- A terminal or Java IDE

No external libraries or build tool are required.

## Compile and Run

Run commands from the relevant homework directory so bundled data files can be
found.

```bash
cd HW1
javac -d out src/*.java
java -cp out Program1 peterpan.txt 10 0.25 peterpan.dictionary
```

```bash
cd HW2
javac -d out src/*.java
java -cp out Routes
```

An example route query is `ATL JFK 1`. The final value selects distance (`1`),
time (`2`), cost (`3`), or all metrics (`4`).

```bash
cd HW3
javac -d out src/*.java
java -cp out Kickstart
```

Choose a year from 1880 through 2015 when prompted.

## Repository Notes

Assignment PDFs, test data, IDE metadata, and compiled class files are retained
as part of the original coursework archive. The source under each `src/`
directory is the important implementation.
