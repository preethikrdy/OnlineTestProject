package onlineTest;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceQuestion extends Questions implements Serializable{
	
	private String[] answer;

	public MultipleChoiceQuestion(int examId, double points, String text, String[] answer) {
		super(examId, points, text);
		this.answer = answer;
	}
	public String toString() {
		return super.toString() + "Correct Answer: " + Arrays.toString(this.answer) + "\n";
	}
	
	public String[] getAnswers() {
		return answer;
	}
	public void setAnswerChoice(String[] answer) {
		this.answer = answer;
	}
	
	public boolean isCorrect(String answer[]) {
		//Automatically returns false if the array lengths are different
		if (this.answer.length != answer.length) {
            return false;
        }
        Arrays.sort(this.answer);
        Arrays.sort(answer);
        //Going through the answer array to see if the answers different
        for (int i = 0; i < this.answer.length; i++) {
            if (this.answer[i] != answer[i]) {
                return false;
            }
        }
        return true;
	}

}
