package onlineTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class FillInTheBlankQuestion extends Questions implements Serializable{
	
	private String answer[];
	private double partialCredit;

	public FillInTheBlankQuestion(int questionNum, double points, String text, String answer[]) {
		super(questionNum, points, text);
		this.answer = answer;	
	}
	
	public String toString() {
		Arrays.sort(this.answer);
		return super.toString() + "Correct Answer: " + Arrays.toString(this.answer) + "\n";
	}
	
	public String[] getCorrectAnswers() {
		return this.answer;
	}
	
	public void setCorrectAnswers(String[] answer) {
		this.answer = answer;
	}
	
	public boolean isCorrect(String answer[]) {
		//Checking to see if the answer array contains the answer to add
		//points to the partial credit 
		int numCorrect = 0;
	    for (int i = 0; i < answer.length; i++) {
	        if (Arrays.asList(this.answer).contains(answer[i])) {
	            numCorrect++;
	        }
	    }
	    this.partialCredit = (double) numCorrect / this.answer.length;
	    return true;
	}
	
	public double getPartialCredit() {
		return this.partialCredit;
	}

}
