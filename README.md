# college-catalogue
This Java app is meant to simulate a college catalogue for handling data of students, professors, and information such as grades and specializations. The system is represented through 8 main classes:
* Student
* Professor
* Student Group
* Student Series (contains multiple groups)
* Specialization (of students)
* Subject
* Course (where a certain subject is taught)
* Grade (earned by a student for a certain course)

Data for each of these is imported on startup through their respective CSV files found in `data`. The app allows for the following main operations through the console:
* Add student
* List information of student
* Set student representative of group or series
* List students in group or series
* Add course to a professor's list of taught courses
* Add grade to student
* Show yearly average grade of student
* List courses of subject
* Show average grade of series
* Add specialization to students

During execution, actions taken will be recorded in `data/audit.csv` along with timestamps.
## Notes
Project built for my Advanced Programming Elements (Java) Course in college.
