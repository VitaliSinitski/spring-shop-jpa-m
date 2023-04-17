package com.vitali.database.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    private Integer quantity;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
    // в sql указать cart_id int references cart(id) on delete cascade
    // в таком случае бд удалит все orderItems при удалении cart
    // лучше здесь ничего не ставить, а использовать delete cascade на уровне sql, а не hibernate

    // Order будемо создавать когда мы выберем orderItems и поместим их в order
//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)

    @ManyToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Order order;

    //
    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }



    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
//    @ManyToOne(optional = false)                                // изменил 03-15:17-40
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;




//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        OrderItem item = (OrderItem) o;
//        return getId() != null && Objects.equals(getId(), item.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
