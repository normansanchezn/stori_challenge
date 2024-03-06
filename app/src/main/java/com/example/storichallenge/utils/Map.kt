package com.example.storichallenge.utils

interface Map<I, out O> {
    fun map(data: I): O
}