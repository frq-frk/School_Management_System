-- Insert Sample Data into Student Table
INSERT INTO student (id, name, student_class, guardian_name, guardian_phone) 
VALUES 
(4, 'John Doe', 'X_CLASS', 'Jane Doe', '9876543210'),
(5, 'Alice Johnson', 'XI_CLASS', 'Robert Johnson', '8765432109'),
(3, 'Bob Smith', 'X_CLASS', 'Sarah Smith', '7654321098');

-- Insert Sample Data into Marks Table
INSERT INTO marks (id, student_id, subject, marks_obtained, term) 
VALUES 
(7, 1, 'Mathematics', 85, 'Term 1'),
(8, 1, 'Science', 90, 'Term 1'),
(3, 2, 'Mathematics', 78, 'Term 1'),
(4, 2, 'Science', 88, 'Term 1'),
(5, 3, 'Mathematics', 92, 'Term 1'),
(6, 3, 'Science', 95, 'Term 1');

-- Insert Sample Data into Attendance Table
INSERT INTO attendance (id, student_id, date, status) 
VALUES 
(7, 1, '2025-01-10', 'Present'),
(8, 1, '2025-01-11', 'Absent'),
(3, 2, '2025-01-10', 'Present'),
(4, 2, '2025-01-11', 'Present'),
(5, 3, '2025-01-10', 'Absent'),
(6, 3, '2025-01-11', 'Present');
