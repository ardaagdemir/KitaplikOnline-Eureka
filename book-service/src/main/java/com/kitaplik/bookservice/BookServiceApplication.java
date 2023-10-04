package com.kitaplik.bookservice;

import com.kitaplik.bookservice.model.Book;
import com.kitaplik.bookservice.repository.BookRepository;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
CommandLineRunner, uygulama ayağa kalkarken veri ekleyebilmek için kullanılan bir implementasyondur.
 */

@SpringBootApplication
@EnableEurekaClient
public class BookServiceApplication implements CommandLineRunner {
    private final BookRepository bookRepository;

    public BookServiceApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("Tüfek, Miktop ve Çelik", 2017, "Jared Diamond", "Pegasus Yayınevi", "1");
        Book book2 = new Book("Ortadoğu", 2016, "Bernard Lewis", "Arkadaş Kitabevi", "2");
        Book book3 = new Book("Modern Türkiyenin Doğuşu", 2015, "Bernard Lewis", "Arkadaş Kitabevi", "3");

        List<Book> books = bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        System.out.println(books);
    }

    @Bean
    public GrpcServerConfigurer keepAliveServerConfigurer(){
        return serverBuilder -> {
            if (serverBuilder instanceof NettyServerBuilder) {
                ((NettyServerBuilder) serverBuilder).keepAliveTime(30, TimeUnit.SECONDS)
                        .keepAliveTimeout(5, TimeUnit.SECONDS).permitKeepAliveWithoutCalls(true);
            }
        };
    }
}
