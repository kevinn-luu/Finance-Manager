// all code in in this class is roughly modeled from JsonSerializationDemo provided by CPSC210

package persistence;

import model.Event;
import model.EventLog;
import model.Expense;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

// A reader that reads information about expenses from a file
public class ExpenseJsonReader {
    private String file;

    // EFFECTS: creates a reader that will read information from the
    //          specified file
    public ExpenseJsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: reads expenses from file and returns list of expenses
    //          throws IOException if data cannot be read
    public List<Expense> read() throws IOException {
        String jsonData = readFile(this.file);
        JSONArray jsonArray = new JSONArray(jsonData);
        EventLog.getInstance().logEvent(new Event("Expenses read from file."));
        return parseExpenses(jsonArray);
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

    // EFFECTS: parses expenses from json array and returns them as a list of expenses
    private List<Expense> parseExpenses(JSONArray jsonArray) {
        List<Expense> expenses = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Expense e = parseExpense(jsonObject);
            expenses.add(e);
        }

        return expenses;
    }

    // EFFECTS: parses a single expense from jsonObject and returns it
    private Expense parseExpense(JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String description = jsonObject.getString("description");
        String date = jsonObject.getString("date");
        int month = jsonObject.getInt("month");

        return new Expense(amount, description, date, month);
    }

}