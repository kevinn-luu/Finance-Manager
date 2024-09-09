package persistence;


import model.Event;
import model.EventLog;
import model.MonthlyExpenseGoal;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// A reader that reads information about monthly expense goal from file
public class MonthlyGoalJsonReader {
    private String file;

    // EFFECTS: constructs a reader to read information from the specified file
    public MonthlyGoalJsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: reads information from file and returns a MonthlyExpenseGoal object
    //          throws IOException if data cannot be read
    public MonthlyExpenseGoal read() throws IOException {
        String jsonData = readFile(this.file);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Monthly goal has been loaded from file."));
        return parseMonthlyExpenseGoal(jsonObject);
    }

    // Function taken from JsonSerializationDemo provided by CPSC 210
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder build = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> build.append(s));
        }

        return build.toString();
    }

    // EFFECTS: turns jsonObject into a MonthlyExpenseGoal object
    private MonthlyExpenseGoal parseMonthlyExpenseGoal(JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        int month = jsonObject.getInt("month");

        MonthlyExpenseGoal goal = new MonthlyExpenseGoal(amount);
        goal.setMonth(month);
        return goal;
    }


}
