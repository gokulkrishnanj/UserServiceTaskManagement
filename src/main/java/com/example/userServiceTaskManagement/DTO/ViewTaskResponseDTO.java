package com.example.userServiceTaskManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewTaskResponseDTO {
    private Integer pageNumber;
    private Integer itemsPerPage;
    private List<TaskResponseDTO> taskResponseDTOList;
    private List<String> taskStatusList;
}
