# NotePad

Bu proje, JavaFX kullanarak geliştirilmiş bir Kullanıcı Yönetim Sistemi'dir. Sistemde, adminler kullanıcı ekleyebilir. Kullanıcılar, sisteme giriş yapabilir ve notlarını yönetebilir. Her kullanıcı, belirli bir rol (admin, user, employee) ile sisteme erişim sağlar.

Proje Hakkında
Bu sistem, kullanıcıların notlarını oluşturup düzenleyebileceği bir platform sunmaktadır. Ayrıca, adminler kullanıcı yönetimi işlemleri yapabilir. Sistemdeki önemli bileşenler şunlardır:
• Admin Paneli: Adminler kullanıcı ekleyebilir ve rolleri belirleyebilir.
• Kullanıcı Girişi: Kullanıcılar, e-posta ve şifre ile sisteme giriş yapar.
• Not Yönetimi: Kullanıcılar notlarını yönetebilir.
• Kayıt Olma: Yeni kullanıcılar kayıt olabilir.

Kullanım
Projede aşağıdaki ana işlevler bulunmaktadır:

1. Admin Paneli
Admin paneli, kullanıcı eklemeyi (admin, user ve employee roller seçebilmeyi) sağlar.
• Kullanıcı Ekle: Admin, yeni kullanıcılar ekleyebilir. Kullanıcı bilgileri (ad, e-posta, şifre, rol) belirtilerek kullanıcı eklenebilir.
• Kullanıcı Listeleme: Tüm kullanıcılar listelenebilir.

2. Kullanıcı Girişi
Kullanıcılar, e-posta ve şifre ile sisteme giriş yapabilir. Giriş başarılı olursa, kullanıcı rolüne bağlı olarak yetkilendirilir.
• Admin: Admin, herkesi yönetebilir.
• Kullanıcı: Kullanıcı, kendi notlarını yönetebilir.
• Employee: Employee, kullanıcı gibi işlem yapabilir, ancak admin yetkileri yoktur.

3. Not Yönetimi
Kullanıcılar, notlarını oluşturup düzenleyebilir. Notlar, iki durumda olabilir: aktif veya arşivlenmiş.
• Not Ekle: Kullanıcı, başlık, içerik, renk ve durum (aktif veya arşivlenmiş) ile not ekleyebilir.
• Not Güncelleme: Kullanıcılar mevcut notlarını güncelleyebilir.
• Not Silme: Notlar, kullanıcı rolüne bağlı olarak silinebilir.

4. Kayıt Olma
Yeni kullanıcılar, sisteme kayıt olabilirler. Kayıt olurken, e-posta ve şifre belirlerler.

Teknolojiler
Bu projede aşağıdaki teknolojiler ve araçlar kullanılmıştır:
• JavaFX: Kullanıcı arayüzü (UI) için.
• CMV Tasarımı: Model-View-Controller (CMV) mimari deseni kullanılmıştır.
• Strategy Pattern: Not silme işlemi için kullanıcı rollerine göre farklı stratejiler uygulanmıştır.

Bu proje, kullanıcı yönetimi ve not ekleme işlemleri için geliştirilmiş bir masaüstü Java uygulamasıdır. Uygulama, JavaFX kullanılarak GUI (Graphical User Interface) oluşturulmuş ve birkaç farklı tasarım deseni kullanılmıştır. Proje, Singleton, Factory, Strategy ,State tasarım desenlerini içerir ve Abstarct sınıfı ve CMV mimarisi kullanılmıştır.

İçindekiler

1. Proje Özeti
2. Tasarım Desenleri
o Singleton
o Factory
o Strategy
o Abstract
o State
CMV Mimarisi ve abstract sınıflarda kullanılmıştır(NoteState ve AbstractNote). 3. Veritabanı Bağlantısı
4. Uygulama Yapısı
5. Kullanıcı Arayüzü
6. Kullanım
7. Notlar

Proje Özeti
Bu uygulama, kullanıcıların giriş yapabileceği, şifreleri ile doğrulama yapabileceği, yeni kullanıcılar ekleyebileceği, mevcut kullanıcıları listeleyebileceği ve notlar oluşturup yönetebileceği bir sistem sunar. Her kullanıcıya belirli bir rol atanır (admin, user, employee) ve her rolün farklı yetkileri vardır. Admin, kullanıcı ekleyebilir ve yönetebilir, user kendi notlarını ekleyip düzenleyebilir, employee ise yalnızca belirli işlemleri yapabilir.

