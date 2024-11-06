package kr.co.boardproject.handler;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private T result;
    private PageMetadata pageMetadata;

    public ApiResponse(String status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(String status, String message, T result, PageMetadata pageMetadata) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.pageMetadata = pageMetadata;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>("200", "정상처리완료", result);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("200", "정상처리완료", null);
    }

    public static <T> ApiResponse<List<T>> successList(List<T> resultList) {
        return new ApiResponse<>("200", "정상처리완료", resultList);
    }

    public static <T> ApiResponse<List<T>> successPage(Page<T> page) {
        PageMetadata pageMetadata = new PageMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return new ApiResponse<>("200", "정상처리완료", page.getContent(), pageMetadata);
    }

    public static <T> ApiResponse<T> error(String status, String message) {
        return new ApiResponse<>(status, message, null);
    }


    @Data
    public static class PageMetadata {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

        public PageMetadata(int page, int size, long totalElements, int totalPages) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }
    }
}
