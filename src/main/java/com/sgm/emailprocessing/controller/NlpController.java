package com.sgm.emailprocessing.controller;

import com.sgm.emailprocessing.model.NlpContent;
import com.sgm.emailprocessing.nlp.CategorizeNLP;
import com.sgm.emailprocessing.nlp.TrainModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NlpController {

    @Autowired
    private CategorizeNLP categorizedNlp;

    @Autowired
    private TrainModel trainModel;

    @GetMapping("/trainTheModel")
    public String trainTheModel(){
        trainModel.trainModel();
        return "index";
    }

    @PostMapping("/Categorize")
    public String categorize(@RequestBody NlpContent content){

        try{
            String category = categorizedNlp.categorizeContent(content.getContent());
        }catch(Exception e){
            e.printStackTrace();
        }

        return "emails";
    }
}
