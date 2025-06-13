package ru.mirea.UserService.models;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "security")
public class Security {
    public Security(Industry industry, String code, String name){
        this.industry = industry;
        this.code = code;
        this.name = name;
    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "indId")
    private Industry industry;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "security", cascade = CascadeType.MERGE)
    private List<Quotes> quotesList;

    @OneToMany(mappedBy = "securityNews")
    private List<News> newsList;
}
