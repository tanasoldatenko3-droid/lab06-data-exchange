package by.bseu.pp.lab06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public final class CsvSalesIO {
    public static final String HEADER = "id;date;customer;category;amount";
    private CsvSalesIO() { }

    /** TODO 2. Ровно пять полей. split с -1 сохраняет пустое последнее поле. */
    public static Sale parseLine(String line, int lineNumber) {
        // TODO 2: реализуйте по условию лабораторной работы.
        if (line==null || line.isEmpty()) {
            throw new IllegalArgumentException("строка не может быть null и пустой");
        }
        if (lineNumber<=0) {
            throw new IllegalArgumentException("физический номер не можеть быть 0 и отрицательным");
        }

        String[] parts = line.split(";", -1);
        if (parts.length != 5) {
            throw new DataFormatException("CSV строка " + lineNumber + ": ожидалось 5 полей, получено " + parts.length, (Throwable) null);
        }
        String id = parts[0];
        String dateText = parts[1];
        String customer = parts [2];
        String category = parts [3];
        String amountText = parts[4];

        if (!amountText.matches("[0-9]+(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?")) {
            throw new DataFormatException("CSV строка " + lineNumber + ": amount: некорректная сумма", (Throwable) null);
        }
        try {
            double amount = Double.parseDouble(amountText);
            LocalDate date = DateParser.parseDate(dateText);
            return new Sale (id,date,customer,category,amount);
        } catch (IllegalArgumentException e) {
            throw new DataFormatException("CSV строка " + lineNumber + ": " + e.getMessage(), e);
        }
    }

    /** TODO 3. UTF-8, обязательный заголовок, BOM допустим только перед заголовком.
     * Пустые строки пропускаются. При ошибке импорт целиком отклоняется. */
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);

        List<Sale> sales = new ArrayList<>();
        // TODO 3: реализуйте по условию лабораторной работы.
        try (BufferedReader reader = Files.newBufferedReader(path,StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new DataFormatException("CSV строка 1: файл пустой, отсутствует заголовок", (Throwable) null);
            }
            if (headerLine.startsWith("\uFEFF")) {
                headerLine = headerLine.substring(1);
            }
            if (!HEADER.equals(headerLine)) {
                throw new DataFormatException("CSV строка 1: неверный заголовок: " + headerLine, (Throwable) null);
            }
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) !=null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                sales.add(parseLine(line,lineNumber));
            }
        }
        return sales;
    }

    /** TODO 4. Проверки до открытия файла. Создать папки, перезаписать файл в UTF-8.
     * Дата ISO, сумма через Double.toString, порядок и повторы сохраняются. */
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        // TODO 4: реализуйте по условию лабораторной работы.
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path,StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();

            for (Sale sale : sales) {
                writer.write(
                        sale.getId() + ";"
                        + sale.getDate().toString() + ";"
                        + sale.getCustomer() + ";"
                        + sale.getCategory() + ";"
                        + Double.toString(sale.getAmount())
                );
                writer.newLine();
            }
        }
    }
}
