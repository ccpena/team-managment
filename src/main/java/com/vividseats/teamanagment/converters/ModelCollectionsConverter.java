package com.vividseats.teamanagment.converters;

import java.util.Set;

public interface ModelCollectionsConverter<T, E> {

  public Set<T> convertTo(Set<E> members);


}
