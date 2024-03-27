package response.dto;

import com.epam.gymtaskapplication.model.Specialization;
import lombok.Data;

@Data
public class SimpleTrainer {
    private String username;
    private String firstName;
    private String lastName;
    private Specialization specialization;
}
