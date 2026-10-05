package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.GitaViewModel
import com.example.ui.components.SacredOmIcon
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutGitaScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About & Play Store Submission") },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("about_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = SaffronPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .testTag("about_screen_list"),
            contentPadding = PaddingValues(top = 12.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sacred Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SacredOmIcon(size = 48, color = SaffronPrimary)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Srimad Bhagavad Gita",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary
                            )
                        )
                        Text(
                            text = "The Universal Scripture of Humanity",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Philosophical Background
            item {
                AboutCard(
                    title = "Historical & Spiritual Background",
                    icon = Icons.Default.Info
                ) {
                    Text(
                        text = "The Bhagavad Gita ('The Song of God') forms an essential 700-verse portion of the Bhishma Parva in the ancient Indian epic Mahabharata. Spoken by Bhagavan Sri Krishna to his close friend and disciple Arjuna just prior to the colossal Kurukshetra war, the dialogue addresses the perennial human crisis: duty versus personal attachment, despair, righteousness, and the destiny of the immortal soul.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "The teaching synthesizes the three primary yogic paths for human fulfillment: Karma Yoga (selfless action without anxiety for fruits), Jnana Yoga (discriminative wisdom of the true Self), and Bhakti Yoga (heartfelt devotion and loving surrender to the Supreme Divine).",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // Tributes by Global Thinkers
            item {
                AboutCard(
                    title = "Tributes from World Luminaries",
                    icon = Icons.Default.FormatQuote
                ) {
                    QuoteItem(
                        quote = "When doubts haunt me, when disappointments stare me in the face, and I see not one ray of hope on the horizon, I turn to Bhagavad Gita and find a verse to comfort me; and I immediately begin to smile in the midst of overwhelming sorrow.",
                        author = "Mahatma Gandhi"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    QuoteItem(
                        quote = "When I read the Bhagavad-Gita and reflect about how God created this universe, everything else seems so superfluous.",
                        author = "Albert Einstein"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    QuoteItem(
                        quote = "If one reads this one Shloka — 'Klaibyam ma sma gamah Partha' (Do not yield to unmanliness, O son of Pritha) — one gets all the merits of reading the entire Gita; for in this one Shloka lies embedded the whole Message of the Gita.",
                        author = "Swami Vivekananda"
                    )
                }
            }

            // Play Store Submission Metadata (ATS-Friendly)
            item {
                AboutCard(
                    title = "Google Play Store Metadata (ATS-Optimized)",
                    icon = Icons.Default.Storefront
                ) {
                    MetaField("App Title (≤ 30 chars)", "Geetha Wisdom")
                    MetaField("Full Name", "Bhagavad Geetha Wisdom: 18 Chapters")
                    MetaField("Short Description (≤ 80 chars)", "All 18 chapters of Bhagavad Gita with audio, Sanskrit shlokas, and daily quote.")
                    MetaField("Category", "Books & Reference / Education / Lifestyle")
                    MetaField("Content Rating", "Everyone (Clean, spiritual, ad-free)")

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Key Features for Play Store Listing:",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val bulletPoints = listOf(
                        "Complete 18 Chapters & 700 Shlokas index with detailed summaries",
                        "Authentic Sanskrit verses in Devanagari with IAST English transliteration",
                        "Word-by-word meanings, English translation & Hindi translation",
                        "In-depth spiritual commentary with modern life practical applications",
                        "Recitation audio playback with Sanskrit/English voice, speed, & repeat chanting loops (Japa)",
                        "Harmonic 136.1 Hz Om / Tanpura background drone for blissful meditation",
                        "Daily inspirational verse notification to elevate your morning",
                        "Fast keyword search across Sanskrit, English, and spiritual topics",
                        "Personal bookmarks with reflection notes and customizable quote card sharing",
                        "100% Offline capability with lightweight storage and dark/light spiritual theme"
                    )

                    bulletPoints.forEach { point ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text("✓ ", color = SaffronPrimary, fontWeight = FontWeight.Bold)
                            Text(
                                point,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            // Privacy Policy & Zero-Data Collection
            item {
                AboutCard(
                    title = "Privacy Policy & Data Security",
                    icon = Icons.Default.Policy
                ) {
                    Text(
                        text = "Effective Date: October 2026\n\n1. Zero Personal Data Collection:\nBhagavad Geetha Wisdom does NOT collect, harvest, store, or sell any personal data, accounts, phone numbers, or analytics. All your bookmarks, reflection notes, and preferences remain strictly on your personal device.\n\n2. Device Permissions:\n• Notifications: Used solely on-device to deliver your scheduled daily verse if enabled.\n• Vibration: Gentle tactile feedback for meditation chanting and audio controls.\n\n3. Offline First:\nAll 18 chapters, verses, and audio tools operate completely offline without requiring internet connectivity.\n\n4. Contact & Support:\nCreated with reverence for spiritual seekers worldwide.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // Credits
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "॥ श्रीकृष्णार्पणमस्तु ॥",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary,
                                letterSpacing = 2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Dedicated as an offering to seekers of eternal truth.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AboutCard(
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
                    modifier = Modifier.size(20.dp)
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
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun QuoteItem(quote: String, author: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "\"$quote\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontStyle = FontStyle.Italic,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "— $author",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary
                ),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun MetaField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = SaffronPrimary
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
