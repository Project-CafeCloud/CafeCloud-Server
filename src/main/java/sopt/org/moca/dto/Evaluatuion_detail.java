package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Evaluatuion_detail {

    private int barista_id;
    private String  barista_name;
    private String  barista_title;
    private String barista_img_url;
    private  int evaluation_bean_condition;
    private String evaluation_bean_condition_comment;
    private  int evaluation_roasting;
    private String evaluation_roasting_comment;
    private  int evaluation_creativity ;
    private String evaluation_creativity_comment;
    private  int evaluation_reasonable;
    private String evaluation_reasonable_comment;
    private  int evaluation_consistancy;
    private String evaluation_consistancy_comment;
    private String evaluation_summary;
}
