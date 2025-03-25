package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String userName;
    private String nickName;
    private String email;
    private String status;
    private String phonenumber;
    private String sex;
    private List<Long> roleIds;
}
