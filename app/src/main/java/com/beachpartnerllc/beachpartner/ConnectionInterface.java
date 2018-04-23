package com.beachpartnerllc.beachpartner;

import android.os.Bundle;

/**
 * Created by seq-kala on 15/4/18.
 */

public interface ConnectionInterface {

    void block(String personid,String person_name);

    void unblock(String personid,String person_name);
    void transition(Bundle bundle);
    void connectionToNote(Bundle bundle);
}
