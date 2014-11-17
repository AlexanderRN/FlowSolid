package Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AlexanderNielsen
 */
public class Wordpair {
    String question, answer;
    int priority;
    public Wordpair(String question, String answer, int priority){
        this.question = question;
        this.answer = answer;
        this.priority = priority;
        
    }
    
    public String toString(){
        return "" + question + "," + answer + "," + priority;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    
    
}
