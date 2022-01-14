package com.prisma.library.library.entity.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto implements Serializable {

    private String name;

    private String firstName;

    private Timestamp memberSince;

    private Timestamp memberTill;

    private String gender;
}
