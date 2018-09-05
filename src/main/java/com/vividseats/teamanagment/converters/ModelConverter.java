package com.vividseats.teamanagment.converters;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface ModelConverter<D,E> {

	Optional<D> convertToDTO(Optional<E> entiy);
	
	Optional<E> convertToEntity(Optional<D> dto);
}
