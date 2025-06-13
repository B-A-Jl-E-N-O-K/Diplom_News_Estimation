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
@Table(name = "industry")
public class Industry {
    public Industry(Source source, String name, String namerus,String descr){
        this.source = source;
        this.namerus = namerus;
        this.name = name;
        this.descr = descr;
    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sourceid")
    private Source source;

    @Column(name = "name")
    private String name;

    @Column(name = "namerus")
    private String namerus;

    @Column(name = "descr")
    private String descr;

    @OneToMany(mappedBy = "industry", fetch = FetchType.EAGER)
    private List<Security> securityList;

}
