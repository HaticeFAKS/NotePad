package Model.Factory;

import Model.ConcreteNote;
import java.util.List;

public abstract class AbstractNoteFactory {

    // Yeni bir not eklemek için soyut bir metod.
    // Bu metod, ConcreteNote nesnesi alır ve notu ilgili kaynağa ekler.
    public abstract void addNote(ConcreteNote note);

    // Var olan bir notu, id'sine göre güncellemek için soyut bir metod.
    // ConcreteNote nesnesi alır ve mevcut notu günceller.
    public abstract void updateNote(ConcreteNote note);

    // Bir notu, id'sine göre silmek için soyut bir metod.
    // ConcreteNote nesnesi alır ve bu notu veritabanından veya kaynaktan siler.
    public abstract void deleteNote(ConcreteNote note);

}
