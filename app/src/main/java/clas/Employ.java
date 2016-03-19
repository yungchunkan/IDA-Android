package clas;

public class Employ {
    private String employid;
    private String position;
    private String responsibilities;
    private String requirement;
    private String term;
    private String salary;
    private String quota;
    private String starttime;
    private String endtime;
    private String restid;
    private Boolean isPartTime;
    private String restName;

    public Employ(String employid, String position, String responsibilities, String requirement, String term, String salary, String quota, String starttime, String endtime, String restid, Boolean isPartTime) {
        this.employid = employid;
        this.position = position;
        this.requirement = requirement;
        this.responsibilities = responsibilities;
        this.term = term;
        this.salary = salary;
        this.quota = quota;
        this.starttime = starttime;
        this.endtime = endtime;
        this.restid = restid;
        this.isPartTime = isPartTime;
        //this.restName = restname;
    }

    public Employ(String employid, String position, String restName) {
        this.employid = employid;
        this.position = position;
        this.restName = restName;
    }

    public String getEmployid() {
        return employid;
    }

    public String getPosition() {
        return position;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public String getTerm() {
        return term;
    }

    public String getSalary() {
        return salary;
    }

    public String getQuota() {
        return quota;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getRestid() {
        return restid;
    }

    public Boolean getIsPartTime() {
        return isPartTime;
    }

    public String getRestname() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }
}
