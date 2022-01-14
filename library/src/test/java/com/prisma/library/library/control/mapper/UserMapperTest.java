package com.prisma.library.library.control.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.entity.enums.GenderEnum;
import com.prisma.library.library.entity.model.User;

class UserMapperTest {


    @Test
    void bookToBookDto() {
        //given
        User user = new User();
        user.setName("test_Name");
        user.setGender(GenderEnum.MALE);
        user.setFirstName("test_FirstName");

        UserDto userDto = new UserDto();
        userDto.setName("test_Name");
        userDto.setGender("MALE");
        userDto.setFirstName("test_FirstName");
        //
        UserDto result = UserMapper.getInstance().userToUserDto(user);

        assertNotNull(result);
        assertEquals(userDto, result);
    }
}
