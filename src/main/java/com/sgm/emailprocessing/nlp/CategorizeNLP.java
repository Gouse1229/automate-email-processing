package com.sgm.emailprocessing.nlp;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CategorizeNLP {

    public String categorizeContent(String emailContent) throws Exception {
        // Load the trained model
        InputStream modelIn = new ClassPathResource("models/email-classifier-model.bin").getInputStream();
        DoccatModel doccatModel = new DoccatModel(modelIn);

        // Initialize the categorizer
        DocumentCategorizerME categorizer = new DocumentCategorizerME(doccatModel);

        // Load the tokenizer model
        InputStream tokenModelIn = new ClassPathResource("nlpfiles/en-token.bin").getInputStream();
        TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
        Tokenizer tokenizer = new TokenizerME(tokenModel);

//        String[] testSamples = {
//                "Regarding My policy Status This is regaridng my ploicy ID:12345 can you help me with the status.",
//                "Reagrding Claim Good Day! I have conserns regarding the claim process can you please help me with the fileing a cliam. ",
//                "Regarding policy number: 89756 Hi can you check the status of my policy payment.",
//                "policy number: 67897 I want to discontinue my policy tell me the process",
//                "policy number: 67897 let me know the premium payment of mine",
//                "I would like to file a claim for a car accident."
//        };
        String[] tokens = tokenizer.tokenize(emailContent);
        double[] outcomes = categorizer.categorize(tokens);
        String category = categorizer.getBestCategory(outcomes);

//        for (String sample : testSamples) {
//            String[] tokens = tokenizer.tokenize(sample);
//            // Tokenize the input
//
//
//            // Categorize the tokenized input
//            double[] outcomes = categorizer.categorize(tokens);
//
//            // Get the predicted category
//            String category = categorizer.getBestCategory(outcomes);
//
//            // Output the result
//            System.out.println("Input: " + sample);
//            System.out.println("Predicted Category: " + category);
//
//        }

        return category;

    }


}