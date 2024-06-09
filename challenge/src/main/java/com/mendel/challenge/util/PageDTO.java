package com.mendel.challenge.util;

import java.util.List;

public class PageDTO {
    private final int offset;
    private final int limit;
    private final int total;

    public PageDTO(int offset, int limit, int total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }
    public int getLimit() {
        return limit;
    }
    public int getTotal() {
        return total;
    }
}
