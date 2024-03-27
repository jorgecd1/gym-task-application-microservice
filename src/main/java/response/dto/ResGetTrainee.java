package response.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ResGetTrainee {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;
    private Set<TrainerInList> trainerList;
}
