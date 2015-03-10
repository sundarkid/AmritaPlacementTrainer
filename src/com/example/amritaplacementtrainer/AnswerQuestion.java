package com.example.amritaplacementtrainer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sundareswaran on 10-03-2015.
 */
public class AnswerQuestion {

    public String answer;
    public int id;

    public JSONObject getJsonObject(){
        JSONObject object = new JSONObject();
        try{
            object.put("id_no",id);
            object.put("answer",answer);
        }catch (JSONException e){

        }
        return object;
    }

}
