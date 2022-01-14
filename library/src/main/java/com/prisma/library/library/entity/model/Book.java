package com.prisma.library.library.entity.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = { "title", "author" }, callSuper = true)
@ToString(of = { "title", "author" }, callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book extends BaseObject {

    private String title;

    private String author;

    private String genre;

    private String publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Borrow> borrow = new HashSet<>();
}
