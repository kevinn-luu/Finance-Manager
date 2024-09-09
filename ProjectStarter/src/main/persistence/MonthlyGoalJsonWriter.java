package persistence;

import model.Event;
import model.EventLog;
import model.MonthlyExpenseGoal;

import java.io.*;

import org.json.JSONObject;

// writer that will convert MonthlyExpenseGoal objects into strings and save
// them to file
public class MonthlyGoalJsonWriter {
    private static int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer that will save objects to the specified destination
    public MonthlyGoalJsonWriter(String destination) {
        this.destination = destination;
    }  

    // MODIFIES: this
    // EFFECTS: opens file for writing. If file doesn't exist, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }

    // MODIFIES: this
    // EFFECTS: saves JSON representation of mg to file
    public void write(MonthlyExpenseGoal mg) {
        JSONObject json = mg.toJson();
        saveToFile(json.toString(INDENT));
        EventLog.getInstance().logEvent(new Event("Monthly goal has been saved to file."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: saves string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
