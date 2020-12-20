import core.InputConstants;
import core.OutputConstants;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static core.files.reader.ReadExcelFile.*;
import static core.files.writer.WriteExcelFile.writeHeaderToExcelSheet;
import static core.files.writer.WriteExcelFile.writeRowToExcelSheet;

public class arrayTraining {
    String inputExcel = InputConstants.ARRAY_TRAINING_INPUT;
    String outputFolder = OutputConstants.TRAINING_OUTPUT;
    String outputExcel = outputFolder + "testing2" + ".xlsx";
    HashMap<String, ArrayList<ArrayList>> contents = new HashMap();

    @Test
    public void runTesting() {
        processExcel(inputExcel);
        writeResults(contents);

    }
    private void writeResults(HashMap<String, ArrayList<ArrayList>> contents) {
        for (HashMap.Entry<String, ArrayList<ArrayList>> entry : contents.entrySet()) {
            ArrayList<String> row = new ArrayList<>();
            row.add(entry.getKey());
            writeRowToExcelSheet(outputExcel, "Sheet", row);

            for (ArrayList x : entry.getValue()) {
                ArrayList<String> items = new ArrayList<>();
                items.add("");
                for (int counter = 0; counter < x.size(); counter++) {
                    items.add(x.get(counter).toString());
                    System.out.println(x.get(counter).toString());
                }
                writeRowToExcelSheet(outputExcel, "Sheet", items);
            }
        }
    }


    private void processExcel(String inputExcel) {
        int sheetsNo = getNumberOfSheets(inputExcel);
        for (int counter = 1; counter <= sheetsNo; counter++) {
            String sheetName = getSheetName(inputExcel, counter);
            processingSheet(sheetName);
        }

    }

    private void processingSheet(String sheetName) {
        ArrayList<ArrayList<String>> rows = getSheetData(inputExcel, sheetName);
        rows.remove(0);

        writeHeaderRow(outputExcel, "Sheet");
        for (ArrayList<String> row : rows) {
            processingRow(row);
        }
    }

    private void writeHeaderRow(String outputExcel, String sheetName) {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("SubTopic");
        headers.add("Paragraph");
        headers.add("Type");
        writeHeaderToExcelSheet(outputExcel, sheetName, headers);
    }

    private void processingRow(ArrayList<String> row) {
        String paragraph = row.size() > 0 ? row.get(0) : "";
        String subTopic = row.size() > 2 ? row.get(2) : "";
        String type = row.size() > 3 ? row.get(3) : "";
        boolean isExist = checkIfExist(subTopic);
        ArrayList<ArrayList> all = new ArrayList<>();
        ArrayList<String> pair = new ArrayList<>();
        pair.add(paragraph);
        pair.add(type);
        all.add(pair);
        if (isExist) {
            contents.get(subTopic).add(pair);
        } else
            contents.put(subTopic, all);
    }

    private boolean checkIfExist(String subTopic) {
        if (contents.keySet().contains(subTopic)) {
            return true;
        } else return false;
    }
}
