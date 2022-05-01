package IOHomework;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String courseName;
    private String score;

    public Student() {
    }

    public Student(String id, String name, String gender, String courseName, String score) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.courseName = courseName;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
