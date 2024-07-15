package onlineTest;

import java.io.Serializable;

public class TFQuestion extends Questions implements Serializable{
	
	private boolean answer;

	public TFQuestion(int questionNum, double points, String text, boolean answer) {
		super(questionNum, points, text);
		this.answer = answer;
	}
	
	public String toString(){
		if(answer == true){
			return super.toString() + "Correct Answer: True\n" ;
		} 
			return super.toString() + "Correct Answer: False\n" ;
	}
	
	public boolean getAnswer() {
		return this.answer;
	}

	@Override
	public boolean isCorrect(String[] answer) {
		//Converting the boolean into a string
		String stringAnswer = Boolean.toString(this.answer);
		//Checking if the answer in the array is equal to the string answer
		if(stringAnswer.equals(answer[0])) {
			return true;
		}
		return false;
	}
}
