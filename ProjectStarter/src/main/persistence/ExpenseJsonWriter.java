// all code in in this class is roughly modeled from JsonSerializationDemo provided by CPSC210

package persistence;

import model.Event;
import model.EventLog;
import model.Expense;

import java.io.*;

import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;

// a writer that will save Expenses to file
public class ExpenseJsonWriter {
    private static int INDENT = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: creates a writer object with specified destination path to file
    public ExpenseJsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens file for writing, throws FileNotFoundException if file cannot
    //          be found
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of an expense array to file
    public void write(List<Expense> expenses) {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : expenses) {
            JSONObject json = e.toJson();
            jsonArray.put(json);
        }

        saveToFile(jsonArray.toString(INDENT));
        EventLog.getInstance().logEvent(new Event("Expenses saved to file."));

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }


}   
