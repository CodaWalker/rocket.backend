package ru.profit.rocket.mapper;

import org.mapstruct.IterableMapping;
import ru.profit.rocket.dto.BaseDTO;
import ru.profit.rocket.model.BaseEntity;

import java.util.List;

public interface CustomMapper<D extends BaseDTO,E extends BaseEntity>{
    D toDTO(E entity);
    E toEntity(D dto);
    @IterableMapping(qualifiedByName = "toDto")
    Iterable<D> toDTOCollection(Iterable<E> entityCollection);
    List<D> toDTOList(List<E> entityCollection);
    @IterableMapping(qualifiedByName = "toEntity")
    Iterable<E> toEntityCollection(Iterable<D> dtoCollection);
}
