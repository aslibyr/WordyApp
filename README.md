# WordyApp - Kelime Öğrenme Uygulaması

## Proje Açıklaması

**WordyApp**, kullanıcılara farklı dillerde kelimeler öğretmeyi amaçlayan bir mobil uygulamadır. Uygulama, kelimelerin anlamlarını, görsellerini ve "learned" (öğrenildi) statülerini içerir. Kullanıcı, kelimeleri öğrenmek için kelime kartlarına tıklayabilir ve öğrendiklerini işaretleyebilir. Ayrıca, öğrenilen kelimeler kaydedilir ve daha sonra görüntülenebilir.

<img src="https://github.com/aslibyr/WordyApp/blob/master/images/1.png" alt="Ekran Görüntüsü 1" width="400"/><img src="https://github.com/aslibyr/WordyApp/blob/master/images/2.png" alt="Ekran Görüntüsü 2" width="400"/><img src="https://github.com/aslibyr/WordyApp/blob/master/images/3.png" alt="Ekran Görüntüsü 3" width="400"/>
<img src="https://github.com/aslibyr/WordyApp/blob/master/images/4.png" alt="Ekran Görüntüsü 4" width="400"/>

## Özellikler

- Kelimeler liste halinde görüntülenir.
- Her bir kelime kartında kelimenin anlamı ve resmi bulunur.
- Kullanıcı, bir kelimeyi "learned" olarak işaretleyebilir ve bu bilgi cihazda saklanır.
- Öğrenilen kelimeler `SharedPreferences` kullanılarak kaydedilir.
- "Learned" kelimeler ayrı bir liste olarak görüntülenir.
- Kelime kartında uzun basıldığında, kelime "learned" statüsünden çıkarılabilir.
- - **Swipe to Refresh** özelliği kullanılarak kelimeler yeniden karıştırılabilir.

## Projede Kullanılan Teknolojiler

- **Kotlin**: Modern, statik tipli bir programlama dili.
- **Android Jetpack Components**: 
  - **ViewModel**: UI verilerini yönetir.
  - **StateFlow**: Akış tabanlı veri yönetimi sağlar.
  - **DataBinding**: XML ve Kotlin kodu arasında veri bağlamayı sağlar.
  - **SharedPreferences**: Basit veri saklama çözümü.
- **Dagger Hilt**: Dependency Injection (bağımlılık enjeksiyonu) için.
- **RecyclerView**: Liste elemanlarını görüntülemek için.
- **Material Design**: UI bileşenleri ve tasarım yönergeleri.
- **Kotlin Coroutines**: Zaman uyumsuz işlemler için.
- **Retrofit**: REST API çağrıları yapmak için.
- **Room**: Yerel veritabanı işlemleri için.
- **LiveData**: Veri değişikliklerini gözlemlemek için.
- **DataStore**: Modern veri saklama çözümü.
- **Navigation Component**: Uygulama içi gezinti yönetimi.
- **WorkManager**: Arka plan işlerini yönetmek için.

## Kurulum ve Çalıştırma

1. Bu projeyi yerel makinenize klonlayın:
    ```bash
    git clone https://github.com/aslibyr/WordyApp.git
    ```

2. Android Studio'yu açın ve bu projeyi yükleyin.

3. Gereken bağımlılıkları yüklemek için Gradle dosyalarını senkronize edin:
    - `Dagger Hilt`
    - `Kotlin Coroutines`

4. Uygulamayı cihazınızda veya emülatörünüzde çalıştırın.

## Testler

Bu projede **Unit Testler** ve **UI Testleri** için `JUnit` ve `Mockito` kullanılmaktadır. Aşağıdaki komutla testleri çalıştırabilirsiniz:

```bash
./gradlew test
