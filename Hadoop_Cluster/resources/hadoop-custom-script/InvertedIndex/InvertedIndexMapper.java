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

