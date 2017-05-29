package com.iitb.cse;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 *
 * @author sanchitgarg This class is used for holding information about an
 * experiment.
 *
 */
public class Experiment {

    @Getter
    @Setter
    int Number = -1;
    @Getter
    @Setter
    String Name = "";
    @Getter
    @Setter
    String Location = "IIT Bombay";
    @Getter
    @Setter
    String Description = "";
    @Getter
    @Setter
    String User = "";
    @Getter
    @Setter
    String FileName = "";
    @Getter
    @Setter
    int ReceivedFiles = 0;
    @Getter
    @Setter
    int SendLogRequest = 0;
    @Getter
    @Setter
    Date StartTime;

    /**
     * constructor
     */
    public Experiment(String a, String b, String c, String d, String e) {
        Name = a;
        Location = b;
        Description = c;
        User = d;
        FileName = e;
    }

    public Experiment(int exp_number, String exp_name, String exp_location, String exp_description) {
        Number = exp_number;
        Name = exp_name;
        Location = exp_location;
        Description = exp_description;
    }

    public int RFIncrement() {
        ReceivedFiles++;
        return ReceivedFiles;
    }

    public int SFIncrement() {
        SendLogRequest++;
        return SendLogRequest;
    }

    /**
     * prints the experiment information
     */
    public void print() {
        System.out.println("printing experiment details....");
        System.out.println("expID: " + Number);
        System.out.println("Name: " + Name);
        System.out.println("Location: " + Location);
        System.out.println("Description: " + Description);
        System.out.println("User: " + User);
        System.out.println("FileName: " + FileName);
    }

    /*
	* Initialize the start time of the experiment
     */
    void InitializeStartTime() {
        StartTime = new Date();
    }
}
