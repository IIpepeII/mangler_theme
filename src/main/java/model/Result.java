package model;

public class Result {
    int id;
    String firstName;
    String lastName;
    int result;
    String startTime;
    String endTime;

    public Result(int id, String firstName, String lastName, int result, String startTime, String endTime){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
