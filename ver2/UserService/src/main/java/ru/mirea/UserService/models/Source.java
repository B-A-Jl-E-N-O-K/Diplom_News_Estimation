package ru.mirea.UserService.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "source")
public class Source {
    public Source(String region, String name, String stock, String regime, String descr){
        this.region = region;
        this.name = name;
        this.stock = stock;
        this.regime = regime;
        this.descr = descr;
    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "region")
    private String region;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private String stock;

    @Column(name = "regime")
    private String regime;

    @Column(name = "descr")
    private String descr;

    @OneToMany(mappedBy = "source")
    private List<Industry> industryList;
}
