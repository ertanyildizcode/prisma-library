package com.prisma.library.library.entity.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.prisma.library.library.entity.enums.GenderEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = { "name", "firstName" }, callSuper = true)
@ToString(of = { "name", "firstName" }, callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseObject {

    private String name;

    private String firstName;

    private Timestamp memberSince;

    private Timestamp memberTill;

    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Borrow> borrow;
}
