package co.grove.storefinder.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Source:
 * https://stackoverflow.com/a/38415815/2171147
 */
public class CSVFile {
    BufferedReader reader;

    public CSVFile(BufferedReader reader) {
        this.reader = reader;
    }

    public List<Store> parseStoreList() {
        List<Store> resultList = new ArrayList<>();
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                try {
                    Store store = Store.Companion.createStoreFromCsvRow(row);
                    resultList.add(store);
                } catch (NumberFormatException ignored) {

                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        return resultList;
    }

}
