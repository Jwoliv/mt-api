package com.mt.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageElementsResponse<T> {
    private List<T> elements;
    private Boolean isNextPage;
    private Boolean isPrevPage;

    @Builder
    public static <T> PageElementsResponse<T> create(List<T> elements, Boolean isNextPage, Boolean isPrevPage) {
        PageElementsResponse<T> response = new PageElementsResponse<>();
        response.setElements(elements);
        response.setIsNextPage(isNextPage);
        response.setIsPrevPage(isPrevPage);
        return response;
    }
}