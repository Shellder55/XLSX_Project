package project.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import project.dto.NumberResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class NumberService {
    public NumberResponse findNthMaxNumber(String pathToFile, int position) throws IOException {
        int[] result = new int[position];
        int numberOfColumns = 0;
        File file = new File(pathToFile);

        if (!file.exists()) throw new FileNotFoundException(pathToFile);

        for (int i = 0; i < position; i++) result[i] = Integer.MIN_VALUE;

        try (FileInputStream fis = new FileInputStream(pathToFile);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            numberOfColumns = getNumberOfColumns(result, numberOfColumns, sheet);
        }
        return getIntegerResponseEntity(pathToFile, position, result, numberOfColumns);
    }

    private NumberResponse getIntegerResponseEntity(String pathToFile, int position, int[] result, int numberOfColumns) throws IOException {
        if (numberOfColumns == 0) {
            throw new FileNotFoundException(pathToFile);
        } else if (position > numberOfColumns) {
            throw new IllegalArgumentException("Maximum number of numbers: " + numberOfColumns + ". " + "Your position: " + position);
        } else {
            return new NumberResponse(result[position - 1]);
        }
    }

    private int getNumberOfColumns(int[] result, int numberOfColumns, Sheet sheet) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    insertToPosition(result, (int) cell.getNumericCellValue());
                    numberOfColumns++;
                }
            }
        }
        return numberOfColumns;
    }

    private void insertToPosition(int[] result, int number) {
        for (int i = 0; i < result.length; i++) {
            if (number > result[i]) {
                for (int j = result.length - 1; j > i; j--) {
                    result[j] = result[j - 1];
                }
                result[i] = number;
                break;
            }
        }
    }
}