package ru.mirea.UserService.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDAO {
    private String secname;
    private String link;
    private String title;
    private boolean est;
    private String time;
    private int res;
}
