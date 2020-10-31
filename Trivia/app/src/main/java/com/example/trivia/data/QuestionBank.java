package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.trivia.controller.AppController.TAG;

public class QuestionBank {
    ArrayList<Question> questionArrayList=new ArrayList<>();
    private String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    public List<Question> getQuestions(final AnswerListAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d("JSON Stuff", "onResponse: "+response);
                        for(int i=0;i<response.length();i++){
                            try {
                                Question question=new Question();
                                question.setAnswer(response.getJSONArray(i).getString(0));
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);
                                //Log.d("JSON1:", "onResponse: "+response.getJSONArray(i).getString(0));
                                //Log.d("JSON2:", "onResponse: "+response.getJSONArray(i).getBoolean(1));
                                //Log.d("Question:", "onResponse: "+question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(null!=callBack) { callBack.processFinished(questionArrayList); }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: "+error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }
}
