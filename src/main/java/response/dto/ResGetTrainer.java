package response.dto;

import com.epam.gymtaskapplication.model.Specialization;
import lombok.Data;

import java.util.List;

@Data
public class ResGetTrainer {
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Specialization specialization;
    private List<TraineeInList> traineesList;
}
