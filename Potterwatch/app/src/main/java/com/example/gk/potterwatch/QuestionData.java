package com.example.gk.potterwatch;

/**
 * Created by gk on 7/23/17.
 */

public class QuestionData {
    private String Question;
    private String Option1;
    private String Option2;
    private String Option3;
    private String Option4;
    private String Answer;

    public QuestionData(String a, String b, String c, String d, String e, String val) {
        Question = a;
        Option1 = b;
        Option2 = c;
        Option3 = d;
        Option4 = e;
        Answer = val;
    }

    public String getQuestion() {
        return Question;
    }

    public String getOption1() {
        return Option1;
    }

    public String getOption2() {
        return Option2;
    }

    public String getOption3() {
        return Option3;
    }

    public String getOption4() {
        return Option4;
    }
    public String getAnswer(){
        return Answer;
    }

    public void setQuestion(String val) {
        Question = val;
    }

    public void setOption1(String val) {
        Option1 = val;
    }

    public void setOption2(String val) {
        Option2 = val;
    }

    public void setOption3(String val) {
        Option3 = val;
    }

    public void setOption4(String val) {
        Option4 = val;
    }

    public void setAnswer(String val){
        Answer = val;
    }


}
