<center>

# Kitaplik-Online Microservice REST Mimari
</center>

![Proje Diyagram](D:\Software\Java\Folksdev\Kitaplık_Online-EurekaServer\readme-images\microservice-eureka.PNG)

<center> 

## Microservice Terminolojisi 
</center>

### Fault Tolerance
  - Olası hata durumlarından sonra farklı bir davranış yaratma işlemine verilen isimdir.
  - Spring dünyasında Hystrix kütüphanesi ile yapılabilen bir işlemdir. Ancak Spring Cloud 2021.0.4 versiyonu ile bu kütüphane deprecate edilmiştir.

_**Yukarıdaki kütüphane yerine Spring Cloud CircuitBreaker Resilience4j kullanılmalıdır.**<br>_
[Resilience Docs](https://resilience4j.readme.io/docs/examples) <br>
[Spring Cloud Circuit Breaker Docs](https://spring.io/projects/spring-cloud-circuitbreaker/)
---
### SOA (Service-Oriented Architecture)
- Uygulama componentlerinin, çeşitli protokoller ile ağ üzerinden birbirleriyle iletişime geçtiği bir mimarı yaklaşımdır.
- Örneğin bir servisin HTTP Protokolünü kullanarak başka bir servisten veri transferi yapması işlemine SOA denebilir.

### WOA (Web-Oriented Architecture)
- .
---
### CAP Theorem (Consistency, Availability, Partition Tolerance)
- #### Consistency
  -  Microservice gibi dağıtık sistemlerde bir sunucuya istek gönderildiğinde her bir servisin isteğine aynı cevap dönebilmelidir. <br>Örneğin A ve B servisleri olsun. A servisi güncellerken B servisi de güncelledi. <br>Daha sonra A servisine bir istekte bulunulduğunda B servisinin de en güncel hali yanıt olarak dönmelidir.
  -  Bu olaya **Data Sync** denir. Bunu sağlamak için çeşitli toollar kullanılabilir. **Distributed Lock (Redis), Kafka, RabbitMQ, Event Sourcing vb.**
      -  **Event Sourcing** --> Sistemdeki tüm durum değişikliklerini (olayları) kaydetmeyi ve bu olaylardan sistemin mevcut durumunu oluşturmayı amaçlayan bir tasarım desenidir. <br>Bu sayede, sistemin geçmiş durumu tamamen yeniden oluşturulabilir ve her bir durum değişikliği geri alınabilir, sorgulanabilir veya analiz edilebilir.
      -  **Distributed Lock** --> Distributed Lock birden fazla bileşen veya uygulamanın aynı kaynağa eş zamanlı erişimini sınırlamak için kullanılan bir koordinasyon mekanizmasıdır.
- #### Availability
    - Dağıtık olan sunuculara bağlı iş akışına sorgu atıldığında bir cevap alınabilmelidir. <br>Örneğin 5 tane bu iş akışına bağlı sorgu varsa ve bir tanesi cevap vermiyorsa geriye kalan 4 sorgudan yine de cevap dönmelidir.
- #### Partition Tolerance
  -  Dağıtık sistemler arasında iletişim koparsa kalan sunucular çalışmaya devam etmelidir. <br>Örneğin A, B, C servisleri arasından A ve B arasındaki iletişim koptuysa C yine de çalışmaya devam etmelidir.
---
### ACID (Atomicity, Consistency, Isolation, Durability)
- #### Atomicity 
    -  Örneğin bir güncelleme işlemi başlatıldığında bu güncelleme işlemine ait 5 adet daha güncelleme var olsun. <br>2 Güncelleme işlemi ardından 3. güncelleme işleminde bir sorun çıktığında güncelleme işlemi orada kalmamalıdır. <br> Böyle bir durumda ya bütün işlem geriye alınmalıdır ya da bütün işlem başarılı sonuçlanmalıdır.
- #### Bkz. [Consistency](#consistency)
- #### Isolation
  -  Aynı veri üzerinde işlem gerçekleştiren transaction birbirini etkilememelidir. Bunun için gerekirse veri setleri kilitlenmelidir. <br>Bu transaction'lar senkron bir şekilde çalışmalıdır.
- #### Durability
  -  Bir hata olması durumunda sistemin önceki durumuna geri dönebilmesi demektir.

### BASE (Basically Avaliable, Soft-State, Eventual Consistent)

---

<center>

## Microservice Teknolojileri
</center> 

- ### İletişim
  - RestAPI --> Servisler arası JSON alışverişi
  - gRPC(Http6) veya Apache Thrift --> Aynı yaklaşıma sahiptirler.
  - Async Communication (Event Driven veya Event Source) --> Servisler birbirlerini direkt çağırmazlar
  - Message Broker (RabbitMQ, Kafka, Redis Pub/Sub)
---
- ### Yük Dağıtma - Load Balancing
  1. Server Side Load Balancing
       - Kubernetes LB
       - Traefik
       - HAProxy
       - Cloud Native LB : AWS Elastic LB
       - Custom LB
  2. Client Side Load Balancing
     - Spring Cloud LB
     - gRPC Client-Side LB

### !! Genel olarak Client-Side Load Balancing öğretilir. Bunu sebebi kodlamasının kolay olmasıdır.

---
- ### Loglama
  - Zipkin Client - Spring Fw
  - Jeager - K8s
  - AWS Cloudwatch
  - Logstash - ElasticSearch
---
- ### Orchestration - Auto Scaling
  - K8s --> Kubernetes
  - Azure K8s Service
  - Mesos
  - Aws Elastic Container Service
---
- ### Service Discovery
  #### A ve B servislerinin birbirini bulabilmesini sağlayan ara yönlendirici katman görevi gören yapılardır.
  - Netflix Eureka - Spring Fw
  - Consul - HashiCorp
  - K8s - Etcd Service
  - Zookeeper - Apache
---
- ### Async Communication
  - Apache Kafka
  - RabbitMQ
  - AWS Simple Queue Service (SQS)
  - Google Cloud Pub/Sub
---
- ### Hata Ayıklama
  - Hystrix
  - Polly
  - Retry and Timeout Design Pattern (custom)
---


<center>

## Mikroservisler Arası İletişim
</center>

### Rest Template 
- Rest Template bir dış API' den veri transferi yapmak için ilk kullanılan yöntemlerden biridir. 
<br>Örneğin A ve B mikroservisleri arasındaki bir veri transferi yapılacağı zaman Rest Template kullanılabilir. Ancak bu bazı bağımlılıklara yol açmaktadır. 
<br>Rest template de bir servise istek atılacağı zaman o servisin url' i tam olarak bilinmelidir. 
<br>Url' de bir değişiklik yapıldığında; örneğin `localhost:8080` yerine `8081` veya `localhost:8080/v1/user` yerine `/v2/user` olduğunda Rest Template için de bu değişiklikleri yapmak gerekmektedir.
<br>Bu durum da bir bağımlılığa yol açmaktadır. Bunu gidermek için **Eureka** adında bir teknoloji kullanılmaktadır.
### Eureka ve FeignClient 
  - Eureka servislerin API'larını tanımladığı ve dolayısıyla url' lerinin tutulduğu bir teknolojidir. Rest Template' de yaşanılan bağımlılık burada giderilmeye çalışılır.
<br>Öncelikle hem A hem de B servisi kendisini Eureka' ya bir **Eureka Client** aracılığıyla tanıtır.
<br>Bu durumda A servisi için Eureka, `localhost:8080 `gibi bir base url mantığıyla bir url tutar. Bu durum portta yapılacak bir değişiklikte servislerin etkilenmemesini sağlar.
<br>Yani A servisinin portu `8081` olduğunda B servisinden A servisine atılacak herhangi bir istek durumunda A servisinde port değişikliği yapmaya gerek kalmaz.
<br>Tabii ki bu durumda da bağımlılık Eureka' ya olmaktadır. Çünkü Eureka bulutta çalışmadığında bütün iletişim kopacak ve uygulama çalışmayacaktır.
<br>Bu durumun önüne geçmek için sunucularda her zaman Eureka' ya yüksek öncelik verilir. Eureka' nın bulunduğu pod' a gerçek zamanlı olarak dinleme yapabilmek adına sinyaller gönderilir. 
<br>Olası bir sorun oluştuğunda bu sorun henüz daha uygulamanın diğer alanlarına ulaşmadan, bu pod yerine yeni bir pod ayağa kaldırılır ve bu sayede Eureka' da herhangi bir hata olması durumu minimize edilebilir.
<br> .
  - _**Eureka kullanırken genellikle onunla beraber kullanılan HttpClient yapısı FeignClient'dır.**_
---

### Creating of Book, Library Microservices - Eureka Server Configuration - FeignClient Configuration

- Öncelikle **book-service** geliştirmesi yapılmalı ve daha sonra Eureka Server' a tanımlanmalıdır.
- Bunun için bir adet **Eureka-Server** microservice' i oluşturarak **application.properties** dosyasında gerekli ayarların yapılması gerekmektedir.
- Daha sonra `book-service/application.properties` dosyasında Eureka için bir url tanımlaması yapılmalıdır.
- Son olarak `book-service/BookServiceApplication` sınıfında @EnableEurekaClient anotasyonunu kullnaılmalıdır.
- Bu işlemler sonucunda artık **book-service, Eureka Server' a** kayıt olmuş olacaktır.
- Kontrol etmek için `localhost:8761 (Eureka application.properties deki port)` adresine gidilebilir.
<br> .
- `library-service` microservice geliştirmesi yapılmalı ve Eureka Server' a tanımlanmalıdır.
- Daha sonra `libarary-service/application.properties` dosyasında Eureka için bir url tanımlaması yapılmalıdır.
- `library-service`' in, `book-service`' e erişebilmesi için önce Eureka Server' a gitmesi gerekir. Bir servisten başka bir servise istek atmak için FeignClient kullanılabilir.
<br>.

### FeignClient 404-Error Decoder and Fault Tolerance (Circuit Breaker)
- _**Feign Client**_ isteklerinde herhangi bir hata oluştuğunda 500, 400 gibi hataların dönmesini engellemek, 
<br>hatanın mesajını görebilmek ve bu hata mesajlarından Rest Response'  a dönüştürmek için bir **Feing Client Error Decoder** sınıfı yazılmalıdır.
- _**Fault Tolerance**_ bir serviste sorun olduğunda veya veritabanında ilgili veri olmadığında bir servisin nasıl davranması gerektiğini belirleyebilen bir yöntemdir.(Business case'dir)
<br>.
- **_Hata alındığında;_** 
  - **işlem durdurulabilir,**
  - **varsayılan bir response üretilebilir,**
  - **farklı bir servise yönlendirilebilir veya**
  - **hata fırlatılabilir.**
---

### API Gateway, Spring Actuator, Distributed Log Trace, Zipkin

- 
     