

# **🔹 Example 1: Verify Spark-Hadoop Integration (HDFS Read/Write)**  
### **📌 Objective:** Ensure Spark can read from and write to HDFS.

### **Step 1: Create a Sample Text File in HDFS**
```bash
echo -e "1,John,25\n2,Emma,30\n3,Liam,28" > /tmp/sample_data.csv
hdfs dfs -mkdir -p /user/spark/input
hdfs dfs -put /tmp/sample_data.csv /user/spark/input/
```

### **Step 2: Read from HDFS using Spark**
Run this in `spark-shell` (Scala):
```scala
val spark = org.apache.spark.sql.SparkSession.builder.appName("HDFS Read Test").getOrCreate()
val df = spark.read.option("header", "false").csv("hdfs:///user/spark/input/sample_data.csv")
df.show()
```

✅ **Expected Output:**
```
+---+-----+---+
| _c0| _c1 | _c2 |
+---+-----+---+
|  1 | John | 25 |
|  2 | Emma | 30 |
|  3 | Liam | 28 |
+---+-----+---+
```

### **Step 3: Write Back to HDFS**
```scala
df.write.mode("overwrite").csv("hdfs:///user/spark/output/")
```
Verify in HDFS:
```bash
hdfs dfs -ls /user/spark/output/
hdfs dfs -cat /user/spark/output/part-00000
```

---

## **🔹 Example 2: Verify Spark-Hive Integration (Query Hive Tables)**
### **📌 Objective:** Ensure Spark can read and write Hive tables.

### **Step 1: Create a Hive Table**
Run in Hive:
```sql
CREATE TABLE IF NOT EXISTS sales_data (
    id INT,
    product STRING,
    price FLOAT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
```

Insert sample data:
```sql
INSERT INTO sales_data VALUES (1, 'Laptop', 1000), (2, 'Phone', 500), (3, 'Tablet', 300);
```

### **Step 2: Query Hive Table Using Spark**
Run in `spark-shell`:
```scala
val spark = org.apache.spark.sql.SparkSession.builder
    .appName("Hive Query Test")
    .enableHiveSupport()
    .getOrCreate()

val hiveDF = spark.sql("SELECT * FROM sales_data")
hiveDF.show()
```

✅ **Expected Output:**
```
+---+--------+------+
| id| product| price|
+---+--------+------+
|  1| Laptop | 1000 |
|  2| Phone  |  500 |
|  3| Tablet |  300 |
+---+--------+------+
```

---

## **🔹 Example 3: Spark Data Processing (Transformation & Aggregation)**
### **📌 Objective:** Test Spark’s ability to perform ETL-like transformations.

```scala
val salesDF = spark.sql("SELECT * FROM sales_data")

// Apply transformation: Increase price by 10%
val updatedDF = salesDF.withColumn("new_price", salesDF("price") * 1.1)

// Show results
updatedDF.show()
```

✅ **Expected Output:**
```
+---+--------+------+---------+
| id| product| price|new_price|
+---+--------+------+---------+
|  1| Laptop | 1000 | 1100.0  |
|  2| Phone  |  500 |  550.0  |
|  3| Tablet |  300 |  330.0  |
+---+--------+------+---------+
```
---

Here’s the **corrected and optimized version** of the **Spark ML example** for your **separate Spark cluster** setup. This version ensures that Spark interacts properly with HDFS and runs efficiently in a distributed mode.

---

# **🏠 Spark ML Example: Predict House Prices with Linear Regression**  

### **📌 Objective**  
Train and evaluate a **Linear Regression** model using **Spark MLlib**, ensuring it runs **distributed** across your **Spark cluster** and interacts with **HDFS** and **Hive**.

---

## **1️⃣ Upload Dataset to HDFS**  
First, create a sample dataset and upload it to **HDFS** for Spark to process:

```bash
echo -e "sqft,bedrooms,price
850,2,180000
900,2,200000
1200,3,250000
1500,3,300000
1750,4,350000" > /tmp/housing_data.csv

# ✅ Upload to HDFS (Ensure you are using the correct Namenode URL)
hdfs dfs -mkdir -p /user/spark/input
hdfs dfs -put /tmp/housing_data.csv /user/spark/input/
```

---

## **2️⃣ Run Spark ML**  

### **📝 Install Numpy on all Spark nodes**

Run this command in all spark nodes:

```bash
sudo apt install python3-numpy
```

