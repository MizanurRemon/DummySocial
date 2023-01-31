package com.example.dummysocial.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.dummysocial.R
import com.example.dummysocial.ui.theme.DummySocialTheme

@Composable
fun PracticeScreen() {
    DummySocialTheme() {
        Scaffold() {

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {

                val (box1, box2, box3) = createRefs()
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(colorResource(id = R.color.yellow))
                        .constrainAs(box1) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                )

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(colorResource(id = R.color.Orange))
                        .constrainAs(box2) {
                            start.linkTo(parent.start)
                            top.linkTo(box1.bottom, margin = 10.dp)
                        }
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(colorResource(id = R.color.green))
                        .constrainAs(box3) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DesignPreview() {
    PracticeScreen()
}