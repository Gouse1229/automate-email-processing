package com.evoke.emailprocessing.nlp;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class TrainModel {

    public void trainModel(){
        // Train model
        try {
            // Load training data
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("C:\\nlp\\train.txt"));
            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            // Training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 50); // Reduce iterations for small datasets
            params.put(TrainingParameters.CUTOFF_PARAM, 0);     // Include all features, no cutoff

            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());

            // Save trained model
            try (OutputStream modelOut = new FileOutputStream("nlpfiles/email-classifier-model.bin")) {
                model.serialize(modelOut);
            }
            System.out.println("Model training successful!");
        } catch (Exception e) {
            System.err.println("Error during training: " + e.getMessage());
        }

    }

}


