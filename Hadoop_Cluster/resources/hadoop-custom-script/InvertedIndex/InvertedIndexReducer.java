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

