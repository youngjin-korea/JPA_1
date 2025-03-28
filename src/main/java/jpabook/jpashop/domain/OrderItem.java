package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // static 메서드로만 생성하기 위해 jpa에서 지원하는 최대의 제한 레벨인 protected 접근 레벨로 기본 생성자 생성
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    // == 생성 메서드 (orderItem을 먼저 만들고 오더가 만들어짐)
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    // == 비지니스 로직
    public void cancel() {
        // item의 수량을 원복 시키기
        this.getItem().addStock(this.count);
    }

    // == 수량 * 가격 => 총 가격
    public int getTotalPrice() {
        return this.getCount() * this.getOrderPrice();
    }
}
