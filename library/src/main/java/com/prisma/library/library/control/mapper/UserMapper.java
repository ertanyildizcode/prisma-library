package com.prisma.library.library.control.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.entity.enums.GenderEnum;
import com.prisma.library.library.entity.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = GenderEnum.class)
public interface UserMapper {

    static UserMapper getInstance() {
        return Mappers.getMapper(UserMapper.class);
    }

    UserDto userToUserDto(User user);
}
