package com.csi.itaca.tools.utils.beaner;

import java.util.List;

public interface Beaner {
    <T> T transform(Object var1, Class<T> var2);

    <T> List<T> transform(List<?> var1, Class<T> var2);

    void update(Object var1, Object var2);

    void addPackageToScan(String... var1);
}

