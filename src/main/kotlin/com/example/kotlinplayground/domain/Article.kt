package com.example.kotlinplayground.domain

import jakarta.persistence.*

@Entity
class Article(
    var name: String
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @OneToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    private var _people: MutableList<Person> = mutableListOf()
    val people: List<Person>
        get() = _people.toList()

    fun joinPerson(person: Person) {
        _people += person
    }
}

