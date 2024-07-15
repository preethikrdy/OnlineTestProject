package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Questions implements Serializable{
	private int questionNum;
	private double points;
	private String text;

	
	public Questions(int questionNum, double points, String text) {
		this.questionNum = questionNum;
		this.points = points;
		this.text = text;
	}
	
	public int getQuestionNumber() {
		return this.questionNum;
	}
	
	public double getPoints() {
		return this.points;
	}
	
	public String toString() {
		return "Question Text: " + this.text + "\nPoints: " + this.points + "\n";
	}
	
	public String getQuestionText() {
		return this.text;
	}
	//Abstract method so that the sub-classes can implement it
	public abstract boolean isCorrect(String answer[]);
	
}
