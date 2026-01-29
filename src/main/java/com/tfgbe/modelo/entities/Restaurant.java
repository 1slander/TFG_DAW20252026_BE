package com.tfgbe.modelo.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;

@Getter
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "RESTAURANTS")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long idRestaurant;

    @Column(nullable = false, unique = true)
    private String cif;

    @Column(name = "restaurant_name", nullable = false, length = 255)
    private String restaurantName;

    private String address;
    private String country;
    private String phone;
    private Integer capacity;
    private Integer totalTables;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private Employee owner;
}
