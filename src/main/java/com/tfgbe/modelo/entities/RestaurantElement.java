package com.tfgbe.modelo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_element")
    private Long idElement;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "pos_x", nullable = false)
    private Integer posX;

    @Column(name = "pos_y", nullable = false)
    private Integer posY;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "rotation")
    @Builder.Default
    private Integer rotation = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private Restaurant restaurant;
}
