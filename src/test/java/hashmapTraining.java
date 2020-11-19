import core.InputConstants;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static core.files.reader.ReadExcelFile.*;

public class hashmapTraining {
    String inputExcel = InputConstants.HASHMAP_TRAINING_INPUT;
    String latestSubTopic="";
    HashMap<String, ArrayList<String>> contents=new HashMap();

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
        for (ArrayList<String> row : rows) {
            processingRow(row);
        }
    }

    private void processingRow(ArrayList<String> row) {
        String paragraph= row.size() > 0 ?row.get(0):"";
        String subTopic= row.size() > 1 ?row.get(1):"";
        String type=row.size() > 2 ?row.get(2):"";
        ArrayList<String> pair=new ArrayList<>();
        pair.add(paragraph);
        pair.add(type);

        if (!latestSubTopic.equals(subTopic)){
            latestSubTopic=subTopic;
            contents.put(subTopic, pair);
            System.out.println(contents.get(subTopic));
        }else
            contents.get(subTopic).add(String.valueOf(pair));
    }

}
