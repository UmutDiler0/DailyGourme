package com.umutdiler.dailygourme2.classes

data class Person(
    override var email : String,
    var password : String,
    var name : String,
    var lastName : String,
    var age : String,
    var weight : String,
    var height : String
) : Partner(email)
    




