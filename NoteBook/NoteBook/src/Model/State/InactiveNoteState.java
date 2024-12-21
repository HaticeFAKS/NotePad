package Model.State;

import Model.ConcreteNote;

public class InactiveNoteState extends NoteState {
    public InactiveNoteState(ConcreteNote note) {
        super(note);
    }

    @Override
    public void notifyState() {
        notifyUser("Not arşivlendi.");  // Bildirim tetiklenir
    }
}

