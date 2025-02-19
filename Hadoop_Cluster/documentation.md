# HADOOP CLUSTER DOCUMENTATION <!-- title: Your Title -->

**Author:** Pham Tung Lam  
**Date Released:** February 11, 2025

- [HADOOP CLUSTER DOCUMENTATION ](#hadoop-cluster-documentation-)
  - [1. Hadoop Introduction:](#1-hadoop-introduction)
  - [2. HDFS Architecture Overview](#2-hdfs-architecture-overview)
    - [a. NameNode (Master)](#a-namenode-master)
    - [b. DataNode (Slave)](#b-datanode-slave)
    - [c. Secondary NameNode](#c-secondary-namenode)
  - [3. How Data is Stored in HDFS](#3-how-data-is-stored-in-hdfs)
    - [a. File to Blocks Mapping](#a-file-to-blocks-mapping)
    - [b. Block Storage Mechanism](#b-block-storage-mechanism)
    - [c. Replication Strategy](#c-replication-strategy)
  - [4. How HDFS Manages Data](#4-how-hdfs-manages-data)
    - [a. Write Process](#a-write-process)
    - [b. Read Process](#b-read-process)
    - [c. Fault Tolerance and Self-Healing](#c-fault-tolerance-and-self-healing)
  - [5. Data Storage](#5-data-storage)
    - [5.1. Metadata (FsImage \& EditLogs)](#51-metadata-fsimage--editlogs)
    - [5.2: HDFS File Types and Storage](#52-hdfs-file-types-and-storage)
  - [6. My Hadoop Cluster](#6-my-hadoop-cluster)
    - [6.1. Topology:](#61-topology)
    - [6.2. Cluster Operation:](#62-cluster-operation)
    - [6.3. What Hadoop Cluster can do:](#63-what-hadoop-cluster-can-do)
    - [6.4. Integration with Hive, Spark, and Oozie:](#64-integration-with-hive-spark-and-oozie)
    - [6.5. Cooperation between Components:](#65-cooperation-between-components)
  - [7. Some example (for Hadoop only):](#7-some-example-for-hadoop-only)
    - [7.1. Storing Data:](#71-storing-data)
    - [7.2. Run MapReduce Job:](#72-run-mapreduce-job)
    - [7.3. Run Custom MapReduce Code:](#73-run-custom-mapreduce-code)
- [Some Advance Example to Try](#some-advance-example-to-try)
  - [**Inverted Index Example in Hadoop (Java \& Python)**](#inverted-index-example-in-hadoop-java--python)
- [**1️⃣ Java Implementation (MapReduce)**](#1️⃣-java-implementation-mapreduce)
    - [**How It Works**](#how-it-works)
    - [**Step 1: Java Code for Inverted Index**](#step-1-java-code-for-inverted-index)
      - [**Mapper Class (Extract Words \& Filenames)**](#mapper-class-extract-words--filenames)
      - [**Reducer Class (Aggregate Files per Word)**](#reducer-class-aggregate-files-per-word)
      - [**Driver Class (Run the Job)**](#driver-class-run-the-job)
    - [**Step 2: Compile and Package**](#step-2-compile-and-package)
    - [**Step 3: Run the Job**](#step-3-run-the-job)
    - [**Sample Input (HDFS `/input/file1.txt` and `/input/file2.txt`)**](#sample-input-hdfs-inputfile1txt-and-inputfile2txt)
    - [**Expected Output (`/output/part-r-00000`)**](#expected-output-outputpart-r-00000)
- [**2️⃣ Python Implementation (Hadoop Streaming)**](#2️⃣-python-implementation-hadoop-streaming)
    - [**Step 1: Python Mapper (`mapper.py`)**](#step-1-python-mapper-mapperpy)
    - [**Step 2: Python Reducer (`reducer.py`)**](#step-2-python-reducer-reducerpy)
    - [**Step 3: Make Scripts Executable**](#step-3-make-scripts-executable)
    - [**Step 4: Run Hadoop Streaming**](#step-4-run-hadoop-streaming)
    - [**Expected Output (`/output/part-00000`)**](#expected-output-outputpart-00000)
  - [**Conclusion**](#conclusion)
  - [**Example 4: Joins in Hadoop (Map-Side Join)**](#example-4-joins-in-hadoop-map-side-join)
  - [**📌 Use Case: Joining User Data and Transaction Data**](#-use-case-joining-user-data-and-transaction-data)
    - [**🔹 Sample Data**](#-sample-data)
      - [**Users (`users.txt`)**](#users-userstxt)
      - [**Transactions (`transactions.txt`)**](#transactions-transactionstxt)
    - [**📌 Expected Output** (Join on `user_id`)](#-expected-output-join-on-user_id)
  - [**📌 Step 1: Upload Data to HDFS**](#-step-1-upload-data-to-hdfs)
  - [**📌 Step 2: Java MapReduce Implementation**](#-step-2-java-mapreduce-implementation)
    - [**1️⃣ Mapper (JoinMapper.java)**](#1️⃣-mapper-joinmapperjava)
    - [**2️⃣ Driver (JoinDriver.java)**](#2️⃣-driver-joindriverjava)
  - [**📌 Step 3: Compile and Package Java Code**](#-step-3-compile-and-package-java-code)
  - [**📌 Step 4: Run Java MapReduce Job**](#-step-4-run-java-mapreduce-job)
  - [**📌 Step 5: Python MapReduce Implementation**](#-step-5-python-mapreduce-implementation)
    - [**1️⃣ Python Mapper (`join_mapper.py`)**](#1️⃣-python-mapper-join_mapperpy)
  - [**📌 Step 6: Run Python MapReduce Job**](#-step-6-run-python-mapreduce-job)
  - [**📌 Expected Output**](#-expected-output)


## 1. Hadoop Introduction:

Apache Hadoop is an open-source software framework used for distributed storage and processing of large data sets using the MapReduce programming model. It is designed to scale up from a single server to thousands of machines, each offering local computation and storage. Hadoop provides a reliable, scalable, and efficient way to handle vast amounts of data, making it a popular choice for big data analytics.

Key components of Hadoop include:
- **Hadoop Distributed File System (HDFS)**: A distributed file system that stores data across multiple machines.
- **MapReduce**: A programming model for processing large data sets with a distributed algorithm.
- **YARN (Yet Another Resource Negotiator)**: A resource management layer for scheduling and managing cluster resources.
- **Hadoop Common**: The common utilities and libraries that support other Hadoop modules.

## 2. HDFS Architecture Overview

HDFS has three main components:

### a. NameNode (Master)
Manages the metadata (file namespace, directory structure, block mapping).
Tracks where each block of a file is stored within the cluster.
Handles file system operations (create, delete, rename, permissions).
Stores metadata in memory for fast access and persists it in the FsImage and EditLogs.
### b. DataNode (Slave)
Stores actual data blocks of files.
Periodically sends heartbeat and block reports to the NameNode.
Performs read/write operations requested by clients.
Handles replication of data blocks to ensure fault tolerance.
### c. Secondary NameNode
It is not a backup for the NameNode but helps by periodically merging the EditLogs into FsImage to reduce metadata load.

## 3. How Data is Stored in HDFS

### a. File to Blocks Mapping
When a file is written to HDFS, it is split into fixed-size blocks (default 128MB or 256MB in modern Hadoop versions).
Each block is stored across multiple DataNodes.
The default replication factor is 3, meaning each block is copied to 3 different DataNodes for fault tolerance.
### b. Block Storage Mechanism
Blocks are stored as physical files on the local filesystem of DataNodes (e.g., ext4, XFS).
Each DataNode stores blocks in /data/hdfs/dn/ (default directory on DataNodes).
Each block has a unique identifier and is immutable (cannot be modified, only read or deleted).
### c. Replication Strategy
NameNode ensures fault tolerance by replicating blocks across DataNodes.
Placement policy:
One replica on a local node.
One replica on a different rack.
Another replica on a different node in the same rack.
If a DataNode fails, NameNode initiates block replication to maintain the required replication factor.

## 4. How HDFS Manages Data

### a. Write Process
1. Client sends a write request to NameNode.
2. NameNode checks permissions and provides block allocation.
3. Client writes data to the first DataNode, which then forwards it to the next DataNode in a pipeline.
4. After successful writing, DataNodes send acknowledgment to the client and NameNode.

```mermaid
sequenceDiagram
    participant Client
    participant NameNode
    participant DataNode1
    participant DataNode2
    participant DataNode3

    Client->>NameNode: Request to write file
    NameNode->>Client: Check permissions and provide block allocation
    Client->>DataNode1: Write data block
    DataNode1->>DataNode2: Forward data block
    DataNode2->>DataNode3: Forward data block
    DataNode1-->>Client: Acknowledge write success
    DataNode2-->>DataNode1: Acknowledge write success
    DataNode3-->>DataNode2: Acknowledge write success
    DataNode1-->>NameNode: Report block write success
    DataNode2-->>NameNode: Report block write success
    DataNode3-->>NameNode: Report block write success
```

### b. Read Process
1. Client requests a file from NameNode.
2. NameNode provides block locations.
3. Client reads data directly from DataNodes in parallel for efficiency.
4. If a DataNode is unavailable, the client fetches from another replica.

```mermaid
sequenceDiagram
    participant Client
    participant NameNode
    participant DataNode1
    participant DataNode2
    participant DataNode3

    Client->>NameNode: Request file
    NameNode->>Client: Provide block locations
    Client->>DataNode1: Read block 1
    Client->>DataNode2: Read block 2
    Client->>DataNode3: Read block 3
    DataNode1-->>Client: Send block 1
    DataNode2-->>Client: Send block 2
    DataNode3-->>Client: Send block 3
    Note over Client: If DataNode1 is unavailable, read from another replica
```

### c. Fault Tolerance and Self-Healing
- **Heartbeat Monitoring**: DataNodes send heartbeats to NameNode every 3 seconds. If a DataNode is unresponsive for 10 minutes, NameNode marks it dead and re-replicates blocks.
- **Rebalancing**: HDFS automatically redistributes blocks to balance storage utilization.
- **Block Corruption Detection**: DataNodes use checksums to verify data integrity.

## 5. Data Storage 

### 5.1. Metadata (FsImage & EditLogs)
*This happen in namenode*
- **FsImage**: Stores a snapshot of the file system's metadata.
- **EditLogs**: Logs all changes (create, delete, update).
- **NameNode** loads FsImage into memory and applies EditLogs at startup.
- **Secondary NameNode** periodically merges EditLogs into FsImage to prevent EditLogs from growing too large.

### 5.2: HDFS File Types and Storage

| Data Type      | Stored As          | Location            |
|----------------|--------------------|---------------------|
| File metadata  | FsImage, EditLogs  | NameNode            |
| Data blocks    | Binary files       | DataNodes           |
| Checkpoints    | FsImage snapshots  | Secondary NameNode  |
| Logs           | EditLogs           | NameNode            |


## 6. My Hadoop Cluster

### 6.1. Topology:

My Hadoop Cluster deployed on QEMU/KVM machines with this following spec:

- **OS**: Ubuntu server 22.04 LTS (cloud-image)
- **Memory**: 8GB
- **vCPU**: 2
- **Disk**: 50GB
- **Number of VM**: 3 (1 Namenode, 2 Datanode)

This is my Cluster topology:

```mermaid
graph TD;
    subgraph Hadoop Cluster
        direction TB;
        A[hadoop-master<br>IP: 192.168.122.102] -- ssh --> B[hadoop-slaves<br>IP: 192.168.122.81];
        A[hadoop-master<br>IP: 192.168.122.102] -- ssh --> C[ubuntu<br>IP: 192.168.122.27];
    end
```

### 6.2. Cluster Operation:

In my Hadoop Cluster, the roles and responsibilities of each node are as follows:

- **hadoop-master**:
    - **ResourceManager**: Manages the allocation of cluster resources and schedules jobs.
    - **NameNode**: Manages the metadata and namespace for the Hadoop Distributed File System (HDFS).
    - **SecondaryNameNode**: Performs periodic checkpoints of the HDFS metadata to prevent loss of data.

- **hadoop-slaves** and **ubuntu**:
    - **DataNode**: Stores the actual data in HDFS and handles read/write requests from clients.
    - **NodeManager**: Manages the execution of individual tasks on each node and monitors resource usage.

The interaction between these components ensures efficient data storage, processing, and resource management across the cluster.

Here is a detailed view of the processes running on each node:

```mermaid
graph TD;
        subgraph Hadoop Cluster
                direction TB;
                A[hadoop-master<br>IP: 192.168.122.102]
                A --> RM[ResourceManager]
                A --> NN[NameNode]
                A --> SNN[SecondaryNameNode]
                
                B[hadoop-slaves<br>IP: 192.168.122.81]
                B --> DN1[DataNode]
                B --> NM1[NodeManager]
                
                C[ubuntu<br>IP: 192.168.122.27]
                C --> DN2[DataNode]
                C --> NM2[NodeManager]
        end
```

### 6.3. What Hadoop Cluster can do:
The Hadoop Cluster described above is capable of efficiently storing and processing large datasets across multiple machines. With its distributed architecture, it can handle vast amounts of data by distributing the storage and computation tasks among the nodes in the cluster. The ResourceManager and NodeManagers ensure optimal resource allocation and job scheduling, while the NameNode and DataNodes manage the storage and retrieval of data in the Hadoop Distributed File System (HDFS). This setup allows for scalable and reliable big data analytics, making it suitable for tasks such as data mining, machine learning, and large-scale data processing.
### 6.4. Integration with Hive, Spark, and Oozie:

The Hadoop Cluster can be extended with additional components such as Hive, Spark, and Oozie to enhance its capabilities and provide a comprehensive big data processing and analytics platform.

#### Hive: <!-- omit from toc -->
Apache Hive is a data warehouse infrastructure built on top of Hadoop. It allows users to query and manage large datasets stored in HDFS using a SQL-like language called HiveQL. Hive translates these queries into MapReduce jobs, making it easier for users to perform data analysis without writing complex MapReduce code.

Capabilities:
- **Data Warehousing**: Store and manage large datasets in a structured format.
- **SQL-like Queries**: Use HiveQL to perform complex queries and data analysis.
- **Integration with HDFS**: Directly query data stored in the Hadoop Distributed File System.

#### Spark: <!-- omit from toc -->
Apache Spark is a fast and general-purpose cluster-computing system that provides an interface for programming entire clusters with implicit data parallelism and fault tolerance. Spark can run on Hadoop clusters and leverage HDFS for storage.

Capabilities:
- **In-Memory Processing**: Perform data processing tasks in memory for faster execution.
- **Advanced Analytics**: Support for machine learning, graph processing, and stream processing.
- **Integration with Hadoop**: Utilize HDFS for data storage and YARN for resource management.

#### Oozie: <!-- omit from toc -->
Apache Oozie is a workflow scheduler system to manage Hadoop jobs. It allows users to define a sequence of jobs written in diverse languages such as MapReduce, Pig, Hive, and Spark, and manage their execution.

Capabilities:
- **Workflow Automation**: Define and manage complex job workflows.
- **Job Scheduling**: Schedule jobs to run at specific times or based on data availability.
- **Integration with Hadoop Ecosystem**: Coordinate jobs across various Hadoop components like Hive, Spark, and MapReduce.

### 6.5. Cooperation between Components:

The integration of Hive, Spark, and Oozie with the Hadoop Cluster enhances its functionality and provides a robust platform for big data processing and analytics. Here is how these components cooperate:

- **Hive and Hadoop**: Hive stores its metadata in a relational database and uses HDFS to store data. Queries written in HiveQL are converted into MapReduce jobs that run on the Hadoop cluster.
- **Spark and Hadoop**: Spark can read data from HDFS, process it in-memory, and write the results back to HDFS. It can also use YARN for resource management, allowing it to coexist with other Hadoop jobs.
- **Oozie and Hadoop**: Oozie workflows can include Hive, Spark, and MapReduce jobs, coordinating their execution and managing dependencies between them. This ensures that complex data processing pipelines can be automated and managed efficiently.

By integrating these components, the Hadoop Cluster becomes a powerful and versatile platform capable of handling a wide range of big data tasks, from data warehousing and SQL querying to advanced analytics and workflow automation.

## 7. Some example (for Hadoop only):

### 7.1. Storing Data:
We can use Hadoop HDFS CLI to interact with file system as any others normal file system.

Example:

- To create directory in HDFS, we use:
```sh
hdfs dfs -mkdir /dir
```
- To list all directory and file in a directory:
```sh
hdfs dfs -ls /dir
```
- Or we can copy a file from local machine to HDFS:
```sh
hdfs dfs -put /local/dir/file.name /hdfs/dir/
```
- And download a copy of hdfs file to local machine:
```sh
hdfs dfs -get /hdfs/dir/file.name /local/dir/
```

### 7.2. Run MapReduce Job:

Hadoop can run MapReduce job, in order to run, hadoop require the input are in hdfs, output directory will be create automatically

1. Prepare input directory:

```sh
hdfs dfs -mkdir /user/username/input
```
- **Syntax**: hdfs dfs -mkdir <directory_path>
- **Function**: Creates a new directory in HDFS at the specified path. Here, it creates the directory `/user/username/input`.

2. Make input file (for example):

```sh
cd /home/username/workspace
echo "Strictly confined Kubernetes makes edge and IoT secure. Learn how MicroK8s" > input.txt
```
- **Syntax**:
  - `cd <directory_path>`: Changes the current directory to the specified path.
  - `echo "<text>" > <file_name>`: Writes the specified text to a file.
- **Function**: Changes the directory to `/home/username/workspace` and creates a file named `input.txt` with the given text.

3. Upload input file to HDFS input directory:
```sh
hdfs dfs -put /home/username/workspace/input.txt /user/username/input/
```
- **Syntax**: `hdfs dfs -put <local_file_path> <hdfs_directory_path>`
- **Function**: Uploads the local file to the specified directory in HDFS. Here, it uploads `input.txt` to `/user/username/input/`.
4. Run sample MapReduce job:

*This example code is shipped along with hadoop installation*
```sh
hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.4.1.jar wordcount input output
```
- **Syntax**: `hadoop jar <jar_file> <class_name> <input_path> <output_path>`
- **Function**: Runs a MapReduce job using the specified JAR file and class. Here, it runs the `wordcount` example using `input` as the input directory and `output` as the output directory.

5. View the result:

You can view the MapReduce job result in a file in `output` directory in hdfs

```sh
hdfs dfs -cat /user/username/output/part-r-00000
```
- **Syntax**: `hdfs dfs -cat <file_path>`
- **Function**: Displays the contents of a file in HDFS. Here, it shows the contents of the file located at `/user/username/output/part-r-00000`.

#### NOTE: Explanation of Output File Name `part-r-00000` <!-- omit from toc -->
In Hadoop MapReduce, the output file name part-r-00000 follows a specific naming convention:

- `part`: Indicates that this file is part of the output from a MapReduce job.
- `r`: Stands for "reduce", indicating that this file is the result of the reduce phase.
- `00000`: A sequence number assigned to the output file. If there are multiple reducers, each reducer will produce a separate output file with a unique sequence number (e.g., part-r-00001, part-r-00002, etc.).

### 7.3. Run Custom MapReduce Code:

Hadoop can also run your custom code as a job.

1. Write a custom Mapreduce code:

Create Java file:

```java name=WordLengthCount.java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordLengthCount {

    public static class TokenizerMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private IntWritable wordLength = new IntWritable();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] words = value.toString().split("\\s+");
            for (String word : words) {
                wordLength.set(word.length());
                context.write(wordLength, one);
            }
        }
    }

    public static class IntSumReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word length count");
        job.setJarByClass(WordLengthCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
```

2. Compile to JAR:

```sh
javac -classpath `hadoop classpath` -d wordlength_classes WordLengthCount.java
jar -cvf wordlength.jar -C wordlength_classes/ .
```

3. Run Job:

```sh
hadoop jar wordlength.jar WordLengthCount /user/username/input /user/username/wordlength_output
```
---

# Some Advance Example to Try

## **Inverted Index Example in Hadoop (Java & Python)**
An **Inverted Index** is a key-value mapping of words to the documents in which they appear. It is widely used in search engines like Google to index web pages efficiently.

---

# **1️⃣ Java Implementation (MapReduce)**
### **How It Works**
1. **Mapper**  
   - Reads an input file (text file).
   - Emits **(word, filename)** pairs.

2. **Reducer**  
   - Aggregates the filenames for each word.
   - Outputs **(word, list of filenames)**.

---

### **Step 1: Java Code for Inverted Index**
Create a new Java project and ensure you have the **Hadoop 3.4.1** dependencies.

#### **Mapper Class (Extract Words & Filenames)**
```java
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;

public class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, Text> {
    private Text word = new Text();
    private Text fileName = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Get filename from context
        String filePath = ((org.apache.hadoop.mapreduce.lib.input.FileSplit) context.getInputSplit()).getPath().getName();
        fileName.set(filePath);

        // Split words by non-alphanumeric characters
        String[] words = value.toString().toLowerCase().split("\\W+");

        for (String w : words) {
            if (!w.isEmpty()) {
                word.set(w);
                context.write(word, fileName);
            }
        }
    }
}
```

---

#### **Reducer Class (Aggregate Files per Word)**
```java
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;
import java.util.HashSet;

public class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> fileSet = new HashSet<>();

        for (Text value : values) {
            fileSet.add(value.toString());
        }

        // Convert set to comma-separated string
        String fileList = String.join(", ", fileSet);
        context.write(key, new Text(fileList));
    }
}
```

---

#### **Driver Class (Run the Job)**
```java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class InvertedIndexDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: InvertedIndex <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Inverted Index");
        
        job.setJarByClass(InvertedIndexDriver.class);
        job.setMapperClass(InvertedIndexMapper.class);
        job.setReducerClass(InvertedIndexReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
```

---

### **Step 2: Compile and Package**
```sh
javac -classpath $(/opt/hadoop-3.4.1/bin/hadoop classpath) -d . InvertedIndexMapper.java InvertedIndexReducer.java InvertedIndexDriver.java

jar cf invertedindex.jar *.class
```

---

### **Step 3: Run the Job**
```sh
hadoop jar invertedindex.jar InvertedIndexDriver /input /output
```
Where `/input` is an HDFS directory containing text files.

---

### **Sample Input (HDFS `/input/file1.txt` and `/input/file2.txt`)**
**file1.txt**  
```
hadoop is great
big data is powerful
```
**file2.txt**  
```
hadoop supports big data
```

### **Expected Output (`/output/part-r-00000`)**
```
big        file1.txt, file2.txt
data       file1.txt, file2.txt
great      file1.txt
hadoop     file1.txt, file2.txt
is         file1.txt
powerful   file1.txt
supports   file2.txt
```

---

# **2️⃣ Python Implementation (Hadoop Streaming)**
Hadoop Streaming allows you to write the Mapper and Reducer in Python.

---

### **Step 1: Python Mapper (`mapper.py`)**
```python
#!/usr/bin/env python3
import sys
import os

# Get the filename from Hadoop environment
file_name = os.environ.get("map_input_file", "unknown_file").split("/")[-1]

for line in sys.stdin:
    words = line.strip().lower().split()
    for word in words:
        print(f"{word}\t{file_name}")
```

---

### **Step 2: Python Reducer (`reducer.py`)**
```python
#!/usr/bin/env python3
import sys

current_word = None
files = set()

for line in sys.stdin:
    word, filename = line.strip().split("\t")

    if current_word == word:
        files.add(filename)
    else:
        if current_word:
            print(f"{current_word}\t{', '.join(files)}")
        current_word = word
        files = {filename}

# Print the last word
if current_word:
    print(f"{current_word}\t{', '.join(files)}")
```

---

### **Step 3: Make Scripts Executable**
```sh
chmod +x mapper.py reducer.py
```

---

### **Step 4: Run Hadoop Streaming**
```sh
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
    -input /input \
    -output /output \
    -mapper mapper.py \
    -reducer reducer.py \
    -file mapper.py \
    -file reducer.py
```

---

### **Expected Output (`/output/part-00000`)**
```
big        file1.txt, file2.txt
data       file1.txt, file2.txt
great      file1.txt
hadoop     file1.txt, file2.txt
is         file1.txt
powerful   file1.txt
supports   file2.txt
```

---

## **Conclusion**
- Java provides **fine-grained control** with the Hadoop MapReduce API.
- Python is **simpler and faster to develop** using Hadoop Streaming.
- Both solutions demonstrate how **HDFS stores data**, **MapReduce processes it**, and **the output is aggregated efficiently**.

| Language | Compilation Required? | Performance |
|----------|-----------------------|-------------|
| Java     | Yes (JAR file)        | Fast        |
| Python   | No (Direct Execution) | Slower      |

- Java is better for production because of better performance.
- Python is easier to write/debug but can be slower for large data.

---
## **Example 4: Joins in Hadoop (Map-Side Join)**  
Map-Side Join is a technique in Hadoop where **one dataset is small enough** to fit in memory, allowing the **Mapper** to perform the join without a Reducer. This is **faster than Reduce-Side Joins** because there is no need for shuffling or sorting.

---

## **📌 Use Case: Joining User Data and Transaction Data**  
We have two datasets:  
1. **Users Dataset (`users.txt`)**: Contains user details.  
2. **Transactions Dataset (`transactions.txt`)**: Contains transaction records.  

### **🔹 Sample Data**
#### **Users (`users.txt`)**  
Format: `user_id, name, age, city`
```
1,Alex,30,New York
2,Bob,25,San Francisco
3,Charlie,28,Los Angeles
```

#### **Transactions (`transactions.txt`)**  
Format: `trans_id, user_id, amount`
```
101,1,200.50
102,2,150.00
103,1,300.00
104,3,450.75
105,2,120.25
```

### **📌 Expected Output** (Join on `user_id`)  
```
101,Alex,200.50
102,Bob,150.00
103,Alex,300.00
104,Charlie,450.75
105,Bob,120.25
```
This output means **each transaction is joined with the corresponding user name**.

---

## **📌 Step 1: Upload Data to HDFS**  
```sh
/opt/hadoop-3.4.1/bin/hadoop fs -mkdir -p /join_data
/opt/hadoop-3.4.1/bin/hadoop fs -put users.txt /join_data/
/opt/hadoop-3.4.1/bin/hadoop fs -put transactions.txt /join_data/
```

---

## **📌 Step 2: Java MapReduce Implementation**  

### **1️⃣ Mapper (JoinMapper.java)**
- Loads the **Users dataset into memory (Distributed Cache)**.
- Reads the **Transactions dataset** and performs a join in the **Mapper**.

```java
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.filecache.DistributedCache;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JoinMapper extends Mapper<Object, Text, Text, Text> {
    private Map<String, String> userMap = new HashMap<>();
    private Text outputKey = new Text();
    private Text outputValue = new Text();

    @Override
    protected void setup(Context context) throws IOException {
        Configuration conf = context.getConfiguration();
        Path[] cacheFiles = context.getLocalCacheFiles(); // Load from Distributed Cache

        if (cacheFiles != null && cacheFiles.length > 0) {
            BufferedReader reader = new BufferedReader(new FileReader(cacheFiles[0].toString()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    userMap.put(parts[0], parts[1]); // user_id -> name
                }
            }
            reader.close();
        }
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split(",");
        if (parts.length == 3) {
            String userId = parts[1];
            String userName = userMap.get(userId);
            if (userName != null) {
                outputKey.set(parts[0]); // trans_id
                outputValue.set(userName + "," + parts[2]); // name, amount
                context.write(outputKey, outputValue);
            }
        }
    }
}
```

---

### **2️⃣ Driver (JoinDriver.java)**
- **Adds `users.txt` to Distributed Cache**.
- Runs the **MapReduce job** with `JoinMapper` only (**no Reducer needed**).

```java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;

public class JoinDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "map-side join");

        job.setJarByClass(JoinDriver.class);
        job.setMapperClass(JoinMapper.class);
        job.setNumReduceTasks(0); // No reducer needed

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0])); // Transactions dataset
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output

        // Add Users dataset to Distributed Cache
        job.addCacheFile(new Path(args[2]).toUri());

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
```

---

## **📌 Step 3: Compile and Package Java Code**
```sh
javac -classpath $(/opt/hadoop-3.4.1/bin/hadoop classpath) -d . JoinMapper.java JoinDriver.java
jar cf join.jar *.class
```

---

## **📌 Step 4: Run Java MapReduce Job**
```sh
/opt/hadoop-3.4.1/bin/hadoop fs -rm -r join_output
/opt/hadoop-3.4.1/bin/hadoop jar join.jar JoinDriver join_data/transactions.txt join_output join_data/users.txt
```

Check the output:
```sh
/opt/hadoop-3.4.1/bin/hadoop fs -cat join_output/part-m-00000
```

---

## **📌 Step 5: Python MapReduce Implementation**
Using **Hadoop Streaming**, we can achieve the same result in Python.

---

### **1️⃣ Python Mapper (`join_mapper.py`)**
Loads the **Users dataset into memory** and performs the join.

```python
#!/usr/bin/env python3
import sys

# Load user data into memory
user_map = {}
with open("users.txt", "r") as f:
    for line in f:
        parts = line.strip().split(",")
        if len(parts) == 4:
            user_map[parts[0]] = parts[1]  # user_id -> name

# Process transactions and join
for line in sys.stdin:
    parts = line.strip().split(",")
    if len(parts) == 3:
        user_id = parts[1]
        if user_id in user_map:
            print(f"{parts[0]}\t{user_map[user_id]},{parts[2]}")
```
Make it executable:
```sh
chmod +x join_mapper.py
```

---

## **📌 Step 6: Run Python MapReduce Job**
```sh
/opt/hadoop-3.4.1/bin/hadoop fs -rm -r /join_output_python

/opt/hadoop-3.4.1/bin/hadoop jar /opt/hadoop-3.4.1/share/hadoop/tools/lib/hadoop-streaming-*.jar \
    -input /join_data/transactions.txt \
    -output /join_output_python \
    -mapper join_mapper.py \
    -file users.txt
```

Check results:
```sh
/opt/hadoop-3.4.1/bin/hadoop fs -cat /join_output_python/part-00000
```

---

## **📌 Expected Output**
Both Java and Python should produce:
```
101,Alex,200.50
102,Bob,150.00
103,Alex,300.00
104,Charlie,450.75
105,Bob,120.25
```

