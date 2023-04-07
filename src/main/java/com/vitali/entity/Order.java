package com.vitali.entity;

import com.vitali.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

//    private String inform;

    // TODO: 08.04.2023 make default value of OrderStatus - pending
    // TODO: 08.04.2023 make variable or aspect with updatedDate of Order. It will be done by changing status of Order
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //    @Builder.Default
//    @ToString.Exclude
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // 03-15:01-00 не мог сохранить order и убрал merge
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @JoinColumn()
    private List<OrderItem> orderItems = new ArrayList<>();

    //
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

//
//    public BigDecimal getTotalCost() {
//        BigDecimal cost = BigDecimal.ZERO;
//        if (orderItems != null) {
//            for (OrderItem item : orderItems) {
//                cost = cost.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
//            }
//        }
//        return cost;
//    }
    
}
