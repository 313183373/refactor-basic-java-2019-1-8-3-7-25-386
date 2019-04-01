package practice3;

import java.math.BigDecimal;

class PriceCalculator {
    private Order order;

    public PriceCalculator(Order order) {
        this.order = order;
    }

    public BigDecimal calculate() {
        BigDecimal subTotal;

        // Total up line items
        subTotal = calcSubTotal();

        // Subtract discounts
        subTotal = subTotal.subtract(calcDiscounts());

        // calculate tax
        BigDecimal tax = subTotal.multiply(order.getTax());

        // calculate GrandTotal
        BigDecimal grandTotal = subTotal.add(tax);

        return grandTotal;
    }

    private BigDecimal calcDiscounts() {
        return order.getDiscounts().stream().reduce(BigDecimal::add).orElse(null);
    }

    private BigDecimal calcSubTotal() {
        return order.getOrderLineItemList().stream().map(OrderLineItem::getPrice).reduce(BigDecimal::add).orElse(null);
    }
}
