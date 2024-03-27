package request.dto;

import com.epam.gymtaskapplication.model.Specialization;
import lombok.Data;
import response.dto.TraineeInList;

import java.util.List;

@Data
public class ReqUpdateTrainer {
    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String specializationName;
    private boolean isActive;
    private List<TraineeInList> trainees;
}
