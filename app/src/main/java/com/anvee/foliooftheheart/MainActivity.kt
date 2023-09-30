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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.res.painterResource
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
                .open("csv/core_spells.csv")
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
                        var fireEnabled by remember {
                            mutableStateOf(true)
                        }
                        var iceEnabled by remember {
                            mutableStateOf(true)
                        }
                        var windEnabled by remember {
                            mutableStateOf(true)
                        }
                        var thunderEnabled by remember {
                            mutableStateOf(true)
                        }
                        var nukeEnabled by remember {
                            mutableStateOf(true)
                        }
                        var psyEnabled by remember {
                            mutableStateOf(true)
                        }
                        var lightEnabled by remember {
                            mutableStateOf(true)
                        }
                        var darkEnabled by remember {
                            mutableStateOf(true)
                        }
                        var almightyEnabled by remember {
                            mutableStateOf(true)
                        }
                        var healEnabled by remember {
                            mutableStateOf(true)
                        }
                        var buffEnabled by remember {
                            mutableStateOf(true)
                        }
                        var debuffEnabled by remember {
                            mutableStateOf(true)
                        }
                        var statusEnabled by remember {
                            mutableStateOf(true)
                        }
                        var intelEnabled by remember {
                            mutableStateOf(true)
                        }
                        var defenseEnabled by remember {
                            mutableStateOf(true)
                        }
                        var miscEnabled by remember {
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
                                title = {
                                    Text(text = "Toggle Spell Types")
                                },
                                text = {
                                    Column {
                                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                            IconToggleButton(
                                                checked = physEnabled,
                                                onCheckedChange = { physEnabled = it }) {
                                                Image(
                                                    painter = painterResource(
                                                        id = if (physEnabled) R.drawable.phys_enabled else R.drawable.phys_disabled
                                                    ),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton (
                                                checked = fireEnabled,
                                                onCheckedChange = { fireEnabled = it }) {
                                                Image (
                                                    painter = painterResource (
                                                        id = if (fireEnabled) R.drawable.fire_enabled else R.drawable.fire_disabled
                                                    ),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = iceEnabled,
                                                onCheckedChange = { iceEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (iceEnabled) R.drawable.ice_enabled else R.drawable.ice_disabled),
                                                    contentDescription = null
                                                )
                                            }
                                            IconToggleButton(
                                                checked = windEnabled,
                                                onCheckedChange = { windEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (windEnabled) R.drawable.wind_enabled else R.drawable.wind_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = thunderEnabled,
                                                onCheckedChange = { thunderEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (thunderEnabled) R.drawable.thunder_enabled else R.drawable.thunder_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                            IconToggleButton(
                                                checked = nukeEnabled,
                                                onCheckedChange = { nukeEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (nukeEnabled) R.drawable.nuke_enabled else R.drawable.nuke_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = psyEnabled,
                                                onCheckedChange = { psyEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (psyEnabled) R.drawable.psy_enabled else R.drawable.psy_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = lightEnabled,
                                                onCheckedChange = { lightEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (lightEnabled) R.drawable.light_enabled else R.drawable.light_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = darkEnabled,
                                                onCheckedChange = { darkEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (darkEnabled) R.drawable.dark_enabled else R.drawable.dark_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = almightyEnabled,
                                                onCheckedChange = { almightyEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (almightyEnabled) R.drawable.almighty_enabled else R.drawable.almighty_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                            IconToggleButton(
                                                checked = healEnabled,
                                                onCheckedChange = { healEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (healEnabled) R.drawable.heal_enabled else R.drawable.heal_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = buffEnabled,
                                                onCheckedChange = { buffEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (buffEnabled) R.drawable.buff_enabled else R.drawable.buff_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = debuffEnabled,
                                                onCheckedChange = { debuffEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (debuffEnabled) R.drawable.debuff_enabled else R.drawable.debuff_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = statusEnabled,
                                                onCheckedChange = { statusEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (statusEnabled) R.drawable.status_enabled else R.drawable.status_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = intelEnabled,
                                                onCheckedChange = { intelEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (intelEnabled) R.drawable.intel_enabled else R.drawable.intel_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                            IconToggleButton(
                                                checked = defenseEnabled,
                                                onCheckedChange = { defenseEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (defenseEnabled) R.drawable.defense_enabled else R.drawable.defense_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                            IconToggleButton(
                                                checked = miscEnabled,
                                                onCheckedChange = { miscEnabled = it }) {
                                                Image(
                                                    painter = painterResource(id = if (miscEnabled) R.drawable.misc_enabled else R.drawable.misc_disabled),
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                    }
                                },
                                confirmButton = {
                                    TextButton(onClick = {
                                        openDialog = false
                                    }) {
                                        Text(text = "Close Options")
                                    }
                                }
                            )
                        }
                        Button(onClick = {
                            if (searchQuery.isNotBlank()) {
                                namesSearched = namesSearched.toMutableList().apply{clear()}
                                for (i in searchableList.indices) {
                                    val item = searchableList[i]
                                    var addItem = true
                                    if (item[0].lowercase().contains(searchQuery.lowercase())) {
                                        when (item[1]) {
                                            "Physical" -> if (!physEnabled) addItem = false
                                            "Fire" -> if (!fireEnabled) addItem = false
                                            "Ice" -> if (!iceEnabled) addItem = false
                                            "Wind" -> if (!windEnabled) addItem = false
                                            "Thunder" -> if (!thunderEnabled) addItem = false
                                            "Nuclear" -> if (!nukeEnabled) addItem = false
                                            "Psychokinesis (PSY)" -> if (!psyEnabled) addItem = false
                                            "Light" -> if (!lightEnabled) addItem = false
                                            "Dark" -> if (!darkEnabled) addItem = false
                                            "Almighty" -> if (!almightyEnabled) addItem = false
                                            "Heal" -> if (!healEnabled) addItem = false
                                            "Buff" -> if (!buffEnabled) addItem = false
                                            "Debuff" -> if (!debuffEnabled) addItem = false
                                            "Status" -> if (!statusEnabled) addItem = false
                                            "Intel" -> if (!intelEnabled) addItem = false
                                            "Defense" -> if (!defenseEnabled) addItem = false
                                            "Miscellaneous" -> if (!miscEnabled) addItem = false
                                        }
                                        if (addItem) namesSearched = namesSearched + listOf(item)
                                    }
                                }
                                if (namesSearched.isEmpty()) {
                                    namesSearched = listOf(listOf(
                                        "[No Results]", "[N/A]", "[N/A]", "[N/A]",
                                        "[N/A]", "[N/A]", "[N/A]", "[N/A]")
                                    )
                                }
                                searchQuery = ""
                            } else {
                                namesSearched = namesSearched.toMutableList().apply{clear()}
                                for (i in searchableList.indices) {
                                    val item = searchableList[i]
                                    var addItem = true
                                    if (item[0].lowercase().contains(searchQuery.lowercase())) {
                                        when (item[1]) {
                                            "Physical" -> if (!physEnabled) addItem = false
                                            "Fire" -> if (!fireEnabled) addItem = false
                                            "Ice" -> if (!iceEnabled) addItem = false
                                            "Wind" -> if (!windEnabled) addItem = false
                                            "Thunder" -> if (!thunderEnabled) addItem = false
                                            "Nuclear" -> if (!nukeEnabled) addItem = false
                                            "Psychokinesis (PSY)" -> if (!psyEnabled) addItem = false
                                            "Light" -> if (!lightEnabled) addItem = false
                                            "Dark" -> if (!darkEnabled) addItem = false
                                            "Almighty" -> if (!almightyEnabled) addItem = false
                                            "Heal" -> if (!healEnabled) addItem = false
                                            "Buff" -> if (!buffEnabled) addItem = false
                                            "Debuff" -> if (!debuffEnabled) addItem = false
                                            "Status" -> if (!statusEnabled) addItem = false
                                            "Intel" -> if (!intelEnabled) addItem = false
                                            "Defense" -> if (!defenseEnabled) addItem = false
                                            "Miscellaneous" -> if (!miscEnabled) addItem = false
                                        }
                                        if (addItem) namesSearched = namesSearched + listOf(item)
                                    }
                                }
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