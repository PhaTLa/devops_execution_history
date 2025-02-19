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

