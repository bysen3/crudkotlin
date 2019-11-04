package com.example.crudkotlin

import java.io.Serializable

/**
 * Created by Parsania Hardik on 26-Apr-17.
 */
class UserModel : Serializable {

    var name: String? = null
    var hobby: String? = null
    var city: String? = null
    var id: Int = 0

    fun getCitys(): String {
        return city.toString()
    }

    fun setCitys(city: String) {
        this.city = city
    }

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getHobbys(): String {
        return hobby.toString()
    }

    fun setHobbys(hobby: String) {
        this.hobby = hobby
    }

}