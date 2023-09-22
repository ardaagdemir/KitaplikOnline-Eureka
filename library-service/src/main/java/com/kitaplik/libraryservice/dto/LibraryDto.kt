package com.kitaplik.libraryservice.dto

/*
@JvmOverloads --> Bir sınıfın çeşitli constructorlarını oluşturmayı sağlar.
                  Örneğin buradaki BookDto sınıfı için BookIdDto' su null olan ve BookIdDto' su olmayan birer constructor üretir.
 */

data class LibraryDto @JvmOverloads constructor(
    val id: String,
    val userBookList: List<BookDto>? = ArrayList() //? --> null olabilir, eğer olursa default olarak ArrayList yap
)
