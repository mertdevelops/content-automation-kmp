package com.decidared.contentautomation.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(state : State<HomeScreenUiState>, event : (HomeScreenEvent) -> Unit,
               onNavigate: (Screen) -> Unit) {
    val previousContents = state.value.previousContents
    val welcomeText = state.value.welcomeText
    val subWelcomeText = state.value.subWelcomeText

    val quickSingleDescription = "Quickly generate a content with default settings"
    val quickMultipleDescription = "Generate at most 10 contents simultaneously with default settings"
    val customizedDescription = "Full control over all content creation parameters"

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().background(Color(26,26,26))) {
        Column(modifier = Modifier.fillMaxSize()) {
            Welcome(Modifier.weight(1.25f),welcomeText,subWelcomeText)
            //Menu(Modifier.weight(2.5f))
            Box (Modifier.weight(3f), contentAlignment = Alignment.Center) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)) {
                    CreationOptionCard(true,Icons.Filled.RocketLaunch,
                        "Single Quick Creation",
                        quickSingleDescription, { onNavigate(Screen.QuickSingle) })
                    CreationOptionCard(false,Icons.Filled.Layers, "Multiple Quick Creation",
                        quickMultipleDescription, {onNavigate(Screen.QuickMulti)})
                    CreationOptionCard(false,Icons.Filled.Tune, "Customized Creation",
                        customizedDescription, {})
                }
            }
            PreviousContents(Modifier.weight(1f), previousContents)
        }
    }
}

@Composable
private fun Welcome(modifier:Modifier,remoteConfigWelcomeText:String,remoteConfigSubWelcomeText:String) {
    val welcomeStyle = TextStyle(color = Color(0xFFFFFFFF), fontSize = 24.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.roboto_semibold)))
    val subWelcomeStyle  = TextStyle(color = Color(0xFF8A8A8A), fontSize = 16.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.roboto_regular)))

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically)) {
            Text(text = remoteConfigWelcomeText, style = welcomeStyle, modifier = Modifier.fillMaxWidth())
            Text(text = remoteConfigSubWelcomeText, style = subWelcomeStyle, modifier = Modifier.fillMaxWidth())
        }
    }
}


@Composable
private fun PreviousContents(modifier: Modifier, previousContents: List<Content>?) {
    if (!previousContents.isNullOrEmpty()) {
        Box(modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Previous Contents", color = Color.White, fontFamily = FontFamily(Font(R.font.roboto_regular)))
                LazyRow(Modifier.padding(8.dp),horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(previousContents.orEmpty().size) {
                        Box(Modifier.size(96.dp)) {
                            Box(Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)).background(Color.White)){

                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier.fillMaxSize())
    }
}

@Composable
fun CreationOptionCard(isActive:Boolean = false, icon: ImageVector, title: String, description: String, onNavigate: () -> Unit ) {
    if(isActive) {
        Card(
            modifier = Modifier.shadow(8.dp, RoundedCornerShape(16.dp),clip=false, spotColor = Color.White).size(width = 344.dp, height = 120.dp).clickable { onNavigate() },
            colors = CardDefaults.cardColors(containerColor = Color(58,58,58)),
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // To push arrow to the end
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(48.dp).background(Color(26,26,26).copy(0.4f), shape = RoundedCornerShape(8.dp)).clip(
                        RoundedCornerShape(8.dp)
                    ), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp) // Icon size
                        )
                    }
                    Column(Modifier.size(width = 248.dp, height = 120.dp),
                        verticalArrangement = Arrangement.Center) {
                        Text(
                            text = title,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontSize = 16.sp,
                            letterSpacing = 0.sp,
                            color = Color(0xFFFFFFFF)
                        )
                        Text(
                            text = description,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            letterSpacing = 0.sp,
                            color = Color(0xFF8A8A8A),
                        )
                    }
                }
                Box(Modifier.size(width = 32.dp, height = 120.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Go to $title",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}