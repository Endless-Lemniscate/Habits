# Habits
Android application for tracking habits. 
This is a sample android Clean Architecture app written in Kotlin with MVVM pattern

## Offline-first sync strategy
Each habit has additional field **STATUS** which can take following values:

* OK
* NOT_SYNCED
* DELETED

As habit created it takes value **NOT_SYNCED** by default.

Also habit have two fields: one **DONE_DATES** for amount of times it was accomplished and another called **DONE_DATES_NS** for keeping same as previous, but not synced with remote repository.

As supposed by *offline-first approach* local db is the only source of truth. That's mean what local repository should not depend on remote one. Last one is used only to prevent data losses and improve stability.

So app has synchronisation module which implemented as *SyncHabitsWithRemote UseCase* in domain layer.

This module works only with habits which have **NOT_SYNCED** or **DELETED** value in field **STATUS**. 

* For those which have **NOT_SYNCED** module loads in remote repository and changes status to **OK**. Thereafter it loads every single value from field **DONE_DATES_NS** to remote and clear this field.
* For habits with status **DELETED** module deletes it from remote storage as well as local storage.