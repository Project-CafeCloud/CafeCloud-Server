package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review {
    private int cafe_id;
    private int user_id;
    private double rating;
    private String title;
    private String content;
}