Tasarım Desenleri
Bu projede kullanılan başlıca tasarım desenleri aşağıda açıklanmıştır:
Singleton Tasarım Deseni
• DatabaseConnection sınıfı, veritabanı bağlantısını yönetir ve tek bir bağlantı örneği oluşturulmasını sağlar. Bu sayede her veritabanı işlemi için aynı bağlantı kullanılır, bu da kaynakların daha verimli yönetilmesine olanak tanır ve diğer yerlerden yapıcı metotla ulaşılır sadece.
Factory Tasarım Deseni
• AbstractNoteFactory deseni, notların oluşturulmasını soyutlar ve farklı not türlerinin eklenmesini kolaylaştırır.
• ConcreteNoteFactory, belirli bir not türünü oluştururken, AbstractNoteFactory ise genel bir şablon sunar.
Strategy Tasarım Deseni
• DeleteStrategy sınıfı, kullanıcının notları silme yetkisini belirler. Kullanıcı rolüne bağlı olarak farklı silme stratejileri uygulanır. Örneğin, user rolündeki bir kullanıcı tüm notları silebilirken, employee rolündeki kullanıcı silme yetkisine sahip değildir.
• AdminDeleteStrategy, UserDeleteStrategy ve EmployeeDeleteStrategy sınıfları, her bir kullanıcının silme işlemi üzerinde farklı haklara sahip olmasını sağlar.

State Tasarım Deseni
• NoteState sınıfı, durum için temel bir yapı oluşturur, notifyState() fonksiyonunun abstract halini içerir.
• ActiveNoteState sınıfı, notifyState() fonksiyonuyla durum için temel bir yapı oluşturur.(aktif not)
• InActiveNoteState sınıfı, notifyState() fonksiyonuyla durum için temel bir yapı oluşturur.(arşivlenmiş not)

Veritabanı Bağlantısı
Projede Singleton tasarım deseni kullanılarak veritabanı bağlantısı yönetilmektedir. DatabaseConnection sınıfı, sadece tek bir bağlantı örneği oluşturulmasını sağlar ve bu örnek tüm uygulama boyunca kullanılır.

Uygulama Yapısı
Uygulama üç ana paketten (CMV) oluşmaktadır:
1. Controller: İş mantığı ve veritabanı işlemleri burada yönetilir.
2. Model: Notlar ve kullanıcıları temsil eden sınıflar burada yer alır.
3. View: Uygulamanın GUI kısmını oluşturan sınıflar burada bulunur.

Controller
• UserController sınıfı, kullanıcı ekleme, listeleme, doğrulama ve kayıt işlemlerini yönetir.
• NoteController sınıfı ise notları ekler, günceller, listeler ve siler.

Model
• User sınıfı, kullanıcıya ait bilgileri saklar.
• ConcreteNote sınıfı, bir notun özelliklerini temsil eder.
• Note sınıfı, soyut bir not şablonu sunar.

View
• LoginFrame: Kullanıcıların sisteme giriş yapabilmesini sağlar.
• RegisterFrame: Yeni kullanıcı kaydı oluşturulabilir.
• AdminView: Admin paneli üzerinden kullanıcı ekleme yapılabilir.
• NoteView: Kullanıcıların notlarını görüntüleyebileceği ve yönetebileceği ekrandır.

Kullanıcı Arayüzü
Uygulamanın kullanıcı arayüzü JavaFX kullanılarak oluşturulmuştur. Temel ekranlar ve işlevler şunlardır:
1. LoginFrame: Kullanıcıların sisteme giriş yapmasını sağlar. Admin kullanıcıları ekleyebilir.
2. RegisterFrame: Yeni kullanıcıların sisteme kayıt olmasını sağlar.
3. AdminView: Admin paneli üzerinden kullanıcı yönetimi yapılabilir.
4. NoteView: Kullanıcıların notlarını ekleyebileceği, düzenleyebileceği ve silebileceği ekran.

Kullanım
1. Giriş Yap: Sisteme kullanıcı adı ve şifre ile giriş yapabilirsiniz.
2. Admin Ekle: Giriş yaptıktan sonra admin olarak sisteme yeni admin kullanıcıları ekleyebilirsiniz.
3. Not Ekle: Sistemdeki kullanıcılar kendi notlarını ekleyebilir ve yönetebilir.
4. Kullanıcı Yönetimi: Admin, sisteme yeni kullanıcılar ekleyebilir ve mevcut kullanıcıları listeleyebilir.

Notlar
• Veritabanı bağlantı bilgileri ve kullanıcı yönetim bilgileri, UserController ve DatabaseConnection sınıflarında saklanmaktadır.
• Proje, Strategy, Factory, Singleton ve State tasarım desenlerini kullanarak esnek ve sürdürülebilir bir yapıya sahiptir.

Takım Arkadaşım: Hamza Can Altıntop
Github Linki: [(https://github.com/1220505072)]

