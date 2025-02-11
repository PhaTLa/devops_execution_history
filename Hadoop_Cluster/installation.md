# HADOOP CLUSTER INSTALLATION
**Author:** Pham Tung Lam  
**Date Released:** February 05, 2025

## Terraform Variables

- `var.libvirt_volume_name` = `hadoop-clt`
- `var.number_of_vms` = `1`
- `var.vm_disk_size` = `53687091200`
- `var.vm_memory` = `8192`
- `var.vm_name` = `hadoop`
- `var.vm_vcpu` = `2`

## Create DevOps Users and Copy SSH Public Key

- Use Ansible playbooks: `ssh`, `ssh_key_management`

## Install OpenJDK 8 on All Nodes

- Use Ansible playbook: `install_openjdk`

## Install Hadoop

- Use Ansible playbook: `install_hadoop`
- This playbook will:
    - Download and extract Hadoop.
    - Create necessary Hadoop directories.
    - Set the environment variables in Hadoop's configuration.
    - Format the NameNode on the master node.
    - Start the Hadoop NameNode, MapReduce, and Resource Manager (YARN) on the master node.
    - Start the Hadoop DataNode on the slave nodes.

## Manually Edit `/etc/hosts` File for All Nodes

- Check the log for issues and find out which hostname Hadoop needs for resolution.
- `/etc/hosts` for NameNode and DataNode:

```sh
127.0.0.1 localhost
192.168.122.102 hadoop-master
192.168.122.27 ubuntu
192.168.122.81 hadoop-slaves
```

## Manually check and correct hostname for all node in cluster:

```sh
sudo hostname -b <correct_hostname>
```
This will ensure hadoop can resolve hostname correctly.

## Create User Directory in HDFS

```sh
cd /opt/hadoop-3.4.1
bin/hdfs dfs -mkdir /user/devops
```

## Demo with an Example Job

- First, create an input folder in HDFS:
    User exec: devops

```sh
cd /opt/hadoop-3.4.1
bin/hdfs dfs -mkdir input
```

- Make an input file and put it into HDFS:

```sh
mkdir input
mkdir output
cd input/
echo "Strictly confined Kubernetes makes edge and IoT secure. Learn how MicroK8s" > input.txt

bin/hdfs dfs -put /home/devops/input/input.txt /user/devops/input
```

- Then run the example job:

```sh
bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.4.1.jar wordcount input output
```

This job will run a simple MapReduce process to count the appearance of each word in the file content.

- If the output looks like below, the job is done successfully:

```
2025-02-04 07:40:49,933 INFO mapreduce.Job:  map 100% reduce 100%
2025-02-04 07:40:51,960 INFO mapreduce.Job: Job job_1738654753639_0001 completed successfully
2025-02-04 07:40:52,114 INFO mapreduce.Job: Counters: 54
                File System Counters
                                FILE: Number of bytes read=147
                                FILE: Number of bytes written=619723
                                FILE: Number of read operations=0
                                FILE: Number of large read operations=0
                                FILE: Number of write operations=0
                                HDFS: Number of bytes read=195
                                HDFS: Number of bytes written=97
                                HDFS: Number of read operations=8
                                HDFS: Number of large read operations=0
                                HDFS: Number of write operations=2
                                HDFS: Number of bytes read erasure-coded=0
                Job Counters 
                                Launched map tasks=1
                                Launched reduce tasks=1
                                Data-local map tasks=1
                                Total time spent by all maps in occupied slots (ms)=6239
                                Total time spent by all reduces in occupied slots (ms)=4620
                                Total time spent by all map tasks (ms)=6239
                                Total time spent by all reduce tasks (ms)=4620
                                Total vcore-milliseconds taken by all map tasks=6239
                                Total vcore-milliseconds taken by all reduce tasks=4620
                                Total megabyte-milliseconds taken by all map tasks=6388736
                                Total megabyte-milliseconds taken by all reduce tasks=4730880
                Map-Reduce Framework
                                Map input records=1
                                Map output records=11
                                Map output bytes=119
                                Map output materialized bytes=147
                                Input split bytes=120
                                Combine input records=11
                                Combine output records=11
                                Reduce input groups=11
                                Reduce shuffle bytes=147
                                Reduce input records=11
                                Reduce output records=11
                                Spilled Records=22
                                Shuffled Maps=1
                                Failed Shuffles=0
                                Merged Map outputs=1
                                GC time elapsed (ms)=250
                                CPU time spent (ms)=1770
                                Physical memory (bytes) snapshot=496267264
                                Virtual memory (bytes) snapshot=5084004352
                                Total committed heap usage (bytes)=389545984
                                Peak Map Physical memory (bytes)=253894656
                                Peak Map Virtual memory (bytes)=2539814912
                                Peak Reduce Physical memory (bytes)=242372608
                                Peak Reduce Virtual memory (bytes)=2544189440
                Shuffle Errors
                                BAD_ID=0
                                CONNECTION=0
                                IO_ERROR=0
                                WRONG_LENGTH=0
                                WRONG_MAP=0
                                WRONG_REDUCE=0
                File Input Format Counters 
                                Bytes Read=75
                File Output Format Counters 
                                Bytes Written=97
```

- Check the MapReduce result:

```sh
bin/hdfs dfs -cat /user/devops/output/part-r-00000
```

The result looks like this:

```
IoT     1
Kubernetes      1
Learn   1
MicroK8s        1
Strictly        1
and     1
confined        1
edge    1
how     1
makes   1
secure. 1
```