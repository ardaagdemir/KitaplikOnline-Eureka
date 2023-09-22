package com.kitaplik.bookservice.dto

import com.kitaplik.bookservice.model.Book

/*
@JvmOverloads --> Bir sınıfın çeşitli constructorlarını oluşturmayı sağlar.
                  Örneğin buradaki BookDto sınıfı için BookIdDto' su null olan ve BookIdDto' su olmayan birer constructor üretir.
 */

data class BookDto @JvmOverloads constructor(
    val id: BookIdDto? = null,
    val title: String,
    val bookYear: Int,
    val author: String,
    val pressName: String
) {
    companion object { //static bir metot oluşturmak
        @JvmStatic
        fun convert(from: Book): BookDto {
            return BookDto(
                from.id?.let { BookIdDto.convert(it, from.isbn) },
                from.title,
                from.bookYear,
                from.author,
                from.pressName
            )
        }
    }
}