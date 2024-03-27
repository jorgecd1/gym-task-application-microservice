package request.dto;

import lombok.Data;

@Data
public class ReqChangeLogin {
    private String username;
    private String oldPassword;
    private String newPassword;
}
