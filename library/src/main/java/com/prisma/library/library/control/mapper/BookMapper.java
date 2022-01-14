package com.prisma.library.library.control.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.prisma.library.library.entity.dto.BookDto;
import com.prisma.library.library.entity.model.Book;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    static BookMapper getInstance() {
        return Mappers.getMapper(BookMapper.class);
    }

    BookDto bookToBookDto(Book book);
}
