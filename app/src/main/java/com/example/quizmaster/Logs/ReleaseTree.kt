package com.example.quizmaster.Logs

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import org.jetbrains.annotations.NotNull
import timber.log.Timber


class ReleaseTree : @NotNull Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
// send the error reports to crashlytics
        Firebase.crashlytics.also {
                it.setCustomKey("Priority", priority)
                tag?.let { _ -> it.setCustomKey("Tag",tag) }
                it.log(message)
                t?.let { e-> it.recordException(e) }
            }.sendUnsentReports()

        }

   }
}

