package sopt.org.moca.model;


import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryParam {

    private int[] concept;
    private int[] menu;
}
