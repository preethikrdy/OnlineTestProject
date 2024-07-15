package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable{
	private int id;
	private String title;
	private ArrayList<Questions> questions;
	
	
	public Exam(int id, String title) {
		this.title = title;
		this.id = id;
		this.questions = new ArrayList<Questions>();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void addQuestion(Questions question) {
		//Adding the param question to the questions array list
		questions.add(question);
	}
	
	public Questions getQuestion(int questionNum) {
		//traversing through the questions array list to find the specific one
		for(Questions q : questions) {
			if(q.getQuestionNumber() == questionNum) {
				return q;
			}
		}
		return null;
	}
	
	public ArrayList<Questions> getAllQuestions(){
		return this.questions;
	}
	
	public double getAllPoints() {
		//Adding up all of the points for the questions 
		double score = 0;
		for(Questions q : questions) {
			score += q.getPoints();
		}
		return score;
	}
}
