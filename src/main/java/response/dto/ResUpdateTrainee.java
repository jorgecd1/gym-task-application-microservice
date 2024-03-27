package response.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResUpdateTrainee {
    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerInList> trainerList;
}
