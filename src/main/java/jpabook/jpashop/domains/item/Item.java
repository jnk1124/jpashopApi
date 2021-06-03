package jpabook.jpashop.domains.item;

import jpabook.jpashop.domains.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비지니스로직
    public void addStock(int quantity){
        stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock =  stockQuantity - quantity;

        if(restStock < 0){
            throw new NotEnoughStockException("need more store");
        }

        this.stockQuantity = restStock;

    }
}
