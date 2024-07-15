### Online Test System

This project implements a system to manage exams, questions, student answers, and grading.

#### Classes and Interfaces

1. **Exam**
   - Manages details of an exam including ID, title, and a list of questions.
   - Concepts: Encapsulation, ArrayList usage.

2. **FillInTheBlankQuestion**
   - Represents a fill-in-the-blank question extending the Questions class.
   - Stores correct answers and calculates partial credit.
   - Concepts: Inheritance, Arrays usage.

3. **Manager (Interface)**
   - Defines methods to add exams, questions, manage student answers, and compute grades.
   - Concepts: Interface, method signatures.

4. **MultipleChoiceQuestion**
   - Represents a multiple-choice question extending the Questions class.
   - Stores correct answers and checks student answers for correctness.
   - Concepts: Inheritance, Arrays usage.

5. **Questions (Abstract class)**
   - Abstract base class for different question types.
   - Contains common attributes and abstract method for checking correctness.
   - Concepts: Abstract class, inheritance.

6. **SystemManager**
   - Implements Manager interface, manages exams, student scores, and grading.
   - Stores exams, student scores, and computes various statistics and reports.
   - Concepts: Collections (Map, ArrayList), File I/O, Serialization.

7. **TFQuestion**
   - Represents a true/false question extending the Questions class.
   - Stores correct answer and checks if student answer matches.
   - Concepts: Inheritance, Boolean operations.

#### Usage

- **Adding Exams and Questions:** Use `SystemManager` to add exams and various question types to exams.
- **Managing Student Answers:** `SystemManager` handles student answers for different question types.
- **Grading and Reporting:** Compute scores, generate grading reports, and manage grading criteria.
