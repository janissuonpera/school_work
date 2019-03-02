
class Course:

    def __init__(self, name, credits, grade):
        self.name, self.credits, self.grade = name, credits, grade
    
    def __str__(self):
        course_info = f"Name: {self.name}\nCredits: {self.credits}\nGrade: {self.grade}"
        return course_info
    



class Student:
    courselist = []
    def __init__(self, name, number):
        self.name, self.number = name, number
    
    def __str__(self):
        student_info = f"{self.name} ({self.number})"
        return student_info

    def addCourse(self, c):
        self.courselist.append(c)
    
    def coursesByName(self):
        courses = self.courselist
        courses.sort(key=lambda a: a.name)
        for course in courses:
            print(course)
            print()
    
    def coursesByCredits(self):
        courses = self.courselist
        courses.sort(key=lambda a: a.name)
        courses.sort(key=lambda a: a.credits, reverse=True)
        for course in courses:
            print(course)
            print()
        
    def coursesByGrade(self):
        courses = self.courselist
        courses.sort(key=lambda a: a.name)
        courses.sort(key=lambda a: a.grade, reverse=True)
        for course in courses:
            print(course)
            print()
        
    def credits(self):
        total = [i.credits for i in self.courselist]
        credit_sum = 0
        for x in range (len(total)):
            credit_sum += int(total[x])
        return credit_sum
        
        
c = Course("Introduction to Calculus", 5, 4)
print(c)
print(c.name, c.credits, c.grade)
s = Student("Brenda Baker", 125321)
print(s)
print(s.name, s.number)
s2 = Student("John Fletcher", 132748)
print(s2)
c2 = Course("Introduction to Statistics", 5, 5)
print(c2)
c3 = Course("Theory of Everything", 10, 3)
print(c3)
c4 = Course("Internship", 2, 4)
print(c4)
c5 = Course("Theoretical Literature", 5, 4)
print(c5)
print()
print("Total credits:", s2.credits())
print()
s2.addCourse(c)
s2.addCourse(c5)
s2.addCourse(c3)
s2.addCourse(c2)
s2.addCourse(c4)
print("Total credits:", s2.credits())
print()
print("Courses by name:")
s2.coursesByName()
print()
print("Courses by credits:")
s2.coursesByCredits()
print()
print("Courses by grade:")
s2.coursesByGrade()










