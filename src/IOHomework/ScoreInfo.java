package IOHomework;

public class ScoreInfo {    //内部类
    private String id;
    private String name;
    private String gender;
    private String scoreSum;
    private String scoreAvg;

    public ScoreInfo() {
    }

    public ScoreInfo(String id, String name, String gender, String scoreSum, String scoreAvg) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.scoreSum = scoreSum;
        this.scoreAvg = scoreAvg;
    }

    public String getScoreAvg() {
        return scoreAvg;
    }

    public void setScoreAvg(String scoreAvg) {
        this.scoreAvg = scoreAvg;
    }

    public String getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(String scoreSum) {
        this.scoreSum = scoreSum;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "scoreInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", scoreSum='" + scoreSum + '\'' +
                ", scoreAvg='" + scoreAvg + '\'' +
                '}';
    }
}

