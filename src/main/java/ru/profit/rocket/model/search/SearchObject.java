package ru.profit.rocket.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchObject<T> {
    private T value;
    private Type type;

    public SearchObject<T> get(){
        return this;
    }

    public enum Type{
       EQUALS,CONTAINS
    }
}
