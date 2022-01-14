package com.prisma.library.library.interfaces;

import java.util.List;

import com.prisma.library.library.entity.model.User;

public interface UserService extends CsvReadingExecution<List<User>> {

    void saveImportedUserList(List<User> userList);

    User findUserByNameAndFirstName(String name, String firstName);
}
