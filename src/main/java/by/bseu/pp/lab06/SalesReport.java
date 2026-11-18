package by.bseu.pp.lab06;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public final class SalesReport {
    private SalesReport() { }
    /** TODO 7. Включить обе даты. Вернуть самостоятельный список, сохраняя порядок и повторы. */
    public static List<Sale> filterByPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        Checks.sales(sales);
        Checks.period(from, to);

        // TODO 7: реализуйте по условию лабораторной работы.
        List<Sale> result = new ArrayList<>();
        for (Sale sale : sales) {
            LocalDate date = sale.getDate();
            if (!date.isBefore(from) && !date.isAfter(to)) {
                result.add(sale);
            }
        }
        return result;
    }

    /** TODO 8. Сложить getAmount без округления. Пустой список даёт 0. */
    public static double totalAmount(List<Sale> sales) {
        Checks.sales(sales);

        // TODO 8: реализуйте по условию лабораторной работы.
        double total = 0.0;
        for (Sale sale : sales) {
            total += sale.getAmount();
        }
        return total;
    }
}
