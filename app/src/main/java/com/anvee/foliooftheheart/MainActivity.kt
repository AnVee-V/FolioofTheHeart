package com.anvee.foliooftheheart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.anvee.foliooftheheart.ui.theme.FolioOfTheHeartTheme

const val EXPANSION_ANIMATION_DURATION = 50

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var csvArray = applicationContext.assets
                .open("core_spells.csv")
                .bufferedReader()
                .useLines { it.toList() }
            csvArray = List(csvArray.size-1) {index ->
                csvArray[index+1].replace("\"", "")
            }
            val searchableList = List(csvArray.size) {index ->
                csvArray[index].split(",(?! )".toRegex())
            }

            var expandedItem by remember {
                mutableStateOf("")
            }
            var searchQuery by remember {
                mutableStateOf("")
            }
            var namesSearched by remember {
                mutableStateOf(listOf<List<String>>())
            }

            var toExpandable by remember {
                mutableStateOf(searchableList)
            }
            FolioOfTheHeartTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { text ->
                                searchQuery = text
                            },
                            label = { Text(text = "Search Spells")},
                            placeholder = { Text(text = "Click Search to Refresh")},
                            modifier = Modifier.weight(1f)
                        )
                        var physEnabled by remember {
                            mutableStateOf(true)
                        }
                        var openDialog by remember {
                            mutableStateOf(false)
                        }

                        IconButton(onClick = { openDialog = true }) {
                            Icon(imageVector = Icons.Default.Settings,
                                contentDescription = "Filter Spells")
                        }

                        if (openDialog) {
                            AlertDialog(
                                onDismissRequest = { openDialog = false },
                                /*title = {
                                    Text(text = "Disable Physical Spells")
                                },
                                text = {
                                    Text(text = "Are you sure about that?")
                                },*/
                                confirmButton = {
                                    TextButton(onClick = {
                                        physEnabled = true
                                        openDialog = false
                                    }) {
                                        Text(text = "Enable Phys")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        physEnabled = false
                                        openDialog = false
                                    }) {
                                        Text(text = "Disable Phys")
                                    }
                                }
                            )
                        }
                        Button(onClick = {
                            if (searchQuery.isNotBlank()) {
                                namesSearched = namesSearched.toMutableList().apply{clear()}
                                for (i in searchableList.indices) {
                                    val item = searchableList[i]
                                    if (item[0].lowercase().contains(searchQuery.lowercase())) {
                                        if (item[1] != "Physical" || (item[1] == "Physical" && physEnabled)) {
                                            namesSearched = namesSearched + listOf(item)
                                        }
                                    } else if (i == searchableList.size-1 && namesSearched.isEmpty()) {
                                        namesSearched = listOf(listOf(
                                            "[No Results]", "[N/A]", "[N/A]", "[N/A]", "[N/A]",
                                            "[N/A]", "[N/A]", "[N/A]"))
                                    }
                                }
                                searchQuery = ""
                            } else {
                                namesSearched = searchableList
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Spells")
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    toExpandable = namesSearched.ifEmpty {
                        searchableList
                    }
                    var isLoading by remember {
                        mutableStateOf(true)
                    }
                    LazyColumn {
                        items(toExpandable) { spell ->
                            ShimmerListItem(isLoading = isLoading, contentAfterLoading = {
                                ExpandableCard(
                                    content = spell,
                                    expanded = expandedItem == spell[0],
                                    onClickExpanded = { spellName ->
                                        expandedItem = if (expandedItem == spellName) {
                                            ""
                                        } else {
                                            spellName
                                        }
                                    }
                                )
                            }
                            )
                        }
                        isLoading = false
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    content: List<String>,
    expanded: Boolean,
    onClickExpanded: (spellName: String) -> Unit,
) {
    Card {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                onClickExpanded(content[0])
            }
        ) {
            Text(text = content[0], fontWeight = FontWeight.Bold)
            Text(text = "${content[1]} ${content[2]}", fontStyle = FontStyle.Italic)
            ExpandableContent(isExpanded = expanded, spellContent = content)
        }
    }
}

@Composable
fun ExpandableContent(
    isExpanded: Boolean,
    spellContent: List<String>,
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSION_ANIMATION_DURATION)
        ) + fadeIn(
            initialAlpha = .3f,
            animationSpec = tween(EXPANSION_ANIMATION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSION_ANIMATION_DURATION)
        ) + fadeOut(animationSpec = tween(EXPANSION_ANIMATION_DURATION))
    }
    
    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Divider()
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Categories ")
                    }
                    append(spellContent[3])
                }
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Reach ")
                    }
                    append(spellContent[4])
                }
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Time ")
                    }
                    append(spellContent[5])
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(", Duration ")
                    }
                    append(spellContent[6])
                }
            )
            Divider()
            Text(text = spellContent[7], textAlign = TextAlign.Justify)
        }
    }
}

/* DEPRECATED
            val csvString = applicationContext.assets
                .open("core_spells.csv")
                .bufferedReader()
                .use { it.readText() }
            val csvToParse = csvString.split("\\r?\\n".toRegex())

            var names by remember {
                mutableStateOf(listOf<Spell>())
            }
            for (i in 1 until csvToParse.size) {
                if (csvToParse[i].isNotEmpty()) {
                    val workingElement = csvToParse[i].split("(?<=,)(?! )".toRegex())
                    names += Spell(
                        workingElement[0].removeSuffix(","),
                        workingElement[1].removeSuffix(","), workingElement[2].removeSuffix(","),
                        workingElement[3].removePrefix("\"").removeSuffix(",").removeSuffix("\""),
                        workingElement[4].removeSuffix(","),
                        workingElement[5].removeSuffix(","), workingElement[6].removeSuffix(","),
                        workingElement[7].removePrefix("\"").removeSuffix(",").removeSuffix("\"")
                    )
                }

            }
*/