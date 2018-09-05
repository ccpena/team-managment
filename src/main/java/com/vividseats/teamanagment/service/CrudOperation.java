package com.vividseats.teamanagment.service;

import java.util.Optional;

public interface CrudOperation<T> {

  Optional<T> findById(Long id);

  Optional<T> create(T object);

}
