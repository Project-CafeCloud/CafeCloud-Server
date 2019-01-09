package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewAlarm {
    private String alarm_user;
    private String alarm_contents;
}
