package com.prisma.library.library.entity.constants;

import java.text.SimpleDateFormat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static final String TITLE = "Title";
    public static final String AUTHOR = "Author";
    public static final String GENRE = "Genre";
    public static final String PUBLISHER = "Publisher";
    public static final String USER_NAME = "Name";
    public static final String USER_FIRST_NAME = "First name";
    public static final String USER_MEMBER_SINCE = "Member since";
    public static final String USER_MEMBER_TILL = "Member till";
    public static final String USER_GENDER = "Gender";
    public static final String BORROWER = "Borrower";
    public static final String BORROW_BOOK = "Book";
    public static final String BORROW_FROM = "borrowed from";
    public static final String BORROW_TO = "borrowed to";

    public static final String[] userCsvHeaders = { Constants.USER_NAME,
                                                    Constants.USER_FIRST_NAME,
                                                    Constants.USER_MEMBER_SINCE,
                                                    Constants.USER_MEMBER_TILL,
                                                    Constants.USER_GENDER };
    public static final String[] booksCsvHeaders =
        { Constants.TITLE, Constants.AUTHOR, Constants.GENRE, Constants.PUBLISHER };

    public static final String[] borrowCsvHeaders =
        { Constants.BORROWER, Constants.BORROW_BOOK, Constants.BORROW_FROM, Constants.BORROW_TO };
}
