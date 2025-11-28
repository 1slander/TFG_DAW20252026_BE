package com.tfgbe.modelo.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
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

    // PK: Mapea a VARCHAR(255). No usamos @GeneratedValue ya que es un String.
    // Usaremos un valor estático ('CASA_PACO') o UUID si la DB lo soporta, ya que es un solo restaurante.
    @Id 
    @Column(name = "ID_RESTAURANT", length = 255)
    private String idRestaurant; 

    @Column(name = "RESTAURANT_NAME", nullable = false, length = 255)
    private String restaurantName;   
    private String address;    
    private String country;    
    private String phone;
    private Integer capacity;
    private Integer totalTables;
    
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private Admin admin;
    
    // Relación One-to-Many: Un restaurante tiene muchas mesas (Opcional, pero recomendado)
    // @OneToMany(mappedBy = "restaurant")
    // private List<Table> tables;
}