package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughtStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member youngjin = createMember();
        Item book = createBook("B123T", 10L, 10000);

        // when
        Long orderId = orderService.order(youngjin.getId(), book.getId(), 2);
        Order getOrder = orderRepository.findOne(orderId);

        // then
        // 주문 후 상태는 ORDER 이다.
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        // 주문의 건수는 개
        Assertions.assertEquals(1, getOrder.getOrderItems().size());
        // 주문시 가격은 주문수량 * 주문시점 가격
        Assertions.assertEquals(20000, getOrder.getTotalPrice());
        // 책 아이템의 재고가 잘 주는 지 확인
        Assertions.assertEquals(8, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("b123", 10L, 2);

        Long orderId = orderService.order(member.getId(), book.getId(), 2);
        // when
        orderService.cancelOrder(orderId);
        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(getOrder.getStatus(), OrderStatus.CANCEL);
        Assertions.assertEquals(book.getStockQuantity(), 10L);
    }


    @Test
    public void 상품주문_재고초과처리() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("B123T", 1L, 10000);
        // when
        Assertions.assertThrows(NotEnoughtStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), 5);
        });
    }

    private Item createBook(String itemName, long stockQuantity, int price) {
        Item book = new Book();
        book.setName(itemName);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member youngjin = Member.builder()
                .name("김영진")
                .address(new Address("busan", "marin", "123-12")).build();
        em.persist(youngjin);
        return youngjin;
    }

}