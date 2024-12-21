package Model.State;

import Model.ConcreteNote;

public class ActiveNoteState extends NoteState {
    public ActiveNoteState(ConcreteNote note) {
        super(note);
    }

    @Override
    public void notifyState() {

        notifyUser("Not aktif.");  // Bildirim tetiklenir
    }
}

