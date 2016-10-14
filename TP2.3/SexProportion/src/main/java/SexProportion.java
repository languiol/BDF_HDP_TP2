import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.DataOutput;


/**o
 * Hello world!
 *
 */
public class SexProportion
{
    public static class IntArrayWritable extends ArrayWritable {
        public IntArrayWritable(IntWritable[] intWritables) {
            super(IntWritable.class);
        }

        @Override
        public IntWritable[] get() {
            return (IntWritable[]) super.get();
        }

        @Override
        public void write(DataOutput sortie) throws IOException {
            for(IntWritable data : get()){
                data.write(sortie);
            }
        }
    }

    public static class MonMapper extends Mapper<Object, Text, Text, IntArrayWritable>
    {
        private final static IntWritable one = new IntWritable(1);
        private final static IntWritable zero = new IntWritable(0);
        private Text origine = new Text();
        private IntWritable[] resultat = new IntWritable[2];
        private IntArrayWritable output = new IntArrayWritable(resultat);
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {

            String[] result = value.toString().split(";");

            try {
                String[] result2 = result[1].toString().split(",");

                //.replaceAll(" ","")

                resultat[1] = one;

                for (int i = 0; i < result2.length; ++i)
                {
                    if(result2[i]=="m"){
                        resultat[0]=one;
                    }
                    else if(result2[i]=="f"){
                        resultat[0]=zero;
                    }

                    output.set(resultat);
                    origine.set("hf");
                    context.write(origine, output);
                }
            }
            catch(Exception e)
            {

            }

        }
    }

    public static class MonReducer extends Reducer<Text,IntArrayWritable,Text,IntWritable>
    {
        private IntWritable result = new IntWritable();
        private Text keyout = new Text();
        private final static IntWritable one = new IntWritable(1);
        private final static IntWritable zero = new IntWritable(0);

        @Override
        public void reduce(Text key, Iterable<IntArrayWritable> values, Context context) throws IOException, InterruptedException
        {
            int tot = 0;
            int sumh = 0;
            int sumf =0;
            int average;

            for (IntArrayWritable value : values)
            {
                Writable[] resultat = value.get();
                if(resultat[0]== one) {
                    sumh += 1;
                }
                if(resultat[0]== zero) {
                    sumf += 1;
                }
                tot += 1;
                //sum += value.get();

            }

            result.set(sumh/tot);
            keyout.set("Homme");
            context.write(keyout,result);

            result.set((sumf)/tot);
            keyout.set("Femme");
            context.write(keyout,result);

        }
    }

    public static void main(String[] args) throws Exception
    {
        //Création de la configuration, afin de séparer les valeurs de sorties avec ";"
        Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ";");

        //Création du job
        Job job = new Job(conf, "Sex Proportion");
        job.setJarByClass(SexProportion.class);

        //on configure les entrées
        //on indique au job le chemin du fichier des données : args[0]
        FileInputFormat.addInputPath(job, new Path(args[0]));

        //on configure les sorties
        //on indique au job le répertoire à créer pour enregistrer les resultats : args[1]
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //on précise les types des cle et des valeurs produites par la fonction mapreduce
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntArrayWritable.class);

        //on indique au job les classes contenant les methodes map, reduce et combiner
        job.setMapperClass(MonMapper.class);

        //pas besoin de combiner pour ce job
        //job.setCombinerClass(MonReducer.class);
        job.setReducerClass(MonReducer.class);

        // execute le job et retourne son statut.
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}