package request.dto;

import lombok.Data;

@Data
public class ReqGetUser {
    private String username;
    private String password;
}
