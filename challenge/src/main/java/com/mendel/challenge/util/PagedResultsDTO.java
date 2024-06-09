package com.mendel.challenge.util;

import java.util.List;

public class PagedResultsDTO<T> {
    private final PageDTO page;
    private final List<T> result;

    public PagedResultsDTO(int offset, int limit, int total, List<T> result) {
        this.page = new PageDTO(offset, limit, total);
        this.result = result;
    }

    public PageDTO getPage() {
        return page;
    }

    public List<T> getResult() {
        return result;
    }
}
