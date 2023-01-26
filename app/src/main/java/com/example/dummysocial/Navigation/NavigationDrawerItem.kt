package com.example.dummysocial.Navigation

import android.graphics.Color
import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dummysocial.R

@Composable
fun NavigationDrawerItem(
    item: NavigationItem,
    selected: Boolean,
    onItemClick: (NavigationItem) -> Unit
) {
    val background = if (selected) R.color.LightGrey else android.R.color.transparent
    val textColor = if (selected) R.color.purple_200 else R.color.black
    val iconColor = if (selected) R.color.purple_200 else R.color.black
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {

        Icon(
            painter = painterResource(id = item.icon),
            tint = colorResource(id = iconColor),
            contentDescription = "icon",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 12.sp,
            color = colorResource(id = textColor)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DrawerItemPreview() {
    NavigationDrawerItem(item = NavigationItem.HomeScreen, selected = false, onItemClick = {})
}