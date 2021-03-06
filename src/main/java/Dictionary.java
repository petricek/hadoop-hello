/**
 * Created by petricek on 4/21/14.
 * For fun
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.fs.FileSystem;

/**
 * Created with IntelliJ IDEA.
 * User: pascal
 * Date: 16-07-13
 * Time: 12:07
 */
public class Dictionary {

    public static void main(String[] args) throws Exception
    {
        System.out.println("Go");

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJobName("dictionary");
        job.setJarByClass(Dictionary.class);
        job.setMapperClass(WordMapper.class);
        job.setReducerClass(AllTranslationsReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileSystem fs = FileSystem.get(conf);
        fs.delete(new Path(args[1]), true); // delete file, true for recursive

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean result = job.waitForCompletion(true);
        System.out.println("Done");
        System.exit(result ? 0 : 1);
    }
}