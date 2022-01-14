package com.prisma.library.library.control.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prisma.library.library.control.repository.UserRepository;
import com.prisma.library.library.entity.enums.GenderEnum;
import com.prisma.library.library.entity.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void process_saveImportedUserList_whenParamsValid() {
        //given
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("test_Name");
        user.setGender(GenderEnum.MALE);
        user.setFirstName("test_FirstName");
        userList.add(user);

        //when
        when(userRepository.findAll()).thenReturn(userList);

        userService.saveImportedUserList(userList);

        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(2)).saveAll(any());

    }

    @Test
    void process_findUserByNameAndFirstName_whenParamsValid() {
        //given
        User user = new User();
        user.setName("test_Name");
        user.setGender(GenderEnum.MALE);
        user.setFirstName("test_FirstName");

        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findByNameAndFirstName("test_Name", "test_FirstName")).thenReturn(optionalUser);

        User result = userService.findUserByNameAndFirstName("test_Name", "test_FirstName");

        assertEquals(user, result);
    }
}
