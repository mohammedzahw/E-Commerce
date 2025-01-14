package com.example.e_commerce.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    @OneToOne(fetch = FetchType.EAGER)
    private UserImage userImage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL })
    @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    private List<Product> cart;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL })
    @JoinTable(name = "wishlist", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    private List<Product> wishList;

    @OneToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "user")
    @ToString.Exclude
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "user")
    @ToString.Exclude
    private List<Phone> phones;

    @OneToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "user")
    @ToString.Exclude
    private List<Notification> notifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "user")
    @ToString.Exclude
    private List<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY, cascade = { jakarta.persistence.CascadeType.ALL }, mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;

}
