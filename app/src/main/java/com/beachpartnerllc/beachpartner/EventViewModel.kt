package com.beachpartnerllc.beachpartner

import android.arch.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event
import com.beachpartnerllc.beachpartner.utils.SingleLiveEvent

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 06 Jul 2018 at 10:06 AM
 */
class EventViewModel : ViewModel() {
    val event = SingleLiveEvent<Pair<Int, Event>>()

    fun setChangedEvent(position: Int, event: Event) {
        this.event.value = Pair(position, event)
    }
}