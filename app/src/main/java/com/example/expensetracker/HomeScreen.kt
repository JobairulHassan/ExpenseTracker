package com.example.expensetracker
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Surface(modifier = Modifier.fillMaxSize()){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.top_background), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
            })
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Column {
                    Text(
                        text = "Good Morning",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "JHK",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Icon(painter = painterResource(id = R.drawable.notifications),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
            val state = viewModel.expenses.collectAsState(initial = emptyList())
            val expenses = viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)
            CardItem(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, balance, income, expenses
            )
            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
                list = state.value
            )
        }
    }
}

@Composable
fun TransactionItem(title:String, amount: String, date:String, color: Color){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)){
        Row {
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp)
                Text(text = date, fontSize = 12.sp)
            }
        }
        Text(text = amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )

    }
}

@Composable
fun TransactionList(
    modifier: Modifier,
    list: List<ExpenseEntity>
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Recent Transactions", fontSize = 20.sp)
                Text(
                    text = "sell All",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        items(list) { item ->
            TransactionItem(
                title = item.title,
                amount = item.amount.toString(),
                date = item.date.toString(),
                color = if(item.type == "Income")Color.Green else Color.Red
            )
            
        }
    }
}

@Composable
fun CardItem(
    modifier: Modifier,
    balance: String,
    income: String,
    expenses: String
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.home_card_bg))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(
                    text = "Total Balance",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = balance,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            ){
            CardRowItem(modifier = Modifier.align(Alignment.CenterStart),
                title = "Income", amount = income, iconGet = R.drawable.arrow_downward)

            CardRowItem(modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense", amount = expenses, iconGet = R.drawable.arrow_upward)
        }

    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, iconGet: Int) {
    Column(modifier = modifier) {
        Row {

            Image(
                painter = painterResource(id = iconGet),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, fontSize = 16.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = amount, fontSize = 20.sp, color = Color.White)
    }
}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun PreviewHomeScreen() {
    HomeScreen()
}