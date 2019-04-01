package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items).subtract(calculateDiscountTotal(products, items));
        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal calculateDiscountTotal(List<Product> products, List<OrderItem> items) {
        return products.stream().map(product -> {
            OrderItem orderItem = findOrderItemByProduct(items, product);
            return product.getPrice()
                    .multiply(new BigDecimal(orderItem.getCount()))
                    .multiply(product.getDiscountRate());
        }).reduce(BigDecimal::add).orElse(null);
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        return items.stream()
                .filter(orderItem -> orderItem.getCode() == product.getCode())
                .findFirst().orElse(null);
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        return products.stream().map(product -> {
            OrderItem orderItem = findOrderItemByProduct(items, product);
            return product.getPrice().multiply(new BigDecimal(orderItem.getCount()));
        }).reduce(BigDecimal::add).orElse(null);
    }

}
