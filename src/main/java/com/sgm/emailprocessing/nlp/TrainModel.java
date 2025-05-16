package com.sgm.emailprocessing.nlp;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class TrainModel {

    public void trainModel() {
        try {
            // Step 1: Load training data from classpath and copy to a temp file
            ClassPathResource resource = new ClassPathResource("nlpfiles/train.txt");

            File tempTrainFile = File.createTempFile("train", ".txt");
            tempTrainFile.deleteOnExit();

            try (InputStream in = resource.getInputStream();
                 OutputStream out = new FileOutputStream(tempTrainFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            // Step 2: Create input stream factory from the temp file
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(tempTrainFile);
            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            // Step 3: Train the model
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 50);
            params.put(TrainingParameters.CUTOFF_PARAM, 0);

            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());

            // Step 4: Save the model to a writable directory (e.g., ./models)
            File modelDir = new File("models");
            modelDir.mkdirs(); // ensure the directory exists

            try (OutputStream modelOut = new FileOutputStream(new File(modelDir, "email-classifier-model.bin"))) {
                model.serialize(modelOut);
            }

            System.out.println("Model training successful!");
        } catch (Exception e) {
            System.err.println("Error during training: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
