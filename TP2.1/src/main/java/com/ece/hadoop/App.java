package com.ece.hadoop;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * Hello world!
 *
 */
public class App
{   //Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
    public static class MonMapper extends Mapper<Object, Text, Text, IntWritable>
    {
        private final static IntWritable one = new IntWritable(1);
        private Text origine = new Text();
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            //On sépare valuein par rapport aux ";"
            String[] result = value.toString().replaceAll(" ","").split(";");
            try
            {   //On sépare result2 par rapport aux "," et on traite uniquement les origines
                String[] result2 = result[2].toString().split(",");

                //on parcourt notre array selon sa taille
                for( int i = 0; i<result2.length; ++i)
                {
                    origine.set(result2[i].toString());
                    //write(KEYOUT,VALUEOUT)
                    context.write(origine,one);
                }



            }
            catch(Exception e)
            {

            }

        }
    }

    //Reducer<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
    public static class MonReducer extends Reducer<Text,IntWritable,Text,IntWritable>
    {
        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
        {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }

            result.set(sum);

            context.write(key,result);
        }
    }

    public static void main(String[] args) throws Exception
    {

        //Création de la configuration, afin de séparer les valeurs de sorties avec ";"
        Configuration conf = new Configuration();
        conf.set("mapred.textoutputformat.separator", ";");

        //Création du job
        Job job = new Job(conf, "Country Name");
        job.setJarByClass(App.class);

        //on configure les entrées
        //on indique au job le chemin du fichier des données : args[0]
        FileInputFormat.addInputPath(job, new Path(args[0]));

        //on configure les sorties
        //on indique au job le répertoire à créer pour enregistrer les resultats : args[1]
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //on précise les types des cle et des valeurs produites par la fonction mapreduce
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //on indique au job les classes contenant les methodes map, reduce et combiner
        job.setMapperClass(MonMapper.class);
        job.setCombinerClass(MonReducer.class);
        job.setReducerClass(MonReducer.class);

        // execute le job et retourne son statut.
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
