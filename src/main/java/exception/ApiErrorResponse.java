package exception;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
}
