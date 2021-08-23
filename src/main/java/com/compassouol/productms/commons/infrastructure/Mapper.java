package com.compassouol.productms.commons.infrastructure;

public interface Mapper<S, T> {
    T map(S source);
}
