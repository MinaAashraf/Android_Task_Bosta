package com.bosta.androidtask_bosta.domain.model

data class UserAddress(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
){
    override fun toString(): String {
        return "$street, $suite, $city, $zipcode"
    }
}
