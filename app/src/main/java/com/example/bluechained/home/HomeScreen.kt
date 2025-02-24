@file:Suppress("DEPRECATION")

package com.example.bluechained.home

import android.widget.CheckBox
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bluechained.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false)}
    var newMessage by remember { mutableStateOf("")}

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.secondary),
                        colorResource(R.color.secondary_gradient)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.background_gradient),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            TopHeader()
            Spacer(modifier = Modifier.height(16.dp))
            HeaderGreeting()
            Spacer(modifier = Modifier.height(24.dp))
            BlockChainStatus()
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = stringResource(R.string.recent_block),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.primary)
                    )
                )
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Toggle Dark Mode",
                    tint = colorResource(R.color.tertiary),
                    modifier = Modifier.size(32.dp)
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            BlockChainContent()
        }

        FloatingActionButton(
            onClick = {showDialog = true},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = colorResource(R.color.tertiary)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Message",
                tint = Color.White
            )
        }

        if(showDialog){
            NewBlockDialogBox(showDialog = showDialog, onDismiss = { showDialog = false }, newMessage, onMessageChange = { newMessage = it })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewBlockDialogBox(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    newMessage: String,
    onMessageChange: (String) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }


    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = colorResource(R.color.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "New Block", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                ))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Enter your new message:", style = TextStyle(
                    color = colorResource(R.color.white)
                ))
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = newMessage,
                    onValueChange = onMessageChange,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colorResource(id = R.color.white)
                    ),
                    placeholder = { Text("Type your message here...",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.primary_dark)
                        )
                    ) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Encrypt",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = colorResource(R.color.white)
                            )
                        )
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = colorResource(R.color.primary)
                            )
                        )
                    }
                    Row {
                        TextButton(
                            onClick = {
                                viewModel.addNewBlock(newMessage,isChecked)
                                onMessageChange("")
                                onDismiss()
                            }
                        ) {
                            Text(
                                "Cancel",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.white)
                                )
                            )
                        }
                        TextButton(
                            onClick = {
                                onMessageChange("")
                                onDismiss()
                            }
                        ) {
                            Text(
                                "Add",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.white)
                                )
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewBlockDialogBoxPreview() {
    var showDialog by remember { mutableStateOf(false)}
    var newMessage by remember { mutableStateOf("")}
    NewBlockDialogBox(showDialog = showDialog, onDismiss = { showDialog = false }, newMessage, onMessageChange = { newMessage = it })
}


@Composable
fun HeaderGreeting() {
    Column {
        Text(
            text = "Welcome to " + stringResource(R.string.app_name),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            color = colorResource(R.color.primary)
        )
        Text(
            text = stringResource(R.string.app_slogan),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            ),
            color = colorResource(R.color.primary)
        )
    }
}

@Composable
private fun BlockChainStatus() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.primary))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "BlueChained Block Status",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = colorResource(R.color.white)
            )
            Text(
                text = "Number of Blocks",
                style = TextStyle(
                    fontSize = 12.sp
                ),
                color = colorResource(R.color.white)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white)
                    )
                )
                Text(
                    text = "Active",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = colorResource(R.color.white)
                )
            }
        }
    }
}

@Composable
private fun BlockChainContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(5) {
            BlockChainedCard()
        }
    }
}

@Composable
fun BlockChainedCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.primary)),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.block_no),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = colorResource(R.color.white)
            )
        }
    }
}


@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.bluechained_logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier.size(40.dp)
        )
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.dark_mode_24px),
                contentDescription = "Toggle Dark Mode",
                tint = colorResource(R.color.primary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewTopHeader() {
    TopHeader()
}
