package request.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReqUpdateTrainee {
    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String address;
    private Date dateOfBirth;
    private boolean isActive;
}
