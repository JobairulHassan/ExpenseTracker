package com.example.expensetracker

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.viewmodel.AddExpenseViewModel
import com.example.expensetracker.viewmodel.AddExpenseViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AddExpense(navController: NavController) {
    val viewModel: AddExpenseViewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White)) {
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
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

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
                    },
                onAddExpenseClick = {
                    coroutineScope.launch {
                        if(viewModel.addExpense(it)) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DataForm(modifier: Modifier, onAddExpenseClick: (model: ExpenseEntity) -> Unit) {
    val name = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val date = remember { mutableStateOf(0L) }
    val dateDialogVisibility = remember { mutableStateOf(false) }
    val category = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf("") }
    val amountError = remember { mutableStateOf("") }
    val dateError = remember { mutableStateOf("") }
    val categoryError = remember { mutableStateOf("") }
    val typeError = remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        var isValid = true

        // Name Validation
        if (name.value.isBlank()) {
            nameError.value = "Name cannot be empty"
            isValid = false
        } else {
            nameError.value = ""
        }

        // Amount Validation
        if (amount.value.isBlank()) {
            amountError.value = "Amount cannot be empty"
            isValid = false
        } else if (amount.value.toDoubleOrNull() == null) {
            amountError.value = "Invalid amount"
            isValid = false
        } else {
            amountError.value = ""
        }

        // Date Validation
        if (date.value == 0L) {
            dateError.value = "Please select a date"
            isValid = false
        } else {
            dateError.value = ""
        }

        // Category Validation
        if (category.value.isBlank()) {
            categoryError.value = "Category cannot be empty"
            isValid = false
        } else {
            categoryError.value = ""
        }

        // Type Validation
        if (type.value.isBlank()) {
            typeError.value = "Type cannot be empty"
            isValid = false
        } else {
            typeError.value = ""
        }

        return isValid
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Name
        Text(text = "Name", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter amount purpose") },
            isError = nameError.value.isNotEmpty()
        )
        if (nameError.value.isNotEmpty()) {
            Text(text = nameError.value, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Amount
        Text(text = "Amount", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("Enter amount") },
            isError = amountError.value.isNotEmpty()
        )
        if (amountError.value.isNotEmpty()) {
            Text(text = amountError.value, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Date
        Text(text = "Date", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = if (date.value == 0L) "" else Utils.dateFormatting(date.value),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            placeholder = { Text("Enter Date") },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            isError = dateError.value.isNotEmpty()
        )
        if (dateError.value.isNotEmpty()) {
            Text(text = dateError.value, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Category
        Text(text = "Category", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        DropdownItems(
            listOf("Salary", "Donation", "Family", "Festival", "Gift", "Home Appliances", "Medical", "Outing", "Personal", "Transport", "Utility"),
            onItemSelected = {
                category.value = it
            }
        )
        if (categoryError.value.isNotEmpty()) {
            Text(text = categoryError.value, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Type
        Text(text = "Type", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.size(8.dp))
        DropdownItems(
            listOf("Income", "Expense"),
            onItemSelected = {
                type.value = it
            }
        )
        if (typeError.value.isNotEmpty()) {
            Text(text = typeError.value, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Add Button
        Button(
            onClick = {
                if (validateForm()) {
                    val model = ExpenseEntity(
                        null,
                        name.value,
                        amount.value.toDoubleOrNull() ?: 0.0,
                        Utils.dateFormatting(date.value),
                        category.value,
                        type.value
                    )
                    onAddExpenseClick(model)
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        ) {
            Text(text = "Add Item", fontSize = 14.sp, color = Color.White)
        }
    }

    if (dateDialogVisibility.value) {
        DatePickerDialogue(
            onDateSelected = {
                date.value = it
                dateDialogVisibility.value = false
            },
            onDismiss = {
                dateDialogVisibility.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogue(
    onDateSelected:(date: Long) ->Unit,
    onDismiss: ()->Unit

) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownItems(listOfItems: List<String>, onItemSelected: (item:String) ->Unit) {
    val expended = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf("Select an Item")
    }
     ExposedDropdownMenuBox(expanded = expended.value, onExpandedChange = {expended.value= it}) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended.value)
            }
        )
         ExposedDropdownMenu(expanded = expended.value, onDismissRequest = { /*TODO*/ }) {
             listOfItems.forEach {
                 DropdownMenuItem(text = { Text(text = it)}, onClick = {
                     selectedItem.value = it
                     onItemSelected(selectedItem.value)
                     expended.value = false
                 })
             }
         }
    }
}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun AddExpensePreview() {
    AddExpense(rememberNavController())
}
