# HIVE EXAMPLE <!-- title: Your Title --> <!-- omit from toc --> 

Click [here](https://phatla.github.io/devops_execution_history/Hive/documentation.html) to back to Document page <!-- omit from toc -->
---
- [**PREQUISITE:**](#prequisite)
- [**1. Building an ETL Workflow in Hive**](#1-building-an-etl-workflow-in-hive)
  - [**🔹 Step 1: Extract - Load Raw Data into Hive**](#-step-1-extract---load-raw-data-into-hive)
    - [**1.1 Create a Raw Data Table**](#11-create-a-raw-data-table)
    - [**1.2 Upload the Raw Data to HDFS**](#12-upload-the-raw-data-to-hdfs)
  - [**🔹 Step 2: Transform - Clean and Enrich the Data**](#-step-2-transform---clean-and-enrich-the-data)
    - [**2.1 Create a Processed Table**](#21-create-a-processed-table)
  - [**🔹 Step 3: Load - Store Aggregated Data**](#-step-3-load---store-aggregated-data)
    - [**3.1 Create a Summary Table**](#31-create-a-summary-table)
    - [**3.2 Query the Processed Data**](#32-query-the-processed-data)
  - [**🔹 Automating the Workflow**](#-automating-the-workflow)
    - [**ETL Script (`etl.hql`)**](#etl-script-etlhql)
    - [**Run the ETL Process**](#run-the-etl-process)
- [**2. Preparing Data for Machine Learning in Hive**](#2-preparing-data-for-machine-learning-in-hive)
  - [**🔹 Step 1: Create a Raw Dataset**](#-step-1-create-a-raw-dataset)
    - [**1.1 Create a Raw Table**](#11-create-a-raw-table)
    - [**1.2 Load Sample Data**](#12-load-sample-data)
  - [**🔹 Step 2: Data Preprocessing**](#-step-2-data-preprocessing)
    - [**2.1 Handle Missing Values**](#21-handle-missing-values)
    - [**2.2 Normalize Numeric Features**](#22-normalize-numeric-features)
    - [**2.3 Convert Categorical Data to Numeric (One-Hot Encoding)**](#23-convert-categorical-data-to-numeric-one-hot-encoding)
  - [**🔹 Step 3: Feature Engineering**](#-step-3-feature-engineering)
    - [**3.1 Create a ‘Days Since Last Purchase’ Feature**](#31-create-a-days-since-last-purchase-feature)
    - [**3.2 Create an Aggregated Table for ML Training**](#32-create-an-aggregated-table-for-ml-training)
  - [**🔹 Step 4: Export Data for Machine Learning**](#-step-4-export-data-for-machine-learning)
    - [**4.1 Save as CSV**](#41-save-as-csv)
    - [**4.2 Load into Pandas for ML (Python Example)**](#42-load-into-pandas-for-ml-python-example)
- [**3. Security and Access Control in Hive**](#3-security-and-access-control-in-hive)
  - [**🔹 Step 1: Authentication in Hive**](#-step-1-authentication-in-hive)
    - [**1.1 Enable Kerberos Authentication**](#11-enable-kerberos-authentication)
  - [**🔹 Step 2: Authorization in Hive**](#-step-2-authorization-in-hive)
    - [**2.1 Using SQL Standard Authorization**](#21-using-sql-standard-authorization)
      - [**Grant Permissions**](#grant-permissions)
      - [**Revoke Permissions**](#revoke-permissions)
      - [**Create and Assign Roles**](#create-and-assign-roles)
  - [**🔹 Step 3: Row-Level Security (Data Masking)**](#-step-3-row-level-security-data-masking)
    - [**3.1 Masking Sensitive Data (e.g., Customer Emails)**](#31-masking-sensitive-data-eg-customer-emails)
  - [**🔹 Step 4: Encrypting Data in Hive**](#-step-4-encrypting-data-in-hive)
    - [**4.1 Configure HDFS Encryption Zone**](#41-configure-hdfs-encryption-zone)
    - [**4.2 Store Hive Table in an Encrypted Zone**](#42-store-hive-table-in-an-encrypted-zone)
  - [**🔹 Step 5: Auditing and Monitoring**](#-step-5-auditing-and-monitoring)
  - [**🔹 Summary of Security Measures**](#-summary-of-security-measures)
  - [**🔹 Next Steps**](#-next-steps)

---

# **PREQUISITE:**

- Hive server (hive-metastore, hiveserver2, thrift, beelline, MySQL or Derby) up and running 
- Hadoop (Yarn, MapReduce, hdfs) up and running.
- Hive is connected to Hadoop cluster (remote or local)

---

# **1. Building an ETL Workflow in Hive**  

Now, let’s build a **complete ETL (Extract, Transform, Load) workflow** using Hive. This will involve:  

1. **Extracting raw data** (CSV files in HDFS).  
2. **Transforming data** (cleaning, enriching, and aggregating).  
3. **Loading the processed data** into a final Hive table.  

---

## **🔹 Step 1: Extract - Load Raw Data into Hive**  
We'll assume we have a raw CSV file with sales transactions stored in HDFS.

### **1.1 Create a Raw Data Table**  
This table will store unprocessed sales data.  

```sql
CREATE TABLE sales_raw (
    transaction_id INT,
    customer_id INT,
    product STRING,
    category STRING,
    price FLOAT,
    quantity INT,
    purchase_date STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
```

### **1.2 Upload the Raw Data to HDFS**  
Prepare a CSV file (`sales_data.csv`):

```
1,101,Laptop,Electronics,1200.50,1,2024-02-20
2,102,Keyboard,Electronics,50.00,2,2024-02-21
3,103,Shoes,Fashion,80.00,1,2024-02-22
4,104,Tablet,Electronics,300.00,1,2024-02-22
5,105,T-Shirt,Fashion,25.00,3,2024-02-23
```

Upload it to HDFS:
```bash
hdfs dfs -mkdir -p /user/hive/warehouse/sales_raw
hdfs dfs -put sales_data.csv /user/hive/warehouse/sales_raw/
```

Load the data into Hive:
```sql
LOAD DATA INPATH '/user/hive/warehouse/sales_raw/sales_data.csv' INTO TABLE sales_raw;
```

---

## **🔹 Step 2: Transform - Clean and Enrich the Data**  

### **2.1 Create a Processed Table**  
We’ll create a new table with additional columns:  
- **total_price** (`price * quantity`)  
- **purchase_year** and **purchase_month** extracted from `purchase_date`  

```sql
CREATE TABLE sales_processed AS
SELECT 
    transaction_id, 
    customer_id, 
    product, 
    category, 
    price, 
    quantity, 
    price * quantity AS total_price,
    TO_DATE(purchase_date) AS purchase_date,
    YEAR(TO_DATE(purchase_date)) AS purchase_year,
    MONTH(TO_DATE(purchase_date)) AS purchase_month
FROM sales_raw;
```

---

## **🔹 Step 3: Load - Store Aggregated Data**  
Now that we have cleaned and structured data, we can aggregate it for reporting.

### **3.1 Create a Summary Table**
```sql
CREATE TABLE sales_summary AS
SELECT 
    category, 
    SUM(total_price) AS total_revenue, 
    COUNT(transaction_id) AS total_transactions, 
    AVG(price) AS avg_price,
    COUNT(DISTINCT customer_id) AS unique_customers
FROM sales_processed
GROUP BY category;
```

### **3.2 Query the Processed Data**
- **View the transformed data**:
  ```sql
  SELECT * FROM sales_processed LIMIT 10;
  ```
- **Check total revenue per category**:
  ```sql
  SELECT * FROM sales_summary;
  ```

---

## **🔹 Automating the Workflow**
To automate this ETL workflow, we can:  
1. **Write Hive queries in a script (`etl.hql`)**  
2. **Execute the script using `hive -f`**  
3. **Schedule it with Apache Oozie or a cron job**

### **ETL Script (`etl.hql`)**
```sql
LOAD DATA INPATH '/user/hive/warehouse/sales_raw/sales_data.csv' INTO TABLE sales_raw;

CREATE TABLE IF NOT EXISTS sales_processed AS
SELECT 
    transaction_id, customer_id, product, category, price, quantity, 
    price * quantity AS total_price, TO_DATE(purchase_date) AS purchase_date,
    YEAR(TO_DATE(purchase_date)) AS purchase_year, MONTH(TO_DATE(purchase_date)) AS purchase_month
FROM sales_raw;

CREATE TABLE IF NOT EXISTS sales_summary AS
SELECT 
    category, SUM(total_price) AS total_revenue, 
    COUNT(transaction_id) AS total_transactions, 
    AVG(price) AS avg_price, 
    COUNT(DISTINCT customer_id) AS unique_customers
FROM sales_processed
GROUP BY category;
```

### **Run the ETL Process**
```bash
hive -f etl.hql
```

---

# **2. Preparing Data for Machine Learning in Hive**  

Before running machine learning (ML) models, data must be cleaned, transformed, and structured properly. In Hive, we can:  
✅ **Preprocess raw data** (handle missing values, normalize, and encode categorical data).  
✅ **Feature engineering** (create new features and extract insights).  
✅ **Export data for ML training** (to Apache Spark, Python, or other ML frameworks).  

---

## **🔹 Step 1: Create a Raw Dataset**  
Let’s assume we have customer transaction data stored in Hive, and we want to prepare it for predicting customer purchase behavior.

### **1.1 Create a Raw Table**
```sql
CREATE TABLE customer_transactions (
    customer_id INT,
    age INT,
    gender STRING,
    annual_income FLOAT,
    spending_score INT,
    transaction_count INT,
    last_purchase_date STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
```

### **1.2 Load Sample Data**
```sql
INSERT INTO customer_transactions VALUES
(1, 25, 'Male', 40000, 75, 15, '2024-01-10'),
(2, 34, 'Female', 65000, 90, 30, '2024-02-12'),
(3, 45, 'Male', 70000, 60, 22, '2024-01-25'),
(4, 23, 'Female', 35000, 80, 10, '2024-02-05'),
(5, 39, 'Male', 52000, 70, 25, '2024-01-20');
```

---

## **🔹 Step 2: Data Preprocessing**  
### **2.1 Handle Missing Values**
```sql
SELECT * FROM customer_transactions WHERE annual_income IS NULL OR spending_score IS NULL;
```
- To replace NULL values with the **average income**:
  ```sql
  SELECT AVG(annual_income) FROM customer_transactions;
  ```
  ```sql
  UPDATE customer_transactions 
  SET annual_income = (SELECT AVG(annual_income) FROM customer_transactions)
  WHERE annual_income IS NULL;
  ```

### **2.2 Normalize Numeric Features**
ML models work better with scaled data. We use **min-max scaling**:  
\[
\text{normalized_value} = \frac{(x - \text{min})}{(\text{max} - \text{min})}
\]

```sql
SELECT 
    customer_id, 
    (age - MIN(age) OVER()) / (MAX(age) OVER() - MIN(age) OVER()) AS age_scaled, 
    (annual_income - MIN(annual_income) OVER()) / (MAX(annual_income) OVER() - MIN(annual_income) OVER()) AS income_scaled,
    (spending_score - MIN(spending_score) OVER()) / (MAX(spending_score) OVER() - MIN(spending_score) OVER()) AS spending_scaled
FROM customer_transactions;
```

### **2.3 Convert Categorical Data to Numeric (One-Hot Encoding)**
ML algorithms require categorical features (e.g., `gender`) to be converted into numeric values.
```sql
SELECT customer_id,
       CASE WHEN gender = 'Male' THEN 1 ELSE 0 END AS gender_male,
       CASE WHEN gender = 'Female' THEN 1 ELSE 0 END AS gender_female
FROM customer_transactions;
```

---

## **🔹 Step 3: Feature Engineering**
### **3.1 Create a ‘Days Since Last Purchase’ Feature**
```sql
SELECT customer_id, 
       DATEDIFF(CURRENT_DATE, TO_DATE(last_purchase_date)) AS days_since_last_purchase
FROM customer_transactions;
```

### **3.2 Create an Aggregated Table for ML Training**
```sql
CREATE TABLE customer_features AS
SELECT 
    customer_id, 
    (age - MIN(age) OVER()) / (MAX(age) OVER() - MIN(age) OVER()) AS age_scaled, 
    (annual_income - MIN(annual_income) OVER()) / (MAX(annual_income) OVER() - MIN(annual_income) OVER()) AS income_scaled,
    (spending_score - MIN(spending_score) OVER()) / (MAX(spending_score) OVER() - MIN(spending_score) OVER()) AS spending_scaled,
    transaction_count,
    DATEDIFF(CURRENT_DATE, TO_DATE(last_purchase_date)) AS days_since_last_purchase,
    CASE WHEN gender = 'Male' THEN 1 ELSE 0 END AS gender_male,
    CASE WHEN gender = 'Female' THEN 1 ELSE 0 END AS gender_female
FROM customer_transactions;
```

---

## **🔹 Step 4: Export Data for Machine Learning**
Now that we have preprocessed data, we can **export it for training in Python, Spark ML, or TensorFlow.**

### **4.1 Save as CSV**
```sql
INSERT OVERWRITE DIRECTORY '/user/hive/warehouse/ml_dataset'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT * FROM customer_features;
```

### **4.2 Load into Pandas for ML (Python Example)**
```python
from pyspark.sql import SparkSession

spark = SparkSession.builder.appName("ML_Prep").getOrCreate()
df = spark.read.csv("hdfs:///user/hive/warehouse/ml_dataset", header=True, inferSchema=True)
df.show()
```

---

# **3. Security and Access Control in Hive**  

When working with Hive in a **multi-user** environment, it's crucial to enforce **proper security measures** to control access to data. This involves:  

✅ **Authentication** (Who can access Hive?)  
✅ **Authorization** (What can they do?)  
✅ **Data Protection** (Encryption & masking)  

---

## **🔹 Step 1: Authentication in Hive**  
Hive supports different authentication mechanisms:  
1. **Simple Authentication** (default, no security)  
2. **Kerberos Authentication** (recommended for secure clusters)  
3. **LDAP Authentication** (for enterprise directory integration)  
4. **Delegation Tokens** (for secure Hadoop jobs)  

### **1.1 Enable Kerberos Authentication**  
If your Hadoop cluster is secured with **Kerberos**, you can enable Kerberos for Hive by configuring `hive-site.xml`:  

```xml
<property>
  <name>hive.server2.authentication</name>
  <value>KERBEROS</value>
</property>

<property>
  <name>hive.server2.authentication.kerberos.principal</name>
  <value>hive/_HOST@EXAMPLE.COM</value>
</property>

<property>
  <name>hive.server2.authentication.kerberos.keytab</name>
  <value>/etc/security/keytabs/hive.service.keytab</value>
</property>
```

To access Hive with Kerberos:
```bash
kinit -kt /etc/security/keytabs/hive.user.keytab hive-user@EXAMPLE.COM
beeline -u "jdbc:hive2://hive-server:10000/default;principal=hive/_HOST@EXAMPLE.COM"
```

---

## **🔹 Step 2: Authorization in Hive**  
Hive provides **role-based access control (RBAC)** and **fine-grained permissions** through:  
- **SQL Standard Authorization**  
- **Apache Ranger (for centralized security management)**  

### **2.1 Using SQL Standard Authorization**
Enable SQL authorization in `hive-site.xml`:
```xml
<property>
  <name>hive.security.authorization.enabled</name>
  <value>true</value>
</property>

<property>
  <name>hive.security.authorization.manager</name>
  <value>org.apache.hadoop.hive.ql.security.authorization.plugin.sqlstd.SQLStdHiveAuthorizerFactory</value>
</property>
```

Now, a **Hive administrator** (`hive` user) can manage permissions.

#### **Grant Permissions**
Example: Allow `user1` to read a specific table.
```sql
GRANT SELECT ON TABLE sales_data TO USER user1;
```

#### **Revoke Permissions**
```sql
REVOKE SELECT ON TABLE sales_data FROM USER user1;
```

#### **Create and Assign Roles**
```sql
CREATE ROLE analyst;
GRANT SELECT ON TABLE sales_data TO ROLE analyst;
GRANT ROLE analyst TO USER user1;
```

---

## **🔹 Step 3: Row-Level Security (Data Masking)**  
Hive allows **row-based filtering** or **column masking** using **Apache Ranger**.

### **3.1 Masking Sensitive Data (e.g., Customer Emails)**
If a table contains personal emails:
```sql
SELECT email FROM customers;
```
To mask it:
```sql
CREATE VIEW masked_customers AS
SELECT 
    customer_id, 
    REGEXP_REPLACE(email, '(.{2}).+(@.+)', '\\1****\\2') AS masked_email
FROM customers;
```

Now, restricted users will see masked emails:
```
customer_id | masked_email
------------+----------------
1           | jo****@gmail.com
2           | ma****@yahoo.com
```

---

## **🔹 Step 4: Encrypting Data in Hive**  
To **encrypt Hive data**, enable **HDFS Transparent Encryption**.

### **4.1 Configure HDFS Encryption Zone**
```bash
hdfs crypto -createZone -keyName hive_key -path /user/hive/warehouse/secure_data
```

### **4.2 Store Hive Table in an Encrypted Zone**
```sql
CREATE TABLE secure_transactions (
    transaction_id INT, 
    card_number STRING, 
    amount FLOAT
)
LOCATION '/user/hive/warehouse/secure_data';
```

Now, **only authorized users** with encryption key access can read this data.

---

## **🔹 Step 5: Auditing and Monitoring**  
To **track user actions**, enable **Hive Audit Logging** in `hive-site.xml`:

```xml
<property>
  <name>hive.exec.submit.audit</name>
  <value>true</value>
</property>
```

For advanced auditing, integrate **Apache Ranger** or **Cloudera Navigator**.

---

## **🔹 Summary of Security Measures**  
| **Feature**        | **Implementation** |
|--------------------|------------------|
| **Authentication** | Kerberos, LDAP, or delegation tokens |
| **Authorization**  | SQL-based roles & permissions, Apache Ranger |
| **Data Masking**   | Regex-based masking, Apache Ranger policies |
| **Encryption**     | HDFS Transparent Encryption for sensitive data |
| **Auditing**       | Hive audit logs, Apache Ranger |

---

## **🔹 Next Steps**
✅ **Integration with Spark and Other Tools**  
✅ **Set up Apache Ranger for advanced security**  
✅ **Implement a real-world security use case in Hive**