package cn.shaikuba.mock.data.entity.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {

    private List<String> errors;

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

}