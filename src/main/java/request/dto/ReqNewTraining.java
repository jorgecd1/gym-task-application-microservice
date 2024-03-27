package request.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReqNewTraining {
    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private Date trainingDate;
    private int trainingDuration;
}
