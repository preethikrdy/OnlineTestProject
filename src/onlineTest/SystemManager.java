package onlineTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SystemManager implements Manager, Serializable {
	
	private Map<String, Double> grades = new TreeMap<String, Double>();
	private ArrayList<Exam> exams = new ArrayList<Exam>();
	private Map<String, Map<Integer, List<Double>>> studentScores = new HashMap<String, Map<Integer, List<Double>>>();


	@Override
	public boolean addExam(int examId, String title) {
		Exam exam = new Exam(examId, title);
		//Adding a new exam if it is not already in the array list
		if(!(exams.contains(exam))) {
			exams.add(exam);
			return true;
		}
		return false;
	}
		
	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		//Going through the exams to find the specific exam Id and adding the question
		for(Exam e : exams) {
			if(e.getId() == examId) {
				e.addQuestion(new TFQuestion(questionNumber, points, text, answer));
			}
		}
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		//Going through the exams to find the specific exam Id and adding the questiom
		for(Exam e : exams) {
			if(e.getId() == examId) {
				e.addQuestion(new MultipleChoiceQuestion(questionNumber, points, text, answer));
			}
		}		
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		//Going through the exams to find the specific exam Id and adding the question
		for(Exam e : exams) {
			if(e.getId() == examId) {
				e.addQuestion(new FillInTheBlankQuestion(questionNumber, points, text, answer));
			}
		}			
	}

	@Override
	public String getKey(int examId) {
		StringBuffer sb = new StringBuffer();
		//traversing through the questions in the exam and adding it to the 
		//string buffer
		for(Exam e : exams) {
			if(e.getId() == examId) {
				for(Questions q : e.getAllQuestions()) {
					sb.append(q.toString());
				}
			}
		}
		return sb.toString();
	}

	@Override
	public boolean addStudent(String name) {
		//Adding the student to the map if it is not already there
		if (!studentScores.containsKey(name)) {
			studentScores.put(name, new HashMap<Integer, List<Double>>());
			return true;
		}
		return false;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		//Adding the answers array list into the map
		if (!studentScores.get(studentName).containsKey(examId)) {
			studentScores.get(studentName).put(examId, new ArrayList<Double>());
		}
		for (Exam e : exams) {
            if (e.getId() == examId) {
            	TFQuestion tf = (TFQuestion) e.getQuestion(questionNumber);
            	//If the answer is correct, then add the points, if not, then add 0 points
            	if(tf.getAnswer() == answer) {
            		studentScores.get(studentName).get(examId).add(tf.getPoints());
            	} 
            	else {
            		studentScores.get(studentName).get(examId).add(0.0);
            	}
                return;
            }
        }		
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		if (!studentScores.get(studentName).containsKey(examId)) {
			studentScores.get(studentName).put(examId, new ArrayList<Double>());
		}
		for (Exam e : exams) {
            if (e.getId() == examId) {
            	MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) e.getQuestion(questionNumber);
            	//If the answer is correct, then add the points, if not, then add 0 points
                if(mcq.isCorrect(answer)) {
                	studentScores.get(studentName).get(examId).add(mcq.getPoints());
                } else {
	                studentScores.get(studentName).get(examId).add(0.0);
	            }
                return;
            }
        }		
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
	    if (!studentScores.get(studentName).containsKey(examId)) {
	        studentScores.get(studentName).put(examId, new ArrayList<Double>());
	    }
	    for (Exam e : exams) {
	        if (e.getId() == examId) {
	            FillInTheBlankQuestion fb = (FillInTheBlankQuestion) e.getQuestion(questionNumber);
	          //If the answer is correct, then add the points times the partial credit points
	            if (fb.isCorrect(answer)) {
	            	double totalPoints = (fb.getPoints() * fb.getPartialCredit());
	                studentScores.get(studentName).get(examId).add(totalPoints); 
	            } 
	            return;
	        }
	    }	
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		double score = 0.0;
	    List<Double> scores = studentScores.get(studentName).get(examId);
	    //getting total score of the specific exam
	    for (int i = 0; i < scores.size(); i++) {
	    	score += studentScores.get(studentName).get(examId).get(i);
	    }
	    return score;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		StringBuffer report = new StringBuffer();
	    double studentScore = 0.0;
	    double totalScore = 0.0;
	    int counter = 1;
	    
	    for (Exam e : exams) {
	        if (e.getId() == examId) { 
	            for (Questions q : e.getAllQuestions()) {
	                double questionScore = 0.0;
	                List<Double> scores = studentScores.get(studentName).get(examId);
	                if (scores != null && counter <= scores.size()) { 
	                    questionScore = scores.get(counter - 1);
	                }
	                //Adding the student scores and the total score to the string buffer
	                report.append("Question #").append(counter).append(" ")
	                .append(questionScore).append(" points out of ").append(q.getPoints()).append("\n");
	                studentScore += questionScore;
	                totalScore += q.getPoints();
	                counter++;
	            }
	        }
	    }
	    report.append( "Final Score: " + studentScore + " out of " + totalScore);
		return report.toString();
	 }

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		//Clearing the grades so they overlap when reusing this method
		grades.clear();
		//Adding the grades and cutoffs to letterGrades map
		for (int i = 0; i < letterGrades.length; i++) {
			grades.put(letterGrades[i], cutoffs[i]);
		}
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double totalGrade = 0.0;
	    int examCount = 0;
	    for (Exam e : exams) {
	        examCount++;
	        double examScore = 0.0;
	        for (Questions q : e.getAllQuestions()) {
	            if (studentScores.get(studentName).containsKey(e.getId()) &&
	                studentScores.get(studentName).get(e.getId()).size() >= q.getQuestionNumber()) {
	            	//Adding the score of each question to the examScore
	                double questionScore = studentScores.get(studentName).get(e.getId()).get(q.getQuestionNumber() - 1);
	                examScore += questionScore;
	            }
	        }
	        //Getting the total grade of all of the exams
	        totalGrade += (examScore / e.getAllPoints());
	    }
	    //returning the average grades of all of the exams
	    return (totalGrade / examCount) * 100;
	}
	
	@Override
	public String getCourseLetterGrade(String studentName) {
		for (String grade : grades.keySet()) {
			//Finding the highest possible grade cutoff the student could have
			if (grades.get(grade) <= getCourseNumericGrade(studentName)) {
				return grade;
			}
		}
		return null;
	}

	@Override
	public String getCourseGrades() {
		String grades = "";
		ArrayList<String> list = new ArrayList<String>();
		//Adding each studnet into a new array list to be able to sort it
		for (String student : studentScores.keySet()) {
			list.add(student);
		}
		Collections.sort(list);
		//Getting the grades from the sorted list of students
		for (int i = 0; i < list.size(); i++) {
			grades += list.get(i) + " " + getCourseNumericGrade(list.get(i))
			+ " " + getCourseLetterGrade(list.get(i)) + "\n";
		}
		return grades;
	}
	
	
	@Override
	public double getMaxScore(int examId) {
		double studentScore = 0;
		ArrayList<Double> scores = new ArrayList<Double>();
		//Adding all of the students scores to a new array list to be sorted
		for (String student : studentScores.keySet()) {
			for (int i = 0; i < studentScores.get(student).get(examId).size(); i++) {
				studentScore += studentScores.get(student).get(examId).get(i);
			}
			scores.add(studentScore);
			studentScore = 0;
		}
		//returning the maximum score from the array list 
		return Collections.max(scores);
	}

	@Override
	public double getMinScore(int examId) {
		double studentScore = 0.0;
		ArrayList<Double> scores = new ArrayList<Double>();
		//Adding all of the students scores to a new array list to be sorted

		for (String student : studentScores.keySet()) {
			for (int i = 0; i < studentScores.get(student).get(examId).size(); i++) {
				studentScore += studentScores.get(student).get(examId).get(i);
			}
			scores.add(studentScore);
			studentScore = 0;
		}
		//returning the minimum score from the array list 

		return Collections.min(scores);
	}

	@Override
	public double getAverageScore(int examId) {
		double average = .0;
		int count = 0;
		//Getting all of the scores for that exam and finding the average
		for (String student : studentScores.keySet()) {
			count++;
			for (int i = 0; i < studentScores.get(student).get(examId).size(); i++) {
				average += studentScores.get(student).get(examId).get(i);
			}
		}
		return average / count;
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		  try (FileOutputStream f = new FileOutputStream(fileName);
			   ObjectOutputStream o = new ObjectOutputStream(f)) {
			   o.writeObject(manager);
		  } catch(IOException e) {
			        e.printStackTrace();
		  }	   
	}

	@Override
	public Manager restoreManager(String fileName) {
		Manager manager = null;
	    try (FileInputStream f = new FileInputStream(fileName);
	        BufferedInputStream b = new BufferedInputStream(f);
	        ObjectInputStream o = new ObjectInputStream(b)) {
	        Manager m = (Manager) o.readObject();
	        manager = m;
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return manager;
	}
}
