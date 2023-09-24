# Kitaplik-Online Microservice Projesi
![Proje Diagram](C:\Users\Arda\Desktop\kitaplik-online\microservice-eureka.PNG)


### Fault Tolerance
- Olası hata durumlarından sonra farklı bir davranış yaratma işlemine verilen isimdir. 
- Spring dünyasında Hystrix kütüphanesi ile yapılabilen bir işlemdir. Ancak Spring CLoud 2021.0.4 versiyonu ile bu kütüphane deprecate edilmiştir.

**Yukarıdaki kütüphane yerine Spring Cloud CircuitBreaker Resilience4j getirilmiştir.**<br>
[Resilience Docs](https://resilience4j.readme.io/docs)

