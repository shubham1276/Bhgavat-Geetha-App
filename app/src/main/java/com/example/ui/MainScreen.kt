package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.screens.AboutGitaScreen
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.ChaptersScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ShareQuoteCardScreen
import com.example.ui.screens.VerseDetailScreen
import com.example.ui.theme.SaffronPrimary

@Composable
fun MainScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    // Tablet & large screen responsive container wrapper
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val maxWidth = maxWidth

        when (val screen = currentScreen) {
            is Screen.MainTabs -> {
                // If on secondary tab on MainTabs, back button returns to Home tab
                BackHandler(enabled = selectedTab != NavigationTab.HOME) {
                    viewModel.selectTab(NavigationTab.HOME)
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 6.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("main_navigation_bar")
                        ) {
                            val tabs = listOf(
                                NavigationTab.HOME to Icons.Default.Home,
                                NavigationTab.CHAPTERS to Icons.Default.MenuBook,
                                NavigationTab.SEARCH to Icons.Default.Search,
                                NavigationTab.FAVORITES to Icons.Default.Bookmark,
                                NavigationTab.SETTINGS to Icons.Default.Settings
                            )

                            tabs.forEach { (tab, icon) ->
                                val isSelected = selectedTab == tab
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { viewModel.selectTab(tab) },
                                    icon = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = tab.title
                                        )
                                    },
                                    label = { Text(tab.title) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = SaffronPrimary,
                                        selectedTextColor = SaffronPrimary,
                                        indicatorColor = SaffronPrimary.copy(alpha = 0.15f)
                                    ),
                                    modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .widthIn(max = 840.dp)
                                .align(Alignment.Center)
                        ) {
                            when (selectedTab) {
                                NavigationTab.HOME -> HomeScreen(viewModel = viewModel)
                                NavigationTab.CHAPTERS -> ChaptersScreen(viewModel = viewModel)
                                NavigationTab.SEARCH -> SearchScreen(viewModel = viewModel)
                                NavigationTab.FAVORITES -> FavoritesScreen(viewModel = viewModel)
                                NavigationTab.SETTINGS -> SettingsScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }

            is Screen.ChapterDetail -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 840.dp)
                        .align(Alignment.Center)
                ) {
                    ChapterDetailScreen(
                        chapterId = screen.chapterId,
                        viewModel = viewModel
                    )
                }
            }

            is Screen.VerseDetail -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 840.dp)
                        .align(Alignment.Center)
                ) {
                    VerseDetailScreen(
                        verseId = screen.verseId,
                        viewModel = viewModel
                    )
                }
            }

            is Screen.AboutGita -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 840.dp)
                        .align(Alignment.Center)
                ) {
                    AboutGitaScreen(
                        viewModel = viewModel
                    )
                }
            }

            is Screen.ShareQuoteCard -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 840.dp)
                        .align(Alignment.Center)
                ) {
                    ShareQuoteCardScreen(
                        verseId = screen.verseId,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
