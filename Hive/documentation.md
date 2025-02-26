# HIVE DOCUMENTATION<!-- title: Your Title -->

**Author:** Pham Tung Lam  
**Date Released:** February 11, 2025

- [HIVE DOCUMENTATION](#hive-documentation)
  - [1. Hive Introduction:](#1-hive-introduction)
  - [2. Hive architecture overview:](#2-hive-architecture-overview)
    - [High-Level Design Architecture of Hive:](#high-level-design-architecture-of-hive)
    - [Detailed Low-Level Design Architecture of Hive:](#detailed-low-level-design-architecture-of-hive)
  - [3. **How exactly that Hive work?**](#3-how-exactly-that-hive-work)
    - [3.1. **Hive Metastore**:](#31-hive-metastore)
      - [**Metadata Objects**](#metadata-objects)
      - [**Mechanicism**](#mechanicism)
      - [**Benefits**](#benefits)
      - [**Criticism**](#criticism)
    - [**3.2. Hive Query Language**](#32-hive-query-language)
    - [**3.3. Compiler**](#33-compiler)
    - [**3.4. Optimizer**](#34-optimizer)
    - [**3.5. Execution**:](#35-execution)

## 1. Hive Introduction:
Hive is a data warehouse infrastructure tool built on top of Hadoop for providing data summarization, query, and analysis. It facilitates reading, writing, and managing large datasets residing in distributed storage using SQL. Hive abstracts the complexity of Hadoop MapReduce by providing a simple SQL-like language called HiveQL.

### Main Components of Hive: <!-- omit from toc -->
- **Metastore**: Central repository for storing metadata information.
- **Driver**: Manages the lifecycle of a HiveQL statement.
- **Compiler**: Compiles HiveQL into a directed acyclic graph of MapReduce jobs.
- **Execution Engine**: Executes the tasks produced by the compiler.
- **HiveServer2**: Provides a Thrift interface and JDBC/ODBC drivers for client interactions.

### Main Purpose: <!-- omit from toc -->
Hive is designed to enable easy data summarization, ad-hoc querying, and analysis of large volumes of data. It is particularly useful for data warehousing tasks where SQL-based querying is preferred over complex MapReduce programming.

## 2. Hive architecture overview:

### High-Level Design Architecture of Hive:

Hive's architecture is designed to provide a high-level abstraction over Hadoop's MapReduce framework. The main components of Hive's architecture include:

1. **User Interface**: Allows users to submit queries and other operations to the system.
2. **MetaStore**: Stores metadata about tables, columns, partitions, and the schema.
3. **HiveQL Process Engine**: Parses, compiles, and optimizes HiveQL queries.
4. **Execution Engine**: Executes the execution plans created by the HiveQL process engine.
5. **HDFS**: Hadoop Distributed File System, where the actual data resides.
6. **MapReduce**: The underlying framework used for processing the data.

[![](https://img.plantuml.biz/plantuml/svg/JP31IWCn48RlUOgXzts38WMxPGiL5D6Bz30cixkXQR9c9beGtzr9es8EWSdt9zzFDYoOfPo3MT2US29O3Vld7eR3TWN8CFjtWay3kbwUc3Bier0UqTAnuk8R6ZJsS83xd2mnGnyd7rjYVWiBjL1_8Ll4f_YVKuCE1L-HLNz7WYmfqzL_bco-ZB4X5QVLqAMqm6qeh2rQGMT_-mtRtMDpkf7hhUAXS-aZTZS_DQxxtvcgIQT0e-XKJVMnTPVzD4jNyOQYqx_v1W00)](https://editor.plantuml.com/uml/JP31IWCn48RlUOgXzts38WMxPGiL5D6Bz30cixkXQR9c9beGtzr9es8EWSdt9zzFDYoOfPo3MT2US29O3Vld7eR3TWN8CFjtWay3kbwUc3Bier0UqTAnuk8R6ZJsS83xd2mnGnyd7rjYVWiBjL1_8Ll4f_YVKuCE1L-HLNz7WYmfqzL_bco-ZB4X5QVLqAMqm6qeh2rQGMT_-mtRtMDpkf7hhUAXS-aZTZS_DQxxtvcgIQT0e-XKJVMnTPVzD4jNyOQYqx_v1W00)

This high-level architecture allows Hive to efficiently manage and process large datasets by leveraging the distributed computing power of Hadoop.

### Detailed Low-Level Design Architecture of Hive:

The low-level design of Hive delves into the intricate details of how each component interacts and functions to provide a seamless data warehousing solution. The key components involved in the low-level architecture include:

1. **Client Interface**: This includes the command-line interface (CLI), web UI, and JDBC/ODBC drivers that allow users to interact with Hive.
2. **Compiler**: The compiler translates HiveQL statements into a directed acyclic graph (DAG) of MapReduce jobs. It performs semantic analysis and query optimization.
3. **Optimizer**: The optimizer improves the execution plan by applying various optimization techniques such as predicate pushdown, join optimization, and partition pruning.
4. **Planner**: The planner generates the physical execution plan from the optimized logical plan.
5. **Executor**: The executor is responsible for executing the physical plan by interacting with the Hadoop MapReduce framework.
6. **Metastore**: The metastore is a central repository that stores metadata about the data, such as schema information, table definitions, and partition details.
7. **SerDe**: Serializer/Deserializer (SerDe) is used for reading and writing data in a specific format.
8. **File Formats**: Hive supports various file formats such as TextFile, SequenceFile, ORC, and Parquet for storing data in HDFS.

[![](https://img.plantuml.biz/plantuml/svg/RLBRIWCn47tFLmpxldyWKko5LYfMIl51VCXcfsqqcuGagrBflpj9rasL1Z8mvmpdp4moyO6xq7UACQ5CtqArvAqn5gQgzm5T1Tp3iHNV38RpifmjjgyPtdjsPinoySuF2DLHVc05kNG4TnuTh3HfxRaOwQGqNQ-Ia52oWC_Ox3ByYKr_GTpFxgO9VsiRaU3pNwSbsJ-jOUECGEzXhWzI_pCqdPKANNQrgZ2FDiXEdWRAs70gt4PnhGV6AdtDUlw5eW_IwD6LQX4llNE9oJ98IQ6sw6QOS8yj5dn1lSB2k8u7d-Xz5oxSMnvumptPFs3WdhIpR_UR3VRd-4ZnVINKzMsQEwrspE92nuGkr4uUZe60j8WO2XGtG3VLfp5YA7IQ88O2nQvp4ILZSun-5TpKDQs9iGdgbZxX3m00)](https://editor.plantuml.com/uml/RLBRIWCn47tFLmpxldyWKko5LYfMIl51VCXcfsqqcuGagrBflpj9rasL1Z8mvmpdp4moyO6xq7UACQ5CtqArvAqn5gQgzm5T1Tp3iHNV38RpifmjjgyPtdjsPinoySuF2DLHVc05kNG4TnuTh3HfxRaOwQGqNQ-Ia52oWC_Ox3ByYKr_GTpFxgO9VsiRaU3pNwSbsJ-jOUECGEzXhWzI_pCqdPKANNQrgZ2FDiXEdWRAs70gt4PnhGV6AdtDUlw5eW_IwD6LQX4llNE9oJ98IQ6sw6QOS8yj5dn1lSB2k8u7d-Xz5oxSMnvumptPFs3WdhIpR_UR3VRd-4ZnVINKzMsQEwrspE92nuGkr4uUZe60j8WO2XGtG3VLfp5YA7IQ88O2nQvp4ILZSun-5TpKDQs9iGdgbZxX3m00)

This detailed low-level architecture highlights the flow of data and the interaction between various components within Hive. It ensures efficient query processing, optimization, and execution, leveraging the distributed nature of Hadoop.

## 3. **How exactly that Hive work?**

![](https://hive.apache.org/attachments/27362072/47743299.png)

### 3.1. **Hive Metastore**:

Hive Metastore, part of Apache Hive, is a distributed, fault-tolerant data warehouse system for large-scale analytics. It stores meta-information about data storages, allowing you to query everything from one place using Hive's HSQL dialect. Despite its name, Hive Metastore is independent from Hive.
<br>
The Metastore in Hive provides data abstraction and data discovery. Data abstraction allows users to reuse information about data formats, extractors, and loaders during table creation. Data discovery helps users find and explore specific data in the warehouse. Hive's metadata repository ensures data and metadata are synchronized, enabling efficient query processing.

![](https://blog.jetbrains.com/wp-content/uploads/2022/06/image-40.png)

#### **Metadata Objects**
- Database – is a namespace for tables. It can be used as an administrative unit in the future. The database ‘default’ is used for tables with no user-supplied database name.
- Table – Metadata for a table contains list of columns, owner, storage and SerDe information. It can also contain any user-supplied key and value data. Storage information includes location of the underlying data, file inout and output formats and bucketing information. SerDe metadata includes the implementation class of serializer and deserializer and any supporting information required by the implementation. All of this information can be provided during creation of the table.
- Partition – Each partition can have its own columns and SerDe and storage information. This facilitates schema changes without affecting older partitions.

#### **Mechanicism**

Hive Metastore maps data to a simple relational structure, storing information about data locations in a metastore database (usually MySQL, Postgres, or Derby). The database schema is fluid and changes over time. The Metastore Thrift server, the main entry point for clients, uses the Thrift RPC protocol, preferred over gRPC for several reasons:

1. It has typed exceptions. So you don’t just get a random exception from inside your RPC, but rather you can actually understand what’s going wrong.
2. It has a rich standard library (if a set of predefined types can be called that).
3. Like gRPC, it supports many languages, but in my opinion, Thrift’s generator produces much nicer code than gRPC’s generator does.

The Thrift protocol, developed by Facebook for its Big Data ecosystem, is ideal for Hive and other ecosystems. The Thrift server, a simple application with APIs, provides information about data sources in Hive Metastore. It supports both typed and dynamically typed languages like Python. All clients, including Hive, communicate only with the Thrift server, which can be easily set up with a single Docker container for experiments.

#### **Benefits**

Using Hive Metastore in this way provides four main benefits related to:

1. **Virtualization**:

    Data analysts prefer to focus on their tables rather than the details of object storage. This need makes Hive Metastore essential, as it supports critical analytic workflows. New technologies ensure compatibility with Hive Metastore to maintain these workflows.

2. **Discoverability**

    Hive Metastore acts as a catalog for collections in object storage, enabling data discovery when updated with new data. It can also store supplemental information like update frequency and ownership.

3. **Schema Evolution**

    Managing mutable data sets over time can be challenging due to changes in records or schema. Hive Metastore records the schema for each data file, allowing access with the appropriate schema. This helps validate schema consistency and alert users to changes.

4. **Performance**

    Since Hive Metastore maps the table to the underlying object, it allows the representation of a relational database as partitions according to the primary key supported by the object storage. The granularity of the partitions can be set by the user, and if partitions are balanced and their number is reasonable, this mapping allows improvement in query performance.

#### **Criticism**

1. **Scaling Thrift**: Thrift, built on top of HTTP, works with many popular tools like HAProxy. However, understanding random Thrift traffic messages can be challenging.
2. **Metastore as a Thin Layer**: Large Hive tables can cause issues with Metastore due to Hive's partitioning and relational database limitations. Metastore can be used with other tools and without partitioning if needed.
3. **Leaky Abstractions**: Metastore may have more leaks than other abstractions, but this can sometimes be an opportunity for fine-tuning when you know what you're doing.

### **3.2. Hive Query Language**

- HiveQL is an SQL-like query language for Hive.
- Mimics SQL syntax for creating tables, loading data, and querying tables.
- Allows embedding custom map-reduce scripts in any language using a row-based streaming interface.
- Custom scripts read rows from standard input and write rows to standard output.
- Flexibility of using any language comes with a performance hit due to row conversion.
- Users accept the performance hit for the flexibility of language choice.
- **Unique feature**: multi-table insert, allowing multiple queries on the same input data with a single HiveQL query.
- Hive optimizes multi-table inserts to share input data scans, increasing throughput significantly.

### **3.3. Compiler**

1. **Parser**

   - Transforms a query string into a parse tree representation.

2. **Semantic Analyser**

   - Converts the parse tree to an internal query representation.
   - Verifies column names and performs expansions like *.
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

### **3.4. Optimizer**

More plan transformations are performed by the optimizer. The optimizer is an evolving component. As of 2011, it was rule-based and performed the following: column pruning and predicate pushdown. However, the infrastructure was in place, and there was work under progress to include other optimizations like map-side join. 
<br>
The optimizer can be enhanced to be cost-based. The sorted nature of output tables can also be preserved and used later on to generate better plans. The query can be performed on a small sample of data to guess the data distribution, which can be used to generate a better plan.

### **3.5. Execution**:

After generating the physical plan, Hive submits the jobs to the Hadoop cluster:

- **Job Submission**: The jobs are submitted to the Hadoop JobTracker (or ResourceManager in YARN) for execution.
- **Task Execution**: Each job is executed as a series of tasks across the cluster:
- **Map Tasks**: Each input split (data partition) is processed by a map task, which applies the specified transformations and outputs intermediate key-value pairs.
- **Shuffle and Sort**: The framework shuffles the intermediate data between the map tasks and sorts it based on the keys.
- **Reduce Tasks**: The sorted data is then processed by reduce tasks, which aggregate the results and produce the final output.

Once all the jobs are completed, the results are saved back to HDFS or another specified output location. The Hive client can then retrieve the results for users to access.