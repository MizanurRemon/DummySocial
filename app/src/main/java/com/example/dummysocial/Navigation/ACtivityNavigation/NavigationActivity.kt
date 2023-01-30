package com.example.dummysocial.Navigation.ACtivityNavigation

import android.content.Context
import android.content.Intent
import com.example.dummysocial.View.UserDetailsActivity

fun navigateToUserDetailsActivity(id: String, context: Context) {
    val intent = Intent(context, UserDetailsActivity::class.java)
    intent.putExtra("userid", id)
    context.startActivity(intent)
}


