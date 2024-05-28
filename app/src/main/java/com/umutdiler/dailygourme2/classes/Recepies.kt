package com.umutdiler.dailygourme2.classes

data class Recepies(
    var foodName : String,
    var ingredients : String,
    var description : String,
    override var email : String
) : Partner(email)

