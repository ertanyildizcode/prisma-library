package com.prisma.library.library.control.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prisma.library.library.control.repository.BorrowRepository;
import com.prisma.library.library.entity.dto.UserDto;
import com.prisma.library.library.entity.enums.GenderEnum;
import com.prisma.library.library.entity.model.Book;
import com.prisma.library.library.entity.model.Borrow;
import com.prisma.library.library.entity.model.User;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @InjectMocks
    private BorrowServiceImpl borrowService;

    @Mock
    private BorrowRepository borrowRepository;

    private List<Borrow> borrowList;

    @BeforeEach
    public void setUp() {
        //Given
        Book book = new Book();
        book.setPublisher("test_publisher");
        book.setGenre("test_genre");
        book.setAuthor("test_author");
        book.setTitle("test_title");

        User user = new User();
        user.setName("test_Name");
        user.setGender(GenderEnum.MALE);
        user.setFirstName("test_FirstName");

        borrowList = new ArrayList<>();
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrowList.add(borrow);
    }

    @Test
    void process_findBorrowedUsersAtLeastOne_whenParamsValid() {

        //when
        when(borrowRepository.findAll()).thenReturn(borrowList);

        Set<UserDto> userDtoSet = borrowService.findBorrowedUsersAtLeastOne();

        Assertions.assertEquals(1, userDtoSet.size());
    }

    @Test
    void process_findNonBorrowedUsers_whenParamsValid() {

        //when
        when(borrowRepository.findBorrowsToExpireDate(Mockito.any())).thenReturn(borrowList);

        Set<UserDto> userDtoSet = borrowService.findNonBorrowedUsers();

        Assertions.assertEquals(1, userDtoSet.size());
    }
}
