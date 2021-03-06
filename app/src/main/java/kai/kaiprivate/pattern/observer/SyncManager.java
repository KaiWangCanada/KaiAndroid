package kai.kaiprivate.pattern.observer;

import java.util.Observable;

public class SyncManager extends Observable {

    // use instance, so you don't need to create an instance yourself
    private static SyncManager instance = new SyncManager();

    public static SyncManager getInstance() {
        return instance;
    }
    private SyncManager() {
    }

    private boolean syncInProgress = false;

    public void sync(final String param1, final String param2, final int param3){

        if (syncInProgress)
            return;

        // example of a custom error message
        if (param2.equals("error")) {
            setChanged();
            notifyObservers(new SyncUpdateMessage(SyncUpdateMessage.SYNC_CUSTOM_ERROR,
                    null));
            return;
        }

        // set flag
        syncInProgress = true;
        notifyObservers(new SyncUpdateMessage(SyncUpdateMessage.SYNC_STARTED,
                null));

        // all good, begin process
        new Thread(new Runnable() {

            @Override
            public void run() {

//                Note note = databaseManager.getNote(String param1);
//                String result = NoteUploader.uploadNote(note);
                String result = param1;

                setChanged();
                notifyObservers(new SyncUpdateMessage(
                        SyncUpdateMessage.SYNC_SUCCESSFUL, result));

                // release flag
                syncInProgress = false;
            }
        }).start();
    }

}