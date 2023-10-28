package com.anvee.foliooftheheart

import android.content.res.Configuration
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
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.anvee.foliooftheheart.ui.theme.FolioOfTheHeartTheme

const val EXPANSION_ANIMATION_DURATION = 50 // Animation duration for expanding spells
var appLoading = true // Controls Shimmers


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition{appLoading}
        }
        setContent {
            // Get orientation
            val orientation = LocalConfiguration.current.orientation
            // Load spell arrays. If you want to import your own spells, follow the template here.
            val coreArray = applicationContext.assets
                .open("csv/core_spells.csv")
                .bufferedReader()
                .useLines {it.toList()}
            val seaArray = applicationContext.assets
                .open("csv/sea_spells.csv")
                .bufferedReader()
                .useLines{it.toList()}
            /*
            val yourArray = applicationContext.assets
                .open(csv/your_spells.csv)
                .bufferedReader()
                .useLines{it.toList()}
            */
            // Join the loaded arrays and Parse them
            val joinedArray = (coreArray.drop(1)
                    // .drop(1) prevents the csv header from being added as a spell.
                    + seaArray.drop(1)
                    //+ yourArray.drop(1)
            )
            val searchableList = List(joinedArray.size) { index ->
                // Parses joinedArray into a format for the Expandable Cards to use
                joinedArray[index].replace("\"", "").split(",(?! )".toRegex())
            }
            // Declare application 'spell's
            val contentDisclaimer = listOf("[Content Disclaimer]", "Read", "Me",
                "[N/A]", "[N/A]", "[N/A]", "[N/A]",
                "This program is not official Grimoire of the Heart Content. " +
                        "It is intended to be used as a quick reference; however it is fallible. " +
                        "Please check the source material for ruling disputes. " +
                        "Moreover, if you find an error in the data presented, please address it to me on Discord: _anvee. " +
                        "Please do not bother the Core team over issues and suggestions with this app.")
            val noSpells = listOf("[No Results]", "[N/A]", "[N/A]",
                "[N/A]", "[N/A]", "[N/A]", "[N/A]",
                "No spells were found for your search. " +
                        "You may have mistyped the spell you were looking for, " +
                        "or you may have to adjust your spell filters. (Click the cog to do so.) " +
                        "It is also possible that the spell you are searching has not been entered into the app or that the spell has been entered into the app incorrectly." +
                        "Feel free to contact me on Discord (_anvee) if you believe there may be an issue with the data.")
            // Declare Search Variables
            var expandedItem by remember {
                // Name of the currently expanded spell
                mutableStateOf("")
            }
            var searchQuery by remember {
                // The current entry of the search field
                mutableStateOf("")
            }
            var namesSearched by remember {
                // The list of spells pulled from the searchableList using the searchQuery
                mutableStateOf(listOf<List<String>>())
            }
            var toExpandable by remember {
                // The list of spells that get turned into expandable spells.
                mutableStateOf(searchableList)
            }
            // Declare toggleable filter variables
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
            var waterEnabled by remember {
                mutableStateOf(true)
            }
            var earthEnabled by remember {
                mutableStateOf(true)
            }
            var fusionEnabled by remember {
                mutableStateOf(true)
            }
            var cosmicEnabled by remember {
                mutableStateOf(true)
            }
            var occultEnabled by remember {
                mutableStateOf(true)
            }
            var gunEnabled by remember {
                mutableStateOf(true)
            }
            var bloomEnabled by remember {
                mutableStateOf(true)
            }
            var weatherEnabled by remember {
                mutableStateOf(true)
            }
            var disclaimerEnabled by remember {
                mutableStateOf(true)
            }
            // Track whether settings dialog is open
            var openDialog by remember {
                mutableStateOf(false)
            }
            // Drawing Begins Here
            FolioOfTheHeartTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row ( // Searchbar, Settings, Search Button
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
                        IconButton(onClick = { openDialog = true }) {
                            Icon(imageVector = Icons.Default.Settings,
                                contentDescription = "Filter Spells")
                        }
                        // Settings Dialog
                        if (openDialog) {
                            AlertDialog(
                                onDismissRequest = { openDialog = false },
                                title = {Text(text = "Toggle Spell Types")                                },
                                properties = DialogProperties(usePlatformDefaultWidth = false),
                                text = {
                                    when (orientation) {
                                        Configuration.ORIENTATION_PORTRAIT -> {
                                            Column (
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween,
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = physEnabled,
                                                        onCheckedChange = { physEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (physEnabled) R.drawable.phys_enabled else R.drawable.phys_disabled),
                                                            contentDescription = "Toggle Phys",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = fireEnabled,
                                                        onCheckedChange = { fireEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (fireEnabled) R.drawable.fire_enabled else R.drawable.fire_disabled),
                                                            contentDescription = "Toggle Fire",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = iceEnabled,
                                                        onCheckedChange = { iceEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (iceEnabled) R.drawable.ice_enabled else R.drawable.ice_disabled),
                                                            contentDescription = "Toggle Ice"
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = windEnabled,
                                                        onCheckedChange = { windEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (windEnabled) R.drawable.wind_enabled else R.drawable.wind_disabled),
                                                            contentDescription = "Toggle Wind",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = thunderEnabled,
                                                        onCheckedChange = { thunderEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (thunderEnabled) R.drawable.thunder_enabled else R.drawable.thunder_disabled),
                                                            contentDescription = "Toggle Thunder",
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = nukeEnabled,
                                                        onCheckedChange = { nukeEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (nukeEnabled) R.drawable.nuke_enabled else R.drawable.nuke_disabled),
                                                            contentDescription = "Toggle Nuke",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = psyEnabled,
                                                        onCheckedChange = { psyEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (psyEnabled) R.drawable.psy_enabled else R.drawable.psy_disabled),
                                                            contentDescription = "Toggle Psy",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = lightEnabled,
                                                        onCheckedChange = { lightEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (lightEnabled) R.drawable.light_enabled else R.drawable.light_disabled),
                                                            contentDescription = "Toggle Light",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = darkEnabled,
                                                        onCheckedChange = { darkEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (darkEnabled) R.drawable.dark_enabled else R.drawable.dark_disabled),
                                                            contentDescription = "Toggle Dark",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = almightyEnabled,
                                                        onCheckedChange = { almightyEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (almightyEnabled) R.drawable.almighty_enabled else R.drawable.almighty_disabled),
                                                            contentDescription = "Toggle Almighty",
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = healEnabled,
                                                        onCheckedChange = { healEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (healEnabled) R.drawable.heal_enabled else R.drawable.heal_disabled),
                                                            contentDescription = "Toggle Heal",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = buffEnabled,
                                                        onCheckedChange = { buffEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (buffEnabled) R.drawable.buff_enabled else R.drawable.buff_disabled),
                                                            contentDescription = "Toggle Buff",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = debuffEnabled,
                                                        onCheckedChange = { debuffEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (debuffEnabled) R.drawable.debuff_enabled else R.drawable.debuff_disabled),
                                                            contentDescription = "Toggle Debuff",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = statusEnabled,
                                                        onCheckedChange = { statusEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (statusEnabled) R.drawable.status_enabled else R.drawable.status_disabled),
                                                            contentDescription = "Toggle Status",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = intelEnabled,
                                                        onCheckedChange = { intelEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (intelEnabled) R.drawable.intel_enabled else R.drawable.intel_disabled),
                                                            contentDescription = "Toggle Intel",
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = defenseEnabled,
                                                        onCheckedChange = { defenseEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (defenseEnabled) R.drawable.defense_enabled else R.drawable.defense_disabled),
                                                            contentDescription = "Toggle Defense",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = miscEnabled,
                                                        onCheckedChange = { miscEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (miscEnabled) R.drawable.misc_enabled else R.drawable.misc_disabled),
                                                            contentDescription = "Toggle Miscellaneous",
                                                        )
                                                    }
                                                }
                                                Divider()
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = waterEnabled,
                                                        onCheckedChange = { waterEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (waterEnabled) R.drawable.water_enabled else R.drawable.water_disabled),
                                                            contentDescription = "Toggle Water",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = earthEnabled,
                                                        onCheckedChange = { earthEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (earthEnabled) R.drawable.earth_enabled else R.drawable.earth_disabled),
                                                            contentDescription = "Toggle Earth",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = fusionEnabled,
                                                        onCheckedChange = { fusionEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (fusionEnabled) R.drawable.fusion_enabled else R.drawable.fusion_disabled),
                                                            contentDescription = "Toggle Fusion",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = cosmicEnabled,
                                                        onCheckedChange = { cosmicEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (cosmicEnabled) R.drawable.cosmic_enabled else R.drawable.cosmic_disabled),
                                                            contentDescription = "Toggle Cosmic",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = occultEnabled,
                                                        onCheckedChange = { occultEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (occultEnabled) R.drawable.occult_enabled else R.drawable.occult_disabled),
                                                            contentDescription = "Toggle Occult",
                                                        )
                                                    }
                                                }
                                                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                                    IconToggleButton(
                                                        checked = gunEnabled,
                                                        onCheckedChange = { gunEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (gunEnabled) R.drawable.gun_enabled else R.drawable.gun_disabled),
                                                            contentDescription = "Toggle Gun",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = bloomEnabled,
                                                        onCheckedChange = { bloomEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (bloomEnabled) R.drawable.bloom_enabled else R.drawable.bloom_disabled),
                                                            contentDescription = "Toggle Bloom",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = weatherEnabled,
                                                        onCheckedChange = { weatherEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (weatherEnabled) R.drawable.weather_enabled else R.drawable.weather_disabled),
                                                            contentDescription = "Toggle Weather",
                                                        )
                                                    }
                                                }
                                                Divider()
                                                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                                    Text(text = "Show Disclaimer ")
                                                    Switch(
                                                        checked = disclaimerEnabled,
                                                        onCheckedChange = { disclaimerEnabled = it }
                                                    )
                                                }
                                                Text (text = "Build: Release 1 (2023/10/28)", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                            }
                                        } else -> {
                                            Column (
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = physEnabled,
                                                        onCheckedChange = { physEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (physEnabled) R.drawable.phys_enabled else R.drawable.phys_disabled),
                                                            contentDescription = "Toggle Phys",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = fireEnabled,
                                                        onCheckedChange = { fireEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (fireEnabled) R.drawable.fire_enabled else R.drawable.fire_disabled),
                                                            contentDescription = "Toggle Fire",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = iceEnabled,
                                                        onCheckedChange = { iceEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (iceEnabled) R.drawable.ice_enabled else R.drawable.ice_disabled),
                                                            contentDescription = "Toggle Ice"
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = windEnabled,
                                                        onCheckedChange = { windEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (windEnabled) R.drawable.wind_enabled else R.drawable.wind_disabled),
                                                            contentDescription = "Toggle Wind",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = thunderEnabled,
                                                        onCheckedChange = { thunderEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (thunderEnabled) R.drawable.thunder_enabled else R.drawable.thunder_disabled),
                                                            contentDescription = "Toggle Thunder",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = nukeEnabled,
                                                        onCheckedChange = { nukeEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (nukeEnabled) R.drawable.nuke_enabled else R.drawable.nuke_disabled),
                                                            contentDescription = "Toggle Nuke",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = psyEnabled,
                                                        onCheckedChange = { psyEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (psyEnabled) R.drawable.psy_enabled else R.drawable.psy_disabled),
                                                            contentDescription = "Toggle Psy",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = lightEnabled,
                                                        onCheckedChange = { lightEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (lightEnabled) R.drawable.light_enabled else R.drawable.light_disabled),
                                                            contentDescription = "Toggle Light",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = darkEnabled,
                                                        onCheckedChange = { darkEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (darkEnabled) R.drawable.dark_enabled else R.drawable.dark_disabled),
                                                            contentDescription = "Toggle Dark",
                                                        )
                                                    }
                                                }
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = almightyEnabled,
                                                        onCheckedChange = { almightyEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (almightyEnabled) R.drawable.almighty_enabled else R.drawable.almighty_disabled),
                                                            contentDescription = "Toggle Almighty",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = healEnabled,
                                                        onCheckedChange = { healEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (healEnabled) R.drawable.heal_enabled else R.drawable.heal_disabled),
                                                            contentDescription = "Toggle Heal",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = buffEnabled,
                                                        onCheckedChange = { buffEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (buffEnabled) R.drawable.buff_enabled else R.drawable.buff_disabled),
                                                            contentDescription = "Toggle Buff",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = debuffEnabled,
                                                        onCheckedChange = { debuffEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (debuffEnabled) R.drawable.debuff_enabled else R.drawable.debuff_disabled),
                                                            contentDescription = "Toggle Debuff",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = statusEnabled,
                                                        onCheckedChange = { statusEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (statusEnabled) R.drawable.status_enabled else R.drawable.status_disabled),
                                                            contentDescription = "Toggle Status",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = intelEnabled,
                                                        onCheckedChange = { intelEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (intelEnabled) R.drawable.intel_enabled else R.drawable.intel_disabled),
                                                            contentDescription = "Toggle Intel",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = defenseEnabled,
                                                        onCheckedChange = { defenseEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (defenseEnabled) R.drawable.defense_enabled else R.drawable.defense_disabled),
                                                            contentDescription = "Toggle Defense",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = miscEnabled,
                                                        onCheckedChange = { miscEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (miscEnabled) R.drawable.misc_enabled else R.drawable.misc_disabled),
                                                            contentDescription = "Toggle Miscellaneous",
                                                        )
                                                    }
                                                }
                                                Divider()
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    IconToggleButton(
                                                        checked = waterEnabled,
                                                        onCheckedChange = { waterEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (waterEnabled) R.drawable.water_enabled else R.drawable.water_disabled),
                                                            contentDescription = "Toggle Water",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = earthEnabled,
                                                        onCheckedChange = { earthEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (earthEnabled) R.drawable.earth_enabled else R.drawable.earth_disabled),
                                                            contentDescription = "Toggle Earth",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = fusionEnabled,
                                                        onCheckedChange = { fusionEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (fusionEnabled) R.drawable.fusion_enabled else R.drawable.fusion_disabled),
                                                            contentDescription = "Toggle Fusion",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = cosmicEnabled,
                                                        onCheckedChange = { cosmicEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (cosmicEnabled) R.drawable.cosmic_enabled else R.drawable.cosmic_disabled),
                                                            contentDescription = "Toggle Cosmic",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = occultEnabled,
                                                        onCheckedChange = { occultEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (occultEnabled) R.drawable.occult_enabled else R.drawable.occult_disabled),
                                                            contentDescription = "Toggle Occult",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = gunEnabled,
                                                        onCheckedChange = { gunEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (gunEnabled) R.drawable.gun_enabled else R.drawable.gun_disabled),
                                                            contentDescription = "Toggle Gun",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = bloomEnabled,
                                                        onCheckedChange = { bloomEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (bloomEnabled) R.drawable.bloom_enabled else R.drawable.bloom_disabled),
                                                            contentDescription = "Toggle Bloom",
                                                        )
                                                    }
                                                    IconToggleButton(
                                                        checked = weatherEnabled,
                                                        onCheckedChange = { weatherEnabled = it }) {
                                                        Image(
                                                            painter = painterResource(id = if (weatherEnabled) R.drawable.weather_enabled else R.drawable.weather_disabled),
                                                            contentDescription = "Toggle Weather",
                                                        )
                                                    }
                                                }
                                                Divider()
                                                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                                    Text(text = "Show Disclaimer ")
                                                    Switch(
                                                        checked = disclaimerEnabled,
                                                        onCheckedChange = { disclaimerEnabled = it }
                                                    )
                                                }
                                                Text (text = "Build: Release 1 (2023/10/28)", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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
                                if (disclaimerEnabled) namesSearched = namesSearched + listOf(contentDisclaimer)
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
                                            "Water" -> if (!waterEnabled) addItem = false
                                            "Earth" -> if (!earthEnabled) addItem = false
                                            "Fusion" -> if (!fusionEnabled) addItem = false
                                            "Cosmic" -> if (!cosmicEnabled) addItem = false
                                            "Occult" -> if (!occultEnabled) addItem = false
                                            "Gun" -> if (!gunEnabled) addItem = false
                                            "Bloom" -> if (!bloomEnabled) addItem = false
                                            "Weather" -> if (!weatherEnabled) addItem = false
                                        }
                                        if (item[1].contains("Fusion") && (!fusionEnabled)) addItem = false
                                        if (addItem) namesSearched = namesSearched + listOf(item)
                                    }
                                }
                                if (namesSearched.isEmpty() || (namesSearched.size == 1 && disclaimerEnabled)) {
                                    namesSearched = namesSearched + listOf(noSpells)
                                }
                                searchQuery = ""
                            } else {
                                namesSearched = namesSearched.toMutableList().apply{clear()}
                                if (disclaimerEnabled) namesSearched = namesSearched + listOf(contentDisclaimer)
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
                                            "Water" -> if (!waterEnabled) addItem = false
                                            "Earth" -> if (!earthEnabled) addItem = false
                                            "Cosmic" -> if (!cosmicEnabled) addItem = false
                                            "Occult" -> if (!occultEnabled) addItem = false
                                            "Gun" -> if (!gunEnabled) addItem = false
                                            "Bloom" -> if (!bloomEnabled) addItem = false
                                            "Weather" -> if (!weatherEnabled) addItem = false
                                        }
                                        if (item[1].contains("Fusion") && (!fusionEnabled)) addItem = false
                                        if (addItem) namesSearched = namesSearched + listOf(item)
                                    }
                                }
                                if (namesSearched.isEmpty() || (namesSearched.size == 1 && disclaimerEnabled)) {
                                    namesSearched = namesSearched + listOf(noSpells)
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Spells")
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    toExpandable = namesSearched.ifEmpty {
                        listOf(contentDisclaimer) + searchableList
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
                appLoading = false
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