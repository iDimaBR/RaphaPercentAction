package com.github.idimabr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class ActionData {

    private Enum<?> type;
    private double chance;
    private List<String> commands;
    private int age;

    public boolean isFarm(){
        return age != -1;
    }
}
