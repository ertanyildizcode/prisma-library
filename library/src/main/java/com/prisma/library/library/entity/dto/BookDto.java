package com.prisma.library.library.entity.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto implements Serializable {

    private String title;

    private String author;

    private String genre;

    private String publisher;
}
