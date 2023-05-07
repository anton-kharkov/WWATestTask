package ua.anton.wwatesttask.site

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor() : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val databaseRef = database.getReference("pinterest_link")

    private var siteUrl = ""

    fun connectToFirebaseDatabase() {
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                siteUrl = value!!
                Log.d("SiteViewModel_23", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("SiteViewModel_27", "Failed to read value.", error.toException())
            }

        })
    }

    fun getSiteUrl(): String {
        return siteUrl
    }
}