### **📝 Save the Following Python Script as `house_price_lr.py`**
```python
from pyspark.sql import SparkSession
from pyspark.ml.regression import LinearRegression
from pyspark.ml.feature import VectorAssembler

# ✅ Step 1: Initialize Spark Session (Ensure Hive & HDFS Access)
spark = SparkSession.builder \
    .appName("House Price Prediction") \
    .config("spark.master", "spark://<master-host>:7077") \
    .config("spark.hadoop.fs.defaultFS", "hdfs://<namenode>:9000") \
    .config("spark.sql.catalogImplementation", "hive") \
    .enableHiveSupport() \
    .getOrCreate()

# ✅ Step 2: Load CSV dataset from HDFS
df = spark.read.option("header", True).option("inferSchema", True) \
    .csv("hdfs:///user/spark/input/housing_data.csv")

# ✅ Step 3: Convert features into vector format
assembler = VectorAssembler(inputCols=["sqft", "bedrooms"], outputCol="features")
data = assembler.transform(df).select("features", "price")

# ✅ Step 4: Split data into training (80%) and testing (20%)
train_data, test_data = data.randomSplit([0.8, 0.2])

# ✅ Step 5: Train Linear Regression Model
lr = LinearRegression(featuresCol="features", labelCol="price")
model = lr.fit(train_data)

# ✅ Step 6: Print Model Coefficients
print(f"Intercept: {model.intercept}")
print(f"Coefficients: {model.coefficients}")

# ✅ Step 7: Make Predictions on Test Data
predictions = model.transform(test_data)
predictions.select("features", "price", "prediction").show()

# ✅ Step 8: Save Model to HDFS
model.save("hdfs:///user/spark/models/house_price_model")

# ✅ Stop Spark Session
spark.stop()
```

---

## **3️⃣ Submit the Job to Spark Cluster**  
Since your **Spark cluster is separate**, submit the job to **run on the cluster**:

```bash
spark-submit \
    --master spark://<master-host>:7077 \
    --conf spark.hadoop.fs.defaultFS=hdfs://<namenode>:9000 \
    --executor-memory 2G \
    --total-executor-cores 2 \
    house_price_lr.py
```

### **🔹 Explanation of Parameters**
- `--master spark://<master-host>:7077`: Connects to your **Spark cluster master**.
- `--executor-memory 2G`: Allocates **2GB RAM per executor**.
- `--total-executor-cores 2`: Uses **2 cores for processing**.
- `spark.hadoop.fs.defaultFS=hdfs://<namenode>:9000`: Ensures HDFS is properly accessed.

---

## **4️⃣ Load the Saved Model and Use for Future Predictions**  
Once the model is saved in **HDFS**, you can **load it later**:

```python
from pyspark.ml.regression import LinearRegressionModel

# Load the trained model from HDFS
loaded_model = LinearRegressionModel.load("hdfs:///user/spark/models/house_price_model")

# Make new predictions
new_predictions = loaded_model.transform(test_data)
new_predictions.show()
```

----

# **Use the trained Linear Regression model** to make new predictions.

---

## **✅ 1️⃣ Create a Test Dataset**
We will create a new CSV file (`new_housing_data.csv`) with **unseen house features** (square feet and bedrooms) and use the trained model to predict their prices.

```bash
echo -e "sqft,bedrooms
1000,2
1350,3
1600,3
1800,4
2000,4" > /tmp/new_housing_data.csv

# ✅ Upload to HDFS (Ensure HDFS path is correct)
hdfs dfs -put /tmp/new_housing_data.csv /user/spark/input/
```

---

## **✅ 2️⃣ Load the Model and Predict New Prices**
Create a new Python script **`test_house_price_model.py`**:

```python
from pyspark.sql import SparkSession
from pyspark.ml.regression import LinearRegressionModel
from pyspark.ml.feature import VectorAssembler

# ✅ Step 1: Initialize Spark Session
spark = SparkSession.builder \
    .appName("Test House Price Model") \
    .config("spark.master", "spark://<master-host>:7077") \
    .config("spark.hadoop.fs.defaultFS", "hdfs://<namenode>:9000") \
    .getOrCreate()

# ✅ Step 2: Load New Test Data from HDFS
test_df = spark.read.option("header", True).option("inferSchema", True) \
    .csv("hdfs:///user/spark/input/new_housing_data.csv")

# ✅ Step 3: Convert Features into Vector Format
assembler = VectorAssembler(inputCols=["sqft", "bedrooms"], outputCol="features")
test_data = assembler.transform(test_df).select("features")

# ✅ Step 4: Load the Trained Model from HDFS
model = LinearRegressionModel.load("hdfs:///user/spark/models/house_price_model")

# ✅ Step 5: Make Predictions
predictions = model.transform(test_data)
predictions.show()

# ✅ Step 6: Stop Spark
spark.stop()
```
🔹 **Replace `<master-host>` with your Spark master hostname or IP.**  
🔹 **Replace `<namenode>` with your actual Hadoop Namenode hostname/IP.**

---

## **✅ 3️⃣ Run the Prediction Script**
Submit the job to Spark:

```bash
spark-submit \
    --master spark://<master-host>:7077 \
    --deploy-mode cluster \
    test_house_price_model.py
```

---

## **✅ 4️⃣ Expected Output**
If everything works correctly, Spark will output **predicted house prices**:

```
+-------------+----------+
|     features|prediction|
+-------------+----------+
|[1000.0, 2.0]| 210000.0 |
|[1350.0, 3.0]| 260000.0 |
|[1600.0, 3.0]| 290000.0 |
|[1800.0, 4.0]| 330000.0 |
|[2000.0, 4.0]| 360000.0 |
+-------------+----------+
```

