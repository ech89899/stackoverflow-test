package com.exmaple.stackoverflowtest.data.network

class ApiResponse<T> (data: T, successful: Boolean, error: String){
    var data: T? =  null
    var successful: Boolean = false
    var error: String = ""
}