package kz.kolesateam.confapp.clicklisteners

import kz.kolesateam.confapp.clicklisteners.BranchClickListener
import kz.kolesateam.confapp.clicklisteners.EventsClickListener
import kz.kolesateam.confapp.clicklisteners.FavoritesClickListener

interface UpcomingItemsClickListener : BranchClickListener, EventsClickListener,
    FavoritesClickListener