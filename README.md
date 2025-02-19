# 📝 NotePad 

Bu proje, JavaFX kullanarak geliştirilmiş bir **Not Defteri Yönetim Sistemi**dir. Kullanıcılar, notlarını kolayca oluşturabilir, düzenleyebilir ve yönetebilir. Notlar, farklı durumlarda (aktif veya arşivlenmiş) saklanabilir ve kullanıcının tercihine göre renklendirilebilir. Kullanıcılarrın rollerine göre belirli yetkileri bulunmaktadır.



## 🚀 Özellikler

- **Not Yönetimi**
  - 📌 Not ekleme, güncelleme ve silme
  - 📂 Notları aktif veya arşivlenmiş olarak saklama
  - 🎨 Notlara renk atama

- **Kullanıcı Girişi ve Yetkilendirme**
  - 🔑 Kullanıcılar, e-posta ve şifre ile sisteme giriş yapabilir.
  - 👤 Kullanıcı rollerine göre belirli yetkilere sahiptir (Admin, User, Employee).

- **Modern Yazılım Mimarisi**
  - 🏗️ CMV (Controller-Model-View) mimarisi
  - 🛠️ Singleton, Factory, Strategy, State tasarım desenleri



## 🏛️ Mimari ve Teknolojiler

- **JavaFX** – Kullanıcı arayüzü geliştirme
- **CMV Mimarisi** – Controller-Model-View yapısı
- **Singleton** – Veritabanı bağlantısını yönetmek için
- **Factory** – Notların oluşturulmasını soyutlamak için
- **Strategy** – Kullanıcı rollerine göre farklı not silme stratejileri
- **State** – Notların durum yönetimi



## 📌 Not Yönetimi

**Notlar iki farklı durumda olabilir:**
1. **Aktif Notlar** 🟢 – Kullanıcı tarafından aktif olarak kullanılan notlar
2. **Arşivlenmiş Notlar** 🔵 – Kullanıcının erişebileceği ancak pasif durumdaki notlar

**İşlevler:**
- 📝 **Not Ekleme** – Başlık, içerik, renk ve durum belirterek not oluşturma
- ✏️ **Not Güncelleme** – Mevcut notları düzenleme
- 🗑️ **Not Silme** – Kullanıcı rolüne bağlı olarak silme işlemi



## 🎨 Kullanıcı Arayüzü

- **📜 Not Defteri Ekranı:** Kullanıcıların notlarını listeleyebileceği, düzenleyebileceği ve yönetebileceği arayüz.
- **🔑 Giriş Ekranı:** Kullanıcıların sisteme giriş yapmasını sağlayan ekran.
- **🛠️ Admin Paneli:** Kullanıcı yönetimi ve rollerin atanabileceği alan.



## 📂 Proje Yapısı

```
📁 src/
 ┣ 📂 controller/  # İş mantığını içeren sınıflar
 ┣ 📂 model/       # Veritabanı ve not yönetim sınıfları
 ┣ 📂 view/        # JavaFX kullanıcı arayüzü dosyaları
 ┗ 📄 main.java    # Uygulamanın giriş noktası
```
## 📥 Kurulum ve Çalıştırma

### 1️⃣ Projeyi İndirme
Projeyi GitHub üzerinden aşağıdaki komut ile klonlayabilirsiniz:
```bash
 git clone https://github.com/HaticeFAKS/NotePad.git
```

### 2️⃣ Bağımlılıkları Kurma
JavaFX bağımlılıklarını eklediğinizden emin olun. Eğer eksikse, aşağıdaki adımları takip edebilirsiniz:
- JavaFX SDK'yı indirin ve projenize ekleyin.
- IDE'nizin (IntelliJ veya Eclipse) JavaFX kütüphanelerini tanıdığından emin olun.

### 3️⃣ Uygulamayı Çalıştırma
Projeyi çalıştırmak için aşağıdaki adımları uygulayın:
- `Main.java` dosyasını bulun.
- IDE üzerinden **Run** tuşuna basarak çalıştırın.
- Alternatif olarak terminalde şu komutu kullanabilirsiniz:
```bash
java --module-path "path_to_javafx" --add-modules javafx.controls,javafx.fxml -jar NotePad.jar
```
Burada `path_to_javafx` kısmını kendi sisteminize göre değiştirin.


## 🔗 Bağlantılar

👨‍💻 **Takım Arkadaşı:** Hamza Can Altıntop  
📌 **GitHub:** [GitHub Projesi](https://github.com/1220505072)











