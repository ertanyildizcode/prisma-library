package com.prisma.library.library.control.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import com.prisma.library.library.control.repository.UserRepository;
import com.prisma.library.library.entity.constants.Constants;
import com.prisma.library.library.entity.model.User;
import com.prisma.library.library.helper.CSVHelper;
import com.prisma.library.library.helper.CSVReaderHelper;
import com.prisma.library.library.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CSVHelper csvHelper;
    private final CSVReaderHelper csvReaderHelper;

    @PostConstruct
    public void initializeCsvFile() throws IOException {
        log.info("Adding user list process to db is started.");
        List<User> userList = executeReading();
        saveImportedUserList(userList);
    }

    @Override
    public List<User> executeReading() throws IOException {
        InputStream inputStream = new FileInputStream(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("user.csv")).getFile());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        List<CSVRecord> csvRecordList = csvHelper.findCsvRecords(inputStreamReader, Constants.userCsvHeaders);
        return csvReaderHelper.readUserCsv(csvRecordList);
    }

    public void saveImportedUserList(List<User> userList) {
        List<User> addUserList = new ArrayList<>();
        List<User> updateUserList = new ArrayList<>();

        Map<ImmutablePair<String, String>, User> userMap = new HashMap<>();
        userRepository.findAll()
            .forEach(user -> userMap.put(ImmutablePair.of(user.getName(), user.getFirstName()), user));

        for (User user : userList) {
            if (!userMap.containsKey(ImmutablePair.of(user.getName(), user.getFirstName()))) {
                addUserList.add(user);
            } else {
                User updatedUser = userMap.get(ImmutablePair.of(user.getName(), user.getFirstName()));
                updatedUser.setMemberSince(user.getMemberSince());
                updatedUser.setMemberTill(user.getMemberTill());
                updatedUser.setGender(user.getGender());
                updateUserList.add(updatedUser);
            }
        }
        userRepository.saveAll(addUserList);
        userRepository.saveAll(updateUserList);
    }

    public User findUserByNameAndFirstName(String name, String firstName) {
        return userRepository.findByNameAndFirstName(name, firstName).get();
    }
}
