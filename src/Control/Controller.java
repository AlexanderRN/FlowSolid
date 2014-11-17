package Control;


import Entities.Wordpair;
import Entities.FileHandler;
import Interfaces.WordPairControlInterface;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AlexanderNielsen
 */
public class Controller implements WordPairControlInterface {
    private ArrayList<Wordpair> qA;
    private HashMap qAMap;
    private ArrayList<Wordpair> p1;
    private ArrayList<Wordpair> p2;
    private ArrayList<Wordpair> p3;
    private ArrayList<Wordpair> p4;
    private ArrayList<Wordpair> p5;
    
    public Controller(){
        
        //qA = FileHandler.loadWordpairs("wordpair.txt");
        //load("wordpair.txt");
        qA = new ArrayList<>();
        qA = FileHandler.loadWordpairs("wordpair.txt");
        p1 = new ArrayList<>();
        p2 = new ArrayList<>();
        p3 = new ArrayList<>();
        p4 = new ArrayList<>();
        p5 = new ArrayList<>();
        qAMap = new HashMap();
        
        
        
    }

    @Override
    public void add(String question, String answer) {
        Wordpair newWordpair = new Wordpair(question, answer.toLowerCase(), 1);
        qA.add(newWordpair);
        System.out.println(qA);
        qAMap.put(newWordpair.getQuestion(), newWordpair);
        
    }

    @Override
    public int size() {
        return qAMap.size();
    }

    @Override
    public String getRandomQuestion() {
        int randomquestion = (int) (Math.random()*15+1);
        clear();
        fillPriorityLists();
        fillHashMap();
        if(randomquestion > 0 && randomquestion < 6 && p1.size() > 0){
            int randomindex = (int) (Math.random()* p1.size() + 0);
            Wordpair question =  p1.get(randomindex);
            return question.getQuestion();
        }
        else if(randomquestion > 5 && randomquestion < 10 && p2.size() > 0){
            int randomindex = (int) (Math.random()* p2.size() + 0);
            Wordpair question =  p2.get(randomindex);
            return question.getQuestion();
        }
        else if(randomquestion > 9 && randomquestion < 13 && p3.size() > 0){
            int randomindex = (int) (Math.random()* p3.size() + 0);
            Wordpair question =  p3.get(randomindex);
            return question.getQuestion();
        }
        else if(randomquestion > 12 && randomquestion < 15 && p4.size() > 0){
            int randomindex = (int) (Math.random()* p4.size() + 0);
            Wordpair question =  p4.get(randomindex);
            return question.getQuestion();
        }
        else if(randomquestion == 15 && p5.size() > 0){
            int randomindex = (int) (Math.random()* p5.size() + 0);
            Wordpair question =  p5.get(randomindex);
            return question.getQuestion();
        }
        else{
            int randomindex = (int) (Math.random()* qA.size() + 0);
            Wordpair question =  qA.get(randomindex);
            return question.getQuestion();
        }        
    }
    
    private void fillPriorityLists(){
        for(Wordpair wP: qA){
           switch(wP.getPriority()){
               case 1: p1.add(wP); break;
               case 2: p2.add(wP); break;
               case 3: p3.add(wP); break;
               case 4: p4.add(wP); break;
               case 5: p5.add(wP); break;
           }
        }
    }    

    @Override
    public boolean checkGuess(String question, String quess) {
        Wordpair activequestion = (Wordpair) qAMap.get(question);
        if((activequestion.getQuestion().equals(question)) && (activequestion.getAnswer().equals(quess.toLowerCase()))){
            if(activequestion.getPriority()< 5){
            activequestion.setPriority(activequestion.getPriority()+1);
            }
            return true;
        } else {
            return false;
        }        
    }

    @Override
    public String lookup(String question) {
        Wordpair activequestion = (Wordpair) qAMap.get(question);
        return activequestion.getAnswer();
        
        
    }

    @Override
    public boolean load(String filename) {
        try{
            qA = FileHandler.loadWordpairs(filename);
            fillHashMap();
            return true;
        } catch (Exception ex){
            System.out.println("fejl");
            return false;
        }
       
    }
    
    private void fillHashMap(){
        for(Wordpair wordpair: qA){
                qAMap.put(wordpair.getQuestion(), wordpair);
            }
    }
    
    

    @Override
    public boolean save(String filename) {
        if(FileHandler.saveWordpair(qA, filename) == true){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void clear() {
        
        p1.clear();
        p2.clear();
        p3.clear();
        p4.clear();
        p5.clear();
        qAMap.clear();
    }
    
}
