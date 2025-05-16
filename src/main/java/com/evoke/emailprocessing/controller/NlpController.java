package com.evoke.emailprocessing.controller;

import com.evoke.emailprocessing.model.NlpContent;
import com.evoke.emailprocessing.nlp.CategorizeNLP;
import com.evoke.emailprocessing.nlp.TrainModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
