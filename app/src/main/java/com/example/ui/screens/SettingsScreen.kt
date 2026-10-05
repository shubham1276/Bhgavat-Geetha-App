package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FontSizePreference
import com.example.model.ThemeMode
import com.example.ui.GitaViewModel
import com.example.ui.components.GitaTopAppBar
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun SettingsScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val settings by viewModel.settings.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen")
    ) {
        GitaTopAppBar(
            title = "Settings & Preferences",
            subtitle = "Customize your spiritual reading experience"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Typography & Font Size
            item {
                SettingsSectionCard(
                    title = "Text Size & Typography",
                    icon = Icons.Default.FormatSize
                ) {
                    Text(
                        text = "Adjust text size for reading Sanskrit and translations comfortably:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FontSizePreference.values().forEach { pref ->
                            val isSelected = settings.fontSize == pref
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.updateFontSize(pref) }
                                    .testTag("font_size_${pref.name}"),
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, GoldAccent) else null
                            ) {
                                Text(
                                    text = pref.title,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Live preview of the font size
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Live Preview:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन ।",
                                fontSize = (18 * settings.fontSize.scale).sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "\"You have a right to your duty, but not to its fruits.\"",
                                fontSize = (14 * settings.fontSize.scale).sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // 2. Theme Mode (Dark/Light/System)
            item {
                SettingsSectionCard(
                    title = "App Appearance & Theme",
                    icon = Icons.Default.DarkMode
                ) {
                    Text(
                        text = "Choose your preferred sacred visual atmosphere:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ThemeMode.values().forEach { mode ->
                        val isSelected = settings.themeMode == mode
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.updateThemeMode(mode) }
                                .padding(vertical = 8.dp)
                                .testTag("theme_mode_${mode.name}"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(20.dp)
                            ) {}
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = mode.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // 3. Language & Script Visibility
            item {
                SettingsSectionCard(
                    title = "Script & Language Visibility",
                    icon = Icons.Default.Language
                ) {
                    ToggleSettingItem(
                        title = "Sanskrit Shloka (Devanagari)",
                        subtitle = "Display original Sanskrit verses in Devanagari script",
                        checked = settings.showSanskrit,
                        onCheckedChange = { viewModel.toggleSetting("sanskrit") }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    ToggleSettingItem(
                        title = "Romanized Transliteration (IAST)",
                        subtitle = "Display pronunciation guide in English letters",
                        checked = settings.showTransliteration,
                        onCheckedChange = { viewModel.toggleSetting("transliteration") }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    ToggleSettingItem(
                        title = "English Translation",
                        subtitle = "Clear, authentic philosophical translation",
                        checked = settings.showEnglish,
                        onCheckedChange = { viewModel.toggleSetting("english") }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    ToggleSettingItem(
                        title = "Hindi Translation (हिन्दी)",
                        subtitle = "Devanagari Hindi interpretation",
                        checked = settings.showHindi,
                        onCheckedChange = { viewModel.toggleSetting("hindi") }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    ToggleSettingItem(
                        title = "Commentary & Life Guidance",
                        subtitle = "Detailed philosophical meaning and modern application",
                        checked = settings.showCommentary,
                        onCheckedChange = { viewModel.toggleSetting("commentary") }
                    )
                }
            }

            // 4. Audio & Recitation
            item {
                SettingsSectionCard(
                    title = "Audio & Recitation Engine",
                    icon = Icons.Default.RecordVoiceOver
                ) {
                    Text(
                        text = "Recitation Speed (${String.format("%.2f", settings.ttsSpeed)}x):",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Slider(
                        value = settings.ttsSpeed,
                        onValueChange = { viewModel.updateTtsSpeed(it) },
                        valueRange = 0.6f..1.4f,
                        steps = 7,
                        colors = SliderDefaults.colors(
                            thumbColor = SaffronPrimary,
                            activeTrackColor = SaffronPrimary
                        ),
                        modifier = Modifier.testTag("tts_speed_slider")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Slow (0.6x)", style = MaterialTheme.typography.labelSmall)
                        Text("Normal (1.0x)", style = MaterialTheme.typography.labelSmall)
                        Text("Fast (1.4x)", style = MaterialTheme.typography.labelSmall)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(6.dp))

                    ToggleSettingItem(
                        title = "Harmonic Meditative Drone (Om / 136.1 Hz)",
                        subtitle = "Synthesize serene background tanpura vibration for meditation",
                        checked = settings.ambientChantEnabled,
                        onCheckedChange = { viewModel.toggleSetting("ambient") }
                    )
                }
            }

            // 5. Daily Quote & Notifications
            item {
                SettingsSectionCard(
                    title = "Daily Wisdom Notification",
                    icon = Icons.Default.Notifications
                ) {
                    ToggleSettingItem(
                        title = "Daily Morning Shloka Reminder",
                        subtitle = "Receive an inspiring verse each morning to elevate your day",
                        checked = settings.dailyNotificationEnabled,
                        onCheckedChange = { viewModel.toggleSetting("notification") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { viewModel.sendDailyVerseNotification(context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("test_notification_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Test Notification Now")
                    }
                }
            }

            // 6. About & Play Store Info
            item {
                SettingsSectionCard(
                    title = "About Bhagavad Geetha Wisdom",
                    icon = Icons.Default.Info
                ) {
                    Text(
                        text = "Learn about the history, philosophical significance, Play Store compliance, and credits.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { viewModel.openAbout() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("open_about_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SaffronPrimary
                        )
                    ) {
                        Text("View About, Credits & Privacy Policy")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SaffronPrimary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
fun ToggleSettingItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = SaffronPrimary
            )
        )
    }
}
