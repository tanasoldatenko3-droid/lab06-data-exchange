package by.bseu.pp.lab06;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class JsonSalesIO {
    private JsonSalesIO() { }
    /** TODO 5. Читать UTF-8 через JsonSupport.parseArray и toSale.
     * Нумерация записей с 1; порядок и повторы сохраняются. */
    public static List<Sale> read(Path path) throws IOException {
        Checks.path(path);
        // TODO 5: реализуйте по условию лабораторной работы.
        List <Sale> sales = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonArray array = JsonSupport.parseArray(reader);
            int number = 1;
            for (JsonElement element : array) {
                sales.add(JsonSupport.toSale(element, number));
                number ++;
            }
        }
        return sales;
    }

    /** TODO 6. Проверить аргументы до открытия. Собрать JsonArray через toJson,
     * создать папки и записать JSON через GSON.toJson(array, writer). */
    public static void write(Path path, List<Sale> sales) throws IOException {
        Checks.path(path);
        Checks.sales(sales);
        // TODO 6: реализуйте по условию лабораторной работы.
        JsonArray array = new JsonArray();
        for (Sale sale : sales) {
            array.add(JsonSupport.toJson(sale));
        }
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            JsonSupport.GSON.toJson(array, writer);
        }
    }
}
