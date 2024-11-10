package com.example.expensetracker

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun AddExpense() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.top_background),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "Add Item",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
            DataForm(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun DataForm(modifier:Modifier) {

    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxWidth()
        .shadow(8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ){

        Text(text = "Name", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Amount", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Date", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Type", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        ) {
            Text(text = "Add Item", fontSize = 14.sp, color=Color.White)
        }
    }

}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun AddExpensePreview() {
    AddExpense()
}