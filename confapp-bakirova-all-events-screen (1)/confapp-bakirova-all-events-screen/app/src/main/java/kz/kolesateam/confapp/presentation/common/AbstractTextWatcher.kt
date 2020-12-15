package kz.kolesateam.confapp.presentation.common

import android.text.Editable
import android.text.TextWatcher

class AbstractTextWatcher(
        val onTextChanged: ((text: String) -> Unit)
) : TextWatcher{
    override fun afterTextChanged(s: Editable?){
        onTextChanged(s.toString()
        )
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
}