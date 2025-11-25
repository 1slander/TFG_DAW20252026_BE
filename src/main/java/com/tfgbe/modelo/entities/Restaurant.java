package com.tfgbe.modelo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(name = "RESTAURANTS") // Mapea a la tabla RESTAURANTS en la base de datos
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

    // Relación Many-to-One: ID_MANAGER (FK a la tabla USERS)
    // Usamos el nombre 'manager' en Java para mayor claridad
    @ManyToOne 
    @JoinColumn(name = "ID_MANAGER") 
    private User manager; 
    
    // Relación One-to-Many: Un restaurante tiene muchas mesas (Opcional, pero recomendado)
    // @OneToMany(mappedBy = "restaurant")
    // private List<Table> tables;
}