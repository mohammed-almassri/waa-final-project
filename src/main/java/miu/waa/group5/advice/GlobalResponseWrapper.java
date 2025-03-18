package miu.waa.group5.advice;

import miu.waa.group5.dto.base.BaseResponse;
import miu.waa.group5.dto.base.ErrorResponse;
import miu.waa.group5.dto.base.PaginatedResponse;
import miu.waa.group5.dto.base.ValidationErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // Enable for all controller responses
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  org.springframework.http.MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if(body instanceof ErrorResponse || body instanceof ValidationErrorResponse || body instanceof BaseResponse<?>) {
            return body;
        }
        if (body instanceof Page) {
            Page<?> page = (Page<?>) body;

            Map<String, Object> meta = new HashMap<>();
            meta.put("totalPages", page.getTotalPages());
            meta.put("totalElements", page.getTotalElements());
            meta.put("currentPage", page.getNumber());

            return new PaginatedResponse<>(
                    "success",
                    page.getContent(),
                    meta
            );
        }

        return new BaseResponse<>("success",body);
    }
}
