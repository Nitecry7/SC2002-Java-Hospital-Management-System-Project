
import java.util.Map;

public class TablePrinter {

    public TablePrinter() {
    }

    // Method to print the table
    public static void printTable(Map<String, String[]> data, String[] headers) {
        int[] columnWidths = new int[headers.length];

        // Calculate the maximum width for each column
        for (int i = 0; i < headers.length; ++i) {
            columnWidths[i] = headers[i].length(); // Start with the header length

            for (String[] row : data.values()) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length()); // Update width based on the data row
            }
        }

        // Print the separator line before the table
        printSeparator(columnWidths);

        // Print the headers
        System.out.print("| ");
        for (int i = 0; i < headers.length; ++i) {
            System.out.print(String.format("%-" + columnWidths[i] + "s | ", headers[i])); // Print header with padding
        }
        System.out.println();

        // Print the separator line after the headers
        printSeparator(columnWidths);

        // Print the data rows
        for (String[] row : data.values()) {
            System.out.print("| ");
            for (int i = 0; i < row.length; ++i) {
                System.out.print(String.format("%-" + columnWidths[i] + "s | ", row[i])); // Print row with padding
            }
            System.out.println();
        }

        // Print the bottom separator line
        printSeparator(columnWidths);
    }

    // Helper method to print the separator line
    private static void printSeparator(int[] columnWidths) {
        System.out.print("+");
        for (int width : columnWidths) {
            System.out.print("-".repeat(width + 2) + "+"); // Add extra space before the `|`
        }
        System.out.println();
    }
}
