package com.beachpartnerllc.beachpartner.utils;

/**
 * Created by seq-kala on 20/4/18.
 */

public interface SaveNoteInterface {
    void save(String text);

    void removeNote(String note_id);

    void update(String text,String note_id);
}
