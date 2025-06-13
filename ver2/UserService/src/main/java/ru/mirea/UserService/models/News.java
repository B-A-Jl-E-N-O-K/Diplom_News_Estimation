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
@AllArgsConstructor
@Table(name = "news")
public class News {
    public News(Security security, String link, String title, boolean est, String time, boolean iscomp){
        this.securityNews = security;
        this.link = link;
        this.title = title;
        this.est = est;
        this.time = time;
        this.iscomp = iscomp;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "secId")
    private Security securityNews;

    @Column(name = "link")
    private String link;

    @Column(name = "title")
    private String title;

    @Column(name = "est")
    private boolean est;

    @Column(name = "name")
    private String time;

    @Column(name = "iscomp")
    private boolean iscomp;

    @Column(name = "res")
    private int res;
}
