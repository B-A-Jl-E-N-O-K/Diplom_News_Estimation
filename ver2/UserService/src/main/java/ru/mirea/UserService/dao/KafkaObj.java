package ru.mirea.UserService.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaObj {

    private int index;
    private String link;
    private String datetime;
    private String title_out;
    private String company;
    private String industry;
    private int class_news;

}
