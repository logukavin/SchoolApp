package com.sample.mvvmcomposesetup.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sample.mvvmcomposesetup.model.SchoolListResponse


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListItemView(item: SchoolListResponse,onItemClick: (SchoolListResponse) -> Unit) {

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
    ) {

        Row(

            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),

            ) {


            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.school_name.toString(),
                    color = Color.Black,
                    style = typography.labelSmall,
                    modifier = Modifier.absolutePadding(
                        left = 10.dp, right = 10.dp, top = 10.dp, bottom = 10.dp
                    )
                )
            }

        }
    }
}


