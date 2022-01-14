package com.prisma.library.library.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BooksBorrowedSearchParameter {

    private String startDate;
    private String endDate;
    private String name;
    private String firstName;
}
