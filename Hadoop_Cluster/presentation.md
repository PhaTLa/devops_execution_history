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
![bg left:40% 80%](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR51hA5-3KoTINZ4zqw3725jlZx7KtciWLdFA&s)

# **Hadoop Exploration** 

###### *Presenter*: Pham Tung Lam (Alex)
---
## 1.Introduction:

<div style="display: flex; justify-content: space-between;">
  <div style="width: 48%; text-align: left;">
    <!-- Content for the first column -->
    - Open-source <br>
    - Scalable <br>
    - Distributed storage <br>
  </div>
  <div style="width: 48%; text-align: left;">
    <!-- Content for the second column -->
    - Fault tolerance <br>
    - High availability <br>
    - Large community
  </div>
</div>

---

## 1. Introduction:

Key components:

- **Hadoop Distributed File System (HDFS)**: A distributed file system that stores data across multiple machines.
<br>
- **MapReduce**: A programming model for processing large data sets with a distributed algorithm.

---
## 1. Introduction:

Key components:

- **YARN (Yet Another Resource Negotiator)**: A resource management layer for scheduling and managing cluster resources.
<br>
- **Hadoop Common**: The common utilities and libraries that support other Hadoop modules.

---
### 1.1. Hadoop Distributed File System (HDFS)


[![bg : 60%](https://mermaid.ink/img/pako:eNqdkD0PgkAMhv_KpbMMOqIhUS_GyQUnPYbKVSHheuQ-BmL47x4xyG6X9n3aPEPfUFtNkMPLYd-Iq9wqFql8fHzJWZ5KceyiD-S-q6l066gOrWVxPWwXvL9f0BAn4-7hipJqyxrdIGZaiSwT3jepFeJwlxhwwtm6-ldyXCSbvyVykfAsIdbTACsw5Ay2Ov3oPREFoSFDCvI0anpi7IICxWM6xRhsOXANeXCRVuBsfDWQP7HzKcVeYyDZYvqs-dEe-WbtnMcPgGSAYQ?type=png)](https://mermaid.live/edit#pako:eNqdkD0PgkAMhv_KpbMMOqIhUS_GyQUnPYbKVSHheuQ-BmL47x4xyG6X9n3aPEPfUFtNkMPLYd-Iq9wqFql8fHzJWZ5KceyiD-S-q6l066gOrWVxPWwXvL9f0BAn4-7hipJqyxrdIGZaiSwT3jepFeJwlxhwwtm6-ldyXCSbvyVykfAsIdbTACsw5Ay2Ov3oPREFoSFDCvI0anpi7IICxWM6xRhsOXANeXCRVuBsfDWQP7HzKcVeYyDZYvqs-dEe-WbtnMcPgGSAYQ)

---
### 1.2. MapReduce

![bg : 60%](https://data-flair.training/blogs/wp-content/uploads/sites/2/2016/12/hadoop-mapreduce-data-flow-execution.gif)

---

### 1.2. MapReduce

![bg : 50%](https://media.geeksforgeeks.org/wp-content/uploads/20200621115714/Map-reduce-workflow.png)


---
### 1.3. YARN (Yet another resource negotiator)

![bg : 60%](https://www.researchgate.net/publication/321469501/figure/fig1/AS:900416803962880@1591687503256/Job-execution-process-in-YARN-11.png)

---

### 1.3. YARN (Yet another resource negotiator)

[![bg : 70%](https://mermaid.ink/img/pako:eNqNU8FuwjAM_ZUoZ_YDPSAhttvgANpl6sVL3TZSGpfE0YYQ_z5XpQxaGPQQ1fZ79stLctCGCtSZjrhL6A2-WqgCNLlX8rUQ2Brbgme1dBY9T_MbjJSCwRV4qDBMAYu2ddYAW_J3MWsRcbe4JM9gfVfqi72Ul_l8NDtT2_TVWFbwN7JnjIBCnarK1MI5khQqM0xUTCqyaOnbTEm3RGywspGF_G25Po9WV_t7rtMaK2J7pSj-w79wUbjCLPfdBhwkb-pJiwu0cM8mZ-p9hD-ZPoR3zHv7QZNE6YX3qrtbj45sRd4yhSueWM4pjpi3hj4iP2fzhw_DkUHZrYaa1mHH0jPdYGjAFvJGDl3TXHONDeY6k98CS0iOc537o0AhMW333uiMQ8KZDpSqWmcluChRags5ytMDO2flin8SDfHxFyDhSGA?type=png)](https://mermaid.live/edit#pako:eNqNU8FuwjAM_ZUoZ_YDPSAhttvgANpl6sVL3TZSGpfE0YYQ_z5XpQxaGPQQ1fZ79stLctCGCtSZjrhL6A2-WqgCNLlX8rUQ2Brbgme1dBY9T_MbjJSCwRV4qDBMAYu2ddYAW_J3MWsRcbe4JM9gfVfqi72Ul_l8NDtT2_TVWFbwN7JnjIBCnarK1MI5khQqM0xUTCqyaOnbTEm3RGywspGF_G25Po9WV_t7rtMaK2J7pSj-w79wUbjCLPfdBhwkb-pJiwu0cM8mZ-p9hD-ZPoR3zHv7QZNE6YX3qrtbj45sRd4yhSueWM4pjpi3hj4iP2fzhw_DkUHZrYaa1mHH0jPdYGjAFvJGDl3TXHONDeY6k98CS0iOc537o0AhMW333uiMQ8KZDpSqWmcluChRags5ytMDO2flin8SDfHxFyDhSGA)

---
### 1.4. Hadoop Common
Hadoop Common is: 

- A collection of libraries and utilities that other Hadoop components require.
- Forms the base of the Hadoop framework. 

--- 
### 1.4. Hadoop Common

Responsibilities:

- Supplying source code and documents and a contribution section.

- Performing basic tasks- abstracting the file system, generalizing the operating system, etc.

- Supporting the Hadoop Framework by keeping Java Archive files (JARs) and scripts needed to initiate Hadoop.
---
## 2. Why Hadoop:

**Challenges: Massive Data Volumes**

- Traditional storage systems (like single-node databases or NAS) cannot efficiently store and process petabytes of data.
- Data is constantly growing, coming from IoT, logs, social media, and transaction systems. 

---
## 2. Why Hadoop:

**Hadoop Solution:**

- HDFS **distributes** data across a cluster of commodity hardware.
- Large files are split into **blocks** (default 128MB or 256MB), which are stored across multiple machines.
- This allows for seamless scaling by simply adding more nodes.

---
## 2. Why Hadoop:

**Challenges: Scalability and Cost Efficiency**

- Traditional high-performance storage solutions (SAN, NAS) are expensive and hard to scale.
- Scaling up (vertical scaling) is costly and has physical limitations.

---
## 2. Why Hadoop:

**Hadoop Solution:**

- **Horizontal scaling**: Instead of buying expensive high-end machines, HDFS runs on clusters of **cheap commodity hardware**.
- **Elasticity**: New nodes can be added dynamically to increase storage and processing power.
- **Linear scaling**: Performance improves as more machines are added.

---
## 2. Why Hadoop:

**Challenges: Fault Tolerance and High Availability**

- Hardware failures (disk crashes, power failures) are common in large-scale data systems.
- Traditional storage systems require expensive RAID solutions to prevent data loss.

---
## 2. Why Hadoop:

**Hadoop Solution:**

- **Data replication**: Each block is replicated (default 3 times) across different nodes to prevent data loss.
- **Automatic recovery**: If a DataNode fails, NameNode detects it and replicates missing blocks.
- **Self-healing mechanism**: HDFS continuously monitors and rebalances data.

---
## 2. Why Hadoop:

**Challenges: High-Throughput Data Processing**

- Traditional storage systems are designed for low-latency transactions, but Big Data applications need **high-throughput batch processing**.
- Reading/writing massive datasets from a single storage device is slow.

---
## 2. Why Hadoop:

**Hadoop Solution:**
- **Parallel processing**: Hadoop processes data **where it is stored** using the MapReduce framework.
- **Data locality**: Computation is moved to the DataNodes instead of transferring large amounts of data over the network.
- **Sequential access optimization**: HDFS is optimized for large file reads rather than random access, which is ideal for analytics.

---
## 2. Why Hadoop:

**Challenges: Write-Once, Read-Many Model**
- Big Data workloads involve appending and analyzing massive datasets rather than frequent updates.
- Traditional RDBMS struggle with handling such unstructured and append-heavy data.

---
## 2. Why Hadoop:

**Hadoop Solution:**
- HDFS follows a **write-once, read-many** model, making it ideal for:
  - **Log processing**
  - **Data lakes**
  - **Analytical workloads**
- Since data is never modified in place, it eliminates **file corruption issues**.

---
## 2. Why Hadoop:

**Challenges: Supports Structured, Semi-Structured, and Unstructured Data**
Traditional databases handle structured data (tables, rows, columns) but struggle with semi-structured (JSON, XML) and unstructured (images, videos, logs).

---
## 2. Why Hadoop:

**Hadoop Solution:**
- HDFS stores **any type of file**: text, images, videos, logs, sensor data.
- It supports various processing engines like **Apache Hive (SQL on Hadoop), Spark**, and **Flink** for analysis.

---
## 2. Why Hadoop:

**Challenges: Integration with Big Data Ecosystem**
- Organizations need to process data using **AI/ML, analytics, and real-time processing**.
- Traditional storage lacks integration with modern data processing frameworks.

---
## 2. Why Hadoop:

**Hadoop Solution:**
- HDFS seamlessly integrates with:
  - **Apache Spark, Hive, HBase** for analytics.
  - **Kafka, Flume** for real-time ingestion.
  - **TensorFlow, PyTorch** for AI/ML workloads.

---
## 3. How Hadoop works:

- How Data is Stored in HDFS
-  Metadata in Hadoop Namenode
- Fault Tolerance and Self-Healing

---
### 3.1. How Data is Stored in HDFS
#### a. File to Blocks Mapping
- Files written to HDFS are split into fixed-size blocks (default 128MB or 256MB in modern Hadoop versions).
- Each block is stored across multiple DataNodes.
- Default replication factor is 3, meaning each block is copied to 3 different DataNodes for fault tolerance.
---
### 3.1. How Data is Stored in HDFS
#### b. Block Storage Mechanism
- Blocks are stored as physical files on the local filesystem of DataNodes (e.g., ext4, XFS).
- Each DataNode stores blocks in /data/hdfs/dn/ (default directory on DataNodes).
- Each block has a unique identifier and is immutable (cannot be modified, only read or deleted).
---
### 3.1. How Data is Stored in HDFS
#### c. Replication Strategy
- NameNode ensures fault tolerance by replicating blocks across DataNodes.
- Placement policy:
  - One replica on a local node.
  - One replica on a different rack.
  - Another replica on a different node in the same rack.
- If a DataNode fails, NameNode initiates block replication to maintain the required replication factor.
---
### 3.2. Metadata in Hadoop Namenode
- **FsImage**: Stores a snapshot of the file system's metadata.
- **EditLogs**: Logs all changes (create, delete, update).
- **NameNode** loads FsImage into memory and applies EditLogs at startup.
- **Secondary NameNode** periodically merges EditLogs into FsImage to prevent EditLogs from growing too large.
---
### 3.2. Metadata in Hadoop Namenode

| Data Type      | Stored As          | Location            |
|----------------|--------------------|---------------------|
| File metadata  | FsImage, EditLogs  | NameNode            |
| Data blocks    | Binary files       | DataNodes           |
| Checkpoints    | FsImage snapshots  | Secondary NameNode  |
| Logs           | EditLogs           | NameNode            |

