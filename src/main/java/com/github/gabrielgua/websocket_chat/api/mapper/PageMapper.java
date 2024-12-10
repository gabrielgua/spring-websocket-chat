package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PageMapper<T, R> {

    public PageResponse.PageResponseBuilder<R> pageResponseBuilder(Page<T> page) {
        return PageResponse.<R>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize());
    }

    public PageResponse<R> toPageResponse(Page<T> page, Function<T, R> converter) {
        List<R> content = page.getContent().stream()
                .map(converter)
                .toList();

        return pageResponseBuilder(page)
                .content(content)
                .build();
    }
}