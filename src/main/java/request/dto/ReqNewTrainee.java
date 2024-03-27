package request.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReqNewTrainee {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
}
