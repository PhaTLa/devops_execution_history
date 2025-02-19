#!/usr/bin/env python3
import sys
import os

# Get the filename from Hadoop environment
file_name = os.environ.get("map_input_file", "unknown_file").split("/")[-1]

for line in sys.stdin:
    words = line.strip().lower().split()
    for word in words:
        print(f"{word}\t{file_name}")

