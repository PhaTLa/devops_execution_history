# HIVE INSTALLATION
**Author:** Pham Tung Lam  
**Date Released:** February 05, 2025

## VM Specification
- CPU: 2 cores
- RAM: 4 GB (8 GB recommended)
- Disk: 20 GB free space
- OS: Ubuntu 22.04 LTS
- Java: JDK 8 or higher
- Hadoop: Pre-installed and configured

# Some note:

## Must use this to interact with Hiveserver2 Metadata:

```sh
hive
beeline> !connect jdbc:hive2://
```

## To perform Join in HiveQL:

```sh
beeline> SET hive.auto.convert.join=false;
```