package com.beachpartnerllc.beachpartner.utils;

import com.beachpartnerllc.beachpartner.models.ConnectionModel;

import java.util.ArrayList;

/**
 * Created by Owner on 5/11/2018.
 */

public interface TeamMakerInterface {

    void onAddListener( ConnectionModel myTeamMember,int position);

    void onRemoveListener(ConnectionModel removedMember,int position);
}
