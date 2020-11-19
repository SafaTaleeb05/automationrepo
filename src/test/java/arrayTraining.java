import core.InputConstants;
import core.OutputConstants;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static core.files.reader.ReadExcelFile.*;
import static core.files.writer.WriteExcelFile.writeHeaderToExcelSheet;
import static core.files.writer.WriteExcelFile.writeRowToExcelSheet;

public class arrayTraining {
    String inputExcel = InputConstants.ARRAY_TRAINING_INPUT;
    String outputFolder = OutputConstants.TRAINING_OUTPUT;
    String latestSubTopic="";

    @Test
    public void runTesting() {
        processExcel(inputExcel);

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
        String outputExcel = outputFolder + "testing"+".xlsx";
        writeHeaderRow(outputExcel, "Sheet");
        for (ArrayList<String> row : rows) {
            ArrayList<String> result = processingRow(row);
            writeRowToExcelSheet(outputExcel, "Sheet", result);
        }
    }
    private void writeHeaderRow(String outputExcel, String sheetName) {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("SubTopic");
        headers.add("Paragraph");
        headers.add("Type");
        writeHeaderToExcelSheet(outputExcel, sheetName, headers);
    }

    private ArrayList<String> processingRow(ArrayList<String> row) {
        ArrayList<String> formattedRow= new ArrayList<>();
        String paragraph= row.size() > 0 ?row.get(0):"";
        String subTopic= row.size() > 2 ?row.get(2):"";
        String type=row.size() > 3 ?row.get(3):"";
        System.out.println("Working on"+subTopic+"in"+type);
        if (!latestSubTopic.equals(subTopic)){
            formattedRow.add(subTopic);
            latestSubTopic=subTopic;
        }else
            formattedRow.add("");

        formattedRow.add(paragraph);
        formattedRow.add(type);

        return formattedRow;
    }


}
