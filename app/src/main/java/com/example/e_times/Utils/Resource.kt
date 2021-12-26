package com.example.e_times.Utils


sealed class Resource<T>(
    var data:T? = null,
    var message:String? = null,
) {
    class Success<T>(data:T):Resource<T>(data)  //data is not nullable as we will be sure that data aint empty in success class
    class Failure<T>(message:String, data: T?=null):Resource<T>(data, message)
    class Loading<T>: Resource<T>()
}


/*
This class is used as a wrapper class for our network requests. It is a generic class.
It helps in knowing the state of the response as success,failure or loading and we can
choose to implement what to do in these states.
This is a recommended practice by Google.
 */