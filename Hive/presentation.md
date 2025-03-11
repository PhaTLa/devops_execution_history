---
marp: true
theme: gaia
color: black
_class: 
 - lead
_backgroundImage: url(https://t4.ftcdn.net/jpg/09/28/41/59/360_F_928415910_OtHgmVFFC20kX8W8fR86GvaOb3GYfFYB.jpg)
backgroundImage: url(https://img.freepik.com/free-vector/geometric-abstract-background-with-connected-dots-lines_1409-1866.jpg)
footer: 'AA subteam - CoE - LGCNS-VNB'
paginate: true
author: Pham Tung Lam
---

![bg left:40% 80%](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmLwqsC_57NdAOf4_80-MA2TztLu_wVncknQ&s)

# **APACHE HIVE** 

###### *Presenter*: Pham Tung Lam (Alex)

---
<!--  _class: lead  -->
# 1. Hive Introduction

---

- Data warehouse infrastructure tool built on Hadoop.
- Provides data summarization, query, and analysis.
- Uses SQL-like language called HiveQL.
- Abstracts complexity of Hadoop MapReduce.
- Supports distributed storage.
- Ideal for large-scale data processing.


---

<!--  _header: 1. Hive Introduction  -->

### Main Purpose

Hive is designed to enable easy data summarization, ad-hoc querying, and analysis of large volumes of data. It is particularly useful for data warehousing tasks where SQL-based querying is preferred over complex MapReduce programming.

---
<!--  _header: 1. Hive Introduction  -->

### Main Components of Hive

- **Metastore**: Central repository for storing metadata information.
- **Driver**: Manages the lifecycle of a HiveQL statement.
- **Compiler**: Compiles HiveQL into a directed acyclic graph of MapReduce jobs.
- **Execution Engine**: Executes the tasks produced by the compiler.
- **HiveServer2**: Provides a Thrift interface and JDBC/ODBC drivers for client interactions.

---
<!--  _class: lead  -->

## 2. Hive Architecture Overview

---

<!--  _header: 2. Hive Architecture Overview  -->

### High-Level Design Architecture of Hive

Hive's architecture is designed to provide a high-level abstraction over Hadoop's MapReduce framework. The main components of Hive's architecture include:

1. **User Interface**: Allows users to submit queries and other operations to the system.
2. **MetaStore**: Stores metadata about tables, columns, partitions, and the schema.

---
<!--  _header: 2. Hive Architecture Overview  -->

### High-Level Design Architecture of Hive

Hive's architecture is designed to provide a high-level abstraction over Hadoop's MapReduce framework. The main components of Hive's architecture include:

3. **HiveQL Process Engine**: Parses, compiles, and optimizes HiveQL queries.
4. **Execution Engine**: Executes the execution plans created by the HiveQL process engine.

---
<!--  _header: 2. Hive Architecture Overview  -->

### High-Level Design Architecture of Hive

Hive's architecture is designed to provide a high-level abstraction over Hadoop's MapReduce framework. The main components of Hive's architecture include:

5. **HDFS**: Hadoop Distributed File System, where the actual data resides.
6. **MapReduce**: The underlying framework used for processing the data.

---

<!--  _header: 2. Hive Architecture Overview  -->


![bg : 60%](https://img.plantuml.biz/plantuml/svg/JP31IWCn48RlUOgXzts38WMxPGiL5D6Bz30cixkXQR9c9beGtzr9es8EWSdt9zzFDYoOfPo3MT2US29O3Vld7eR3TWN8CFjtWay3kbwUc3Bier0UqTAnuk8R6ZJsS83xd2mnGnyd7rjYVWiBjL1_8Ll4f_YVKuCE1L-HLNz7WYmfqzL_bco-ZB4X5QVLqAMqm6qeh2rQGMT_-mtRtMDpkf7hhUAXS-aZTZS_DQxxtvcgIQT0e-XKJVMnTPVzD4jNyOQYqx_v1W00)

---
<!--  _header: 2. Hive Architecture Overview  -->

### Detailed Low-Level Design Architecture of Hive

The low-level design of Hive delves into the intricate details of how each component interacts and functions to provide a seamless data warehousing solution. The key components involved in the low-level architecture include:

1. **Client Interface**: CLI, web UI, and JDBC/ODBC drivers.
2. **Compiler**: Translates HiveQL statements into a DAG of MapReduce jobs.
3. **Optimizer**: Applies optimization techniques like predicate pushdown, join optimization, and partition pruning.

---
<!--  _header: 2. Hive Architecture Overview  -->

### Detailed Low-Level Design Architecture of Hive

The low-level design of Hive delves into the intricate details of how each component interacts and functions to provide a seamless data warehousing solution. The key components involved in the low-level architecture include:

4. **Planner**: Generates the physical execution plan from the optimized logical plan.
5. **Executor**: Executes the physical plan by interacting with the Hadoop MapReduce framework.
6. **Metastore**: Stores metadata about the data, such as schema information, table definitions, and partition details.

---
<!--  _header: 2. Hive Architecture Overview  -->

### Detailed Low-Level Design Architecture of Hive

The low-level design of Hive delves into the intricate details of how each component interacts and functions to provide a seamless data warehousing solution. The key components involved in the low-level architecture include:

7. **SerDe**: Serializer/Deserializer for reading and writing data in a specific format.
8. **File Formats**: Supports various file formats such as TextFile, SequenceFile, ORC, and Parquet.

---
<!--  _header: 2. Hive Architecture Overview  -->

![bg : 60%](https://img.plantuml.biz/plantuml/svg/RLBRIWCn47tFLmpxldyWKko5LYfMIl51VCXcfsqqcuGagrBflpj9rasL1Z8mvmpdp4moyO6xq7UACQ5CtqArvAqn5gQgzm5T1Tp3iHNV38RpifmjjgyPtdjsPinoySuF2DLHVc05kNG4TnuTh3HfxRaOwQGqNQ-Ia52oWC_Ox3ByYKr_GTpFxgO9VsiRaU3pNwSbsJ-jOUECGEzXhWzI_pCqdPKANNQrgZ2FDiXEdWRAs70gt4PnhGV6AdtDUlw5eW_IwD6LQX4llNE9oJ98IQ6sw6QOS8yj5dn1lSB2k8u7d-Xz5oxSMnvumptPFs3WdhIpR_UR3VRd-4ZnVINKzMsQEwrspE92nuGkr4uUZe60j8WO2XGtG3VLfp5YA7IQ88O2nQvp4ILZSun-5TpKDQs9iGdgbZxX3m00)

---
<!-- _class: lead  -->

## 3. How Exactly Does Hive Work?

----
<!--  _header: 3. How Exactly Does Hive Work?  -->

![bg : 70%](https://hive.apache.org/attachments/27362072/47743299.png)

---
<!--  _header: 3. How Exactly Does Hive Work?  -->
### 3.1. Hive Metastore

Hive Metastore, part of Apache Hive, is a distributed, fault-tolerant data warehouse system for large-scale analytics. It stores meta-information about data storages, allowing you to query everything from one place using Hive's HSQL dialect. Despite its name, Hive Metastore is independent from Hive.

---
<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Metadata Objects

- **Database**: Namespace for tables. The default database is used for tables with no user-supplied database name.
- **Table**: Metadata includes columns, owner, storage, and SerDe information. Storage information includes data location, file formats, and bucketing. SerDe metadata includes serializer and deserializer implementation.
- **Partition**: Each partition can have its own columns, SerDe, and storage information, facilitating schema changes without affecting older partitions.

---
<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Mechanism

Hive Metastore maps data to a simple relational structure, storing information about data locations in a metastore database (usually MySQL, Postgres, or Derby). The database schema is fluid and changes over time. 

---

<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Mechanism

The Metastore Thrift server, the main entry point for clients, uses the Thrift RPC protocol, preferred over gRPC for several reasons:

1. Typed exceptions for better error understanding.
2. Rich standard library.
3. Supports many languages with nicer code generation than gRPC.

---
<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Benefits

1. **Virtualization**: Supports critical analytic workflows by focusing on tables rather than object storage details.
2. **Discoverability**: Acts as a catalog for collections in object storage, enabling data discovery and storing supplemental information like update frequency and ownership.

---

<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Benefits


3. **Schema Evolution**: Records schema for each data file, allowing access with the appropriate schema and validating schema consistency.
4. **Performance**: Maps tables to underlying objects, allowing relational database representation as partitions, improving query performance.

---
<!--  _header: 3. How Exactly Does Hive Work?/3.1. Hive Metastore  -->
### Criticism

1. **Scaling Thrift**: Works with many popular tools like HAProxy, but understanding random Thrift traffic messages can be challenging.
2. **Metastore as a Thin Layer**: Large Hive tables can cause issues due to partitioning and relational database limitations. Metastore can be used with other tools and without partitioning if needed.
3. **Leaky Abstractions**: Metastore may have more leaks than other abstractions, but this can sometimes be an opportunity for fine-tuning.

---

### 3.2. Hive Query Language

- SQL-like query language for Hive.
- Mimics SQL syntax for creating tables, loading data, and querying tables.
- Allows embedding custom map-reduce scripts in any language using a row-based streaming interface.
- Custom scripts read rows from standard input and write rows to standard output.
- Flexibility of using any language comes with a performance hit due to row conversion.
- Users accept the performance hit for the flexibility of language choice.
- **Unique feature**: Multi-table insert, allowing multiple queries on the same input data with a single HiveQL query.
- Hive optimizes multi-table inserts to share input data scans, increasing throughput significantly.

---

### 3.3. Compiler

1. **Parser**
   - Transforms a query string into a parse tree representation.

2. **Semantic Analyser**
   - Converts the parse tree to an internal query representation.
   - Verifies column names and performs expansions like `*`.
   - Conducts type-checking and implicit type conversions.
   - Collects expressions for partitioned tables to prune unnecessary partitions.
   - Collects sampling information if specified in the query.

3. **Logical Plan Generator**
   - Converts the internal query representation to a logical plan (tree of operators).
   - Includes relational algebra operators (e.g., filter, join) and Hive-specific operators.
   - Uses a reduceSink operator at the map-reduce boundary.
   - Optimizes the plan for performance (e.g., multi-way joins, map-side partial aggregation, two-stage group-by).

4. **Query Plan Generator**
   - Converts the logical plan to a series of map-reduce tasks.
   - Recursively traverses the operator tree to create map-reduce tasks.
   - Uses the reduceSink operator's descriptor for reduction keys.
   - Includes required samples/partitions if specified in the query.
   - Serializes the plan and writes it to a file for submission to the map-reduce framework.

---

### 3.4. Optimizer

- Performs plan transformations.
- Rule-based optimizer (as of 2011) includes column pruning and predicate pushdown.
- Infrastructure in place for future optimizations like map-side join.
- Can be enhanced to be cost-based.
- Preserves sorted nature of output tables for better plans.
- Uses data sampling to guess data distribution for better plan generation.

---

### 3.5. Execution

After generating the physical plan, Hive submits the jobs to the Hadoop cluster:

- **Job Submission**: Submitted to the Hadoop JobTracker (or ResourceManager in YARN) for execution.
- **Task Execution**: Each job is executed as a series of tasks across the cluster:
  - **Map Tasks**: Processes each input split (data partition) and outputs intermediate key-value pairs.
  - **Shuffle and Sort**: Shuffles and sorts intermediate data based on keys.
  - **Reduce Tasks**: Processes sorted data, aggregates results, and produces final output.

Results are saved back to HDFS or another specified output location. The Hive client can then retrieve the results for users to access.

---

## 4. Example and Demo

Click this [link](https://phatla.github.io/devops_execution_history/Hive/examples.html) to navigate to Examples section.

<!-- TODO: Replace above link to fit with others hosting platform -->