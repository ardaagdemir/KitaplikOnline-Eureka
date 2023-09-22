package com.kitaplik.bookservice.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

/*
Lombok gibi 3th party kütüphaneler kullanmak yerine pojolar için Kotlin sınıfları kullanmak çoğu zaman daha doğrudur.
Bunun sebebi ise bu kütüphanelerin her zaman desteklenemeyecek olması veya versiyon olarak uygulamanızda eski sürümün kalıyor olmasıdır.
Lombok gibi annotation proccessor mantığıyla çalışan yapılar, compile time sırasında arka planda bir source code oluşturarak bunun üzerinden işlemlerini gerçekleştirir.
Bu doğrudan bu anotasyona bağımlı olduğumuz ve bunun üzerinden sınıfların çağrıldığını gösterir.
Genelde EE uygulamalarında kullanımı çok tercih edilmez. Bunun yerine Kotlin sınıfları compile time da constructor ile init olarak daha güvenli bir yapı sağlar.
Ayrıca Kotlin sınıfları Lombok olmadan kullanılan Java sınıflarına göre daha okunabilirdir.

Lombok kütüphanesi ile kullanılan @Data ve @NoArgsConstructor gibi anotasyonlara burada ihtiyaç yoktur.
Bu anotasyonlar compile time da javanın çalışmasını geciktirirler. Bunun sebebi ise program öncelikle Data için çalışır ve sonlanır.
Daha sonra gelip tekrar NoArgsConstructor için bir kez daha sınfı çağırır çalışır ve sonlanır. Bu da büyük ölçekli uygulamalarda ciddi zaman kayıpları yaratmaktadır.
Kotlin data sınıfı @Data anotasyonu içerisinde bulunana @EqualsAndHashcode ve @ToString gibi yapıları kendi bünyesinde bulundurur.
 */

@Entity
@Table(name = "books")
data class Book @JvmOverloads constructor(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val title: String,
    val bookYear: Int,
    val author: String,
    val pressName: String,
    val isbn: String
)
