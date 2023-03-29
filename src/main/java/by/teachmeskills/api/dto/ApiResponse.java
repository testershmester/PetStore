package by.teachmeskills.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String code;
    private String type;
    private String message;
}
