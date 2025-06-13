package ru.mirea.UserService.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quotes")
@IdClass(QuotesPK.class)
public class Quotes {
    public Quotes(Security security, String date, float value){
        this.security = security;
        this.date = date;
        this.value = value;
    }

    @Id
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "secId")
    private Security security;

    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "value")
    private float value;
}
