package com.example.data

import com.example.model.Chapter
import com.example.model.Verse

object GitaData {

    val chapters: List<Chapter> = listOf(
        Chapter(
            id = 1,
            nameSanskrit = "अर्जुनविषादयोग",
            nameTransliteration = "Arjuna Viṣāda Yoga",
            nameEnglish = "Arjuna's Dilemma & Grief",
            versesCount = 47,
            yogaType = "Preliminary",
            summary = "On the sacred battlefield of Kurukshetra, the righteous warrior Arjuna surveys both armies. Overwhelmed by grief, compassion, and moral confusion over having to fight his respected teachers and kinsmen, he casts aside his bow and surrenders to despair.",
            keyTeachings = listOf(
                "Facing moral crisis and inner conflict with honesty",
                "The peril of attachment clouding righteous duty (Dharma)",
                "The need for an enlightened spiritual guide when human intellect falters"
            )
        ),
        Chapter(
            id = 2,
            nameSanskrit = "साङ्ख्ययोग",
            nameTransliteration = "Sāṅkhya Yoga",
            nameEnglish = "Transcendental Knowledge",
            versesCount = 72,
            yogaType = "Jnana Yoga",
            summary = "Sri Krishna admonishes Arjuna for his despondency and begins his divine instruction. He reveals the eternal, indestructible nature of the soul (Atman), the transient nature of the physical body, the call to selfless action (Karma Yoga), and the supreme equanimity of the enlightened sage (Sthitaprajna).",
            keyTeachings = listOf(
                "The soul never dies, nor was it ever born (Nainam Chindanti Shastrani)",
                "Perform your duty without attachment to outcomes (Karmanye Vadhikaraste)",
                "Equanimity in triumph and defeat, pleasure and pain (Samatvam Yoga Uchyate)",
                "The attributes and peace of a person of steady intellect (Sthitaprajna)"
            )
        ),
        Chapter(
            id = 3,
            nameSanskrit = "कर्मयोग",
            nameTransliteration = "Karma Yoga",
            nameEnglish = "The Path of Selfless Action",
            versesCount = 43,
            yogaType = "Karma Yoga",
            summary = "Arjuna asks whether renunciation of action or active engagement is superior. Krishna explains that action is unavoidable in nature. The secret of freedom is Karma Yoga: dedicating all actions as a sacred offering (Yajna) for the welfare of the world (Lokasamgraha) without selfish desire.",
            keyTeachings = listOf(
                "Inactivity is impossible for embodied beings",
                "Action performed as a selfless sacrifice creates no karmic bondage",
                "Leaders must set noble examples for society to follow",
                "Lust and anger, born of Rajas, are the soul's greatest enemies"
            )
        ),
        Chapter(
            id = 4,
            nameSanskrit = "ज्ञानकर्मसंन्यासयोग",
            nameTransliteration = "Jñāna Karma Sannyāsa Yoga",
            nameEnglish = "Renunciation of Action through Knowledge",
            versesCount = 42,
            yogaType = "Jnana & Karma Yoga",
            summary = "Krishna reveals the ancient lineage of this supreme wisdom and proclaims his divine incarnation whenever righteousness (Dharma) declines. He explains how spiritual wisdom burns all karmic reactions to ashes and that even action becomes freedom when done in pure awareness.",
            keyTeachings = listOf(
                "Divine descent to protect the good and restore righteousness (Yada Yada Hi Dharmasya)",
                "Seeing inaction in action and action in inaction",
                "Spiritual wisdom is the greatest purifier in this world (Nahi Jnanena Sadrisham)",
                "Faith (Shraddha) brings knowledge, doubt destroys inner peace"
            )
        ),
        Chapter(
            id = 5,
            nameSanskrit = "कर्मसंन्यासयोग",
            nameTransliteration = "Karma Sannyāsa Yoga",
            nameEnglish = "The Yoga of True Renunciation",
            versesCount = 29,
            yogaType = "Karma & Jnana Yoga",
            summary = "Krishna resolves the apparent tension between external ascetic renunciation and selfless action in the world. He affirms that Karma Yoga is easier, safer, and equally effective. The wise person remains in the world like a lotus leaf untouched by water, anchored in Brahman.",
            keyTeachings = listOf(
                "True renunciation is internal freedom from likes and dislikes",
                "Remaining unaffected like a lotus leaf resting on water",
                "Seeing the divine essence equally in all living beings",
                "Mastery over senses and desires leads to perpetual peace"
            )
        ),
        Chapter(
            id = 6,
            nameSanskrit = "ध्यानयोग",
            nameTransliteration = "Dhyāna Yoga",
            nameEnglish = "The Path of Meditation",
            versesCount = 47,
            yogaType = "Raja Yoga",
            summary = "The blueprint for mastering the mind through regular meditation. Krishna outlines the proper environment, posture, breathing, and moderation in food and sleep. When Arjuna worries that the mind is as restless as the wind, Krishna assures him it can be conquered by steady practice (Abhyasa) and dispassion (Vairagya).",
            keyTeachings = listOf(
                "The mind can be one's greatest friend or worst enemy",
                "Moderation in eating, sleeping, work, and recreation (Yukta-ahara)",
                "Conquering the restless mind through Abhyasa and Vairagya",
                "A seeker of righteousness never meets with destruction"
            )
        ),
        Chapter(
            id = 7,
            nameSanskrit = "ज्ञानविज्ञानयोग",
            nameTransliteration = "Jñāna Vijñāna Yoga",
            nameEnglish = "Knowledge and Realization",
            versesCount = 30,
            yogaType = "Bhakti & Jnana Yoga",
            summary = "Krishna reveals the nature of the Supreme Reality: his lower material nature (matter, elements, mind, intellect) and his higher spiritual nature (the life-principle animating all). He describes the veil of Maya and the four types of virtuous people who seek God.",
            keyTeachings = listOf(
                "God is the fragrance in the earth, the light in the sun, and life in all beings",
                "Divine Maya is hard to cross, but those who surrender to God cross easily",
                "Four kinds of seekers: the distressed, the seeker of knowledge, the seeker of wealth, and the wise",
                "The wise seeker who knows God in truth is dearest of all"
            )
        ),
        Chapter(
            id = 8,
            nameSanskrit = "अक्षरब्रह्मयोग",
            nameTransliteration = "Akṣara Brahma Yoga",
            nameEnglish = "The Imperishable Brahman",
            versesCount = 28,
            yogaType = "Jnana & Bhakti Yoga",
            summary = "Arjuna inquires about Brahman, Adhyatma, Karma, Adhibhuta, and Adhidaiva. Krishna clarifies these concepts and explains the vital science of remembrance at the time of death: whatever state of being one remembers when leaving the body, to that state one goes. Therefore, remember the Divine at all times while performing duties.",
            keyTeachings = listOf(
                "Constant remembrance of the Divine shapes the soul's destiny (Anta-kale cha mam eva)",
                "Fight your battles while keeping your mind fixed on the Supreme (Tasmāt sarveṣu kāleṣu mām anusmara yudhya ca)",
                "The cosmic cycles of creation and dissolution of Brahma",
                "Reaching the eternal abode beyond birth and rebirth"
            )
        ),
        Chapter(
            id = 9,
            nameSanskrit = "राजविद्याराजगुह्ययोग",
            nameTransliteration = "Rāja Vidyā Rāja Guhya Yoga",
            nameEnglish = "The Royal Knowledge & Supreme Secret",
            versesCount = 34,
            yogaType = "Bhakti Yoga",
            summary = "Krishna imparts the sovereign secret that leads to direct spiritual realization and liberation. He explains how the entire cosmos is pervaded by Him, yet unattached. He promises that to those who worship Him with single-minded devotion, He personally provides what they lack and preserves what they have.",
            keyTeachings = listOf(
                "The supreme, purest sovereign knowledge accessible through direct experience",
                "God protects and provides for those devoted with single-minded love (Yoga-kshemam vahamyaham)",
                "Even a leaf, a flower, fruit, or water offered with love is accepted by the Divine (Patram Pushpam Phalam Toyam)",
                "No one is excluded: all beings can attain the highest liberation"
            )
        ),
        Chapter(
            id = 10,
            nameSanskrit = "विभूतियोग",
            nameTransliteration = "Vibhūti Yoga",
            nameEnglish = "Divine Splendors & Opulence",
            versesCount = 42,
            yogaType = "Bhakti Yoga",
            summary = "Krishna reveals his divine manifestations pervading the universe. He is the beginning, middle, and end of all beings; the Sun among luminaries; the Himalayas among mountains; the Om among sounds; and Rama among warriors. Whatever is glorious, beautiful, or mighty springs from a single spark of His splendor.",
            keyTeachings = listOf(
                "I am the source of all; from Me everything evolves (Aham Sarvasya Prabhavo)",
                "Devotees delight in conversing about and sharing the Divine",
                "Seeing God in the pinnacle of every excellence in creation",
                "The entire cosmos is sustained by a mere fraction of divine majesty"
            )
        ),
        Chapter(
            id = 11,
            nameSanskrit = "विश्वरूपदर्शनयोग",
            nameTransliteration = "Viśvarūpa Darśana Yoga",
            nameEnglish = "The Vision of the Universal Cosmic Form",
            versesCount = 55,
            yogaType = "Divine Vision",
            summary = "At Arjuna's humble request, Sri Krishna grants him divine spiritual eyes (Divya Chakshu) to behold His staggering Universal Cosmic Form (Vishwarupa). Arjuna witnesses countless celestial suns, universes, gods, and warriors entering the blazing jaws of Time (Kala). Stunned with awe and trembling reverence, Arjuna bows and seeks forgiveness.",
            keyTeachings = listOf(
                "Spiritual vision transcends ordinary sensory perception",
                "Time is the supreme transformer and consumer of all worldly forms (Kalo'smi)",
                "Be merely an instrument in the divine cosmic plan (Nimitta-matram bhava Savyasachin)",
                "Unalloyed devotion is the true gate to comprehending God's gentle, grace-filled form"
            )
        ),
        Chapter(
            id = 12,
            nameSanskrit = "भक्तियोग",
            nameTransliteration = "Bhakti Yoga",
            nameEnglish = "The Yoga of Devotion",
            versesCount = 20,
            yogaType = "Bhakti Yoga",
            summary = "Arjuna asks whether devotion to the personal form of God or contemplation of the unmanifested Absolute is higher. Krishna explains that while both lead to liberation, devotion to the personal Divine is more natural and joyous for embodied beings. He describes the sublime qualities of the devotee who is deeply cherished by Him.",
            keyTeachings = listOf(
                "Devotion with full heart and steady mind is the smoothest path",
                "Hierarchical options for seekers: total absorption, regular practice, selfless work, or renunciation of results",
                "The beloved devotee: free from malice, compassionate, forgiving, content, equal in honor and dishonor",
                "True love of God manifests as universal goodwill to all beings"
            )
        ),
        Chapter(
            id = 13,
            nameSanskrit = "क्षेत्रक्षेत्रज्ञविभागयोग",
            nameTransliteration = "Kṣetra Kṣetrajña Vibhāga Yoga",
            nameEnglish = "The Field and the Knower of the Field",
            versesCount = 35,
            yogaType = "Jnana Yoga",
            summary = "Krishna analyzes reality into the 'Field' (Kshetra - the physical body, senses, mind, ego, emotions) and the 'Knower of the Field' (Kshetrajna - the conscious soul and the Supreme Soul observing within). True wisdom is discerning the distinction between mortal matter and immortal consciousness.",
            keyTeachings = listOf(
                "The body is the field where karmic seeds sprout and bear fruit",
                "Consciousness is the witness, untouched by physical changes",
                "True virtues of wisdom: humility, non-violence, purity, forbearance, self-restraint",
                "The indwelling Supreme Lord resides equally in every living creature"
            )
        ),
        Chapter(
            id = 14,
            nameSanskrit = "गुणत्रयविभागयोग",
            nameTransliteration = "Guṇatraya Vibhāga Yoga",
            nameEnglish = "The Three Modes of Material Nature",
            versesCount = 27,
            yogaType = "Jnana Yoga",
            summary = "Krishna explains the three primal qualities (Gunas) of nature that bind the soul: Sattva (purity, clarity, harmony), Rajas (passion, greed, intense activity), and Tamas (darkness, inertia, delusion). He explains how to transcend all three modes (Gunatita) to attain immortal bliss.",
            keyTeachings = listOf(
                "Sattva brings wisdom and happiness; Rajas brings longing and agitation; Tamas causes sloth and ignorance",
                "Every person's character is a blend of these three dynamic forces",
                "Cultivating Sattva to rise above Tamas and Rajas",
                "The Gunatita (one who transcends the gunas) remains unshaken by life's dualities"
            )
        ),
        Chapter(
            id = 15,
            nameSanskrit = "पुरुषोत्तमयोग",
            nameTransliteration = "Puruṣottama Yoga",
            nameEnglish = "The Supreme Divine Person",
            versesCount = 20,
            yogaType = "Jnana & Bhakti Yoga",
            summary = "The material world is symbolized as the upside-down Ashvattha tree with roots in heaven and branches in earthly desires. Severing this tree with the sharp axe of non-attachment, the seeker enters the luminous realm of Purushottama—the Supreme Being who transcends both the perishable world and imperishable souls.",
            keyTeachings = listOf(
                "Severing entanglement with the axe of dispassion (Asanga-shastrena)",
                "The spiritual world is not illuminated by sun, moon, or fire, but by divine effulgence",
                "A living soul is an eternal fragment of the Divine (Mamaivamsho jiva-loke)",
                "God as the digestive fire (Vaishvanara) and memory/knowledge in all hearts"
            )
        ),
        Chapter(
            id = 16,
            nameSanskrit = "दैवासुरसम्पद्विभागयोग",
            nameTransliteration = "Daivāsura Sampad Vibhāga Yoga",
            nameEnglish = "The Divine and Demonic Natures",
            versesCount = 24,
            yogaType = "Moral Wisdom",
            summary = "Krishna contrasts divine virtues that lead to liberation with negative traits that lead to degradation and bondage. The three gateways to self-ruin are lust (Kama), anger (Krodha), and greed (Lobha). He emphasizes adherence to righteous scriptural wisdom for living a purposeful life.",
            keyTeachings = listOf(
                "Divine qualities: fearlessness, purity of heart, charity, truthfulness, compassion, gentleness",
                "Destructive qualities: arrogance, pride, wrath, harshness, and ignorance",
                "The three gates to self-destruction: Lust, Anger, and Greed",
                "Living guided by spiritual principles brings harmony and fulfillment"
            )
        ),
        Chapter(
            id = 17,
            nameSanskrit = "श्रद्धात्रयविभागयोग",
            nameTransliteration = "Śraddhātraya Vibhāga Yoga",
            nameEnglish = "The Threefold Division of Faith",
            versesCount = 28,
            yogaType = "Spiritual Practice",
            summary = "Arjuna inquires about the nature of faith. Krishna explains that everyone possesses faith according to their dominant Guna (Sattvic, Rajasic, or Tamasic). He details how food, worship, austerity of body, speech, and mind, and charitable giving are influenced by these three modes, culminating in the sacred mantra Om Tat Sat.",
            keyTeachings = listOf(
                "A person is made by their faith; as their faith is, so indeed they are (Shraddhamayo'yam purushah)",
                "Sattvic food promotes longevity, vitality, strength, and joy",
                "Austerity of speech: words that cause no agitation, are truthful, pleasant, and beneficial",
                "The sacred syllables OM TAT SAT sanctify all worthy undertakings"
            )
        ),
        Chapter(
            id = 18,
            nameSanskrit = "मोक्षसंन्यासयोग",
            nameTransliteration = "Mokṣa Sannyāsa Yoga",
            nameEnglish = "Liberation through Renunciation",
            versesCount = 78,
            yogaType = "Synthesis & Liberation",
            summary = "The grand synthesis and crowning culmination of the Bhagavad Gita. Krishna clarifies the true meaning of Sannyasa (giving up selfish actions) and Tyaga (renouncing the fruit of action). He reviews the five factors of action, the nature of duty (Svadharma), and delivers His ultimate promise of supreme refuge and unconditional divine love.",
            keyTeachings = listOf(
                "It is better to perform one's own natural duty imperfectly than another's duty flawlessly (Shreyan svadharmo vigunah)",
                "Fix your mind on Me, be devoted to Me, worship Me; you shall attain Me (Man-mana bhava mad-bhakto)",
                "Abandon all dogmatic attachments and surrender solely unto Me; I shall liberate you from all fears (Sarva-dharman parityajya)",
                "Wherever there is Sri Krishna and the noble wielder of the bow Arjuna, there will surely be fortune, victory, prosperity, and righteous order (Yatra yogeshwarah krishno)"
            )
        )
    )

    val keyVerses: List<Verse> = listOf(
        // Chapter 1
        Verse(
            id = "1.1",
            chapterId = 1,
            verseNumber = 1,
            textSanskrit = "धृतराष्ट्र उवाच |\nधर्मक्षेत्रे कुरुक्षेत्रे समवेता युयुत्सवः |\nमामकाः पाण्डवाश्चैव किमकुर्वत सञ्जय ॥",
            textTransliteration = "dhṛtarāṣṭra uvāca\ndharmakṣetre kurukṣetre samavetā yuyutsavaḥ\nmāmakāḥ pāṇḍavāścaiva kimakurvata sañjaya",
            textEnglish = "Dhritarashtra said: O Sanjaya, assembled on the sacred field of Kurukshetra, eager to fight, what did my sons and the sons of Pandu do?",
            textHindi = "धृतराष्ट्र ने कहा: हे संजय! धर्मभूमि कुरुक्षेत्र में युद्ध की इच्छा से एकत्र हुए मेरे और पाण्डु के पुत्रों ने क्या किया?",
            wordMeanings = "dharmakṣetre = in the holy field; kurukṣetre = at Kurukshetra; samavetāḥ = assembled; yuyutsavaḥ = desiring to fight; māmakāḥ = my party; pāṇḍavāḥ = the sons of Pandu; ca = and; eva = certainly; kim = what; akurvata = did they do; sañjaya = O Sanjaya.",
            commentary = "The Bhagavad Gita opens with the word 'Dharma-kshetra' (field of righteousness). Kurukshetra symbolizes not just an ancient battleground, but the human heart and mind, where the continuous struggle between noble virtues (the Pandavas) and base egoistic desires (the Kauravas) unfolds daily.",
            practicalApplication = "Recognize that your inner doubts, moral dilemmas, and conflicting desires are a sacred battlefield. The journey to wisdom starts by confronting our illusions honestly.",
            tags = listOf("Dharma", "Introduction", "Inner Conflict", "Kurukshetra")
        ),
        Verse(
            id = "1.47",
            chapterId = 1,
            verseNumber = 47,
            textSanskrit = "सञ्जय उवाच |\nएवमुक्त्वार्जुनः सङ्ख्ये रथोपस्थ उपाविशत् |\nविसृज्य सशरं चापं शोकसंविग्नमानसः ॥",
            textTransliteration = "sañjaya uvāca\nevam uktvārjunaḥ saṅkhye rathopastha upāviśat\nvisṛjya sa-śaraṁ cāpaṁ śoka-saṁvigna-mānasaḥ",
            textEnglish = "Sanjaya said: Having spoken thus on the battlefield, Arjuna cast aside his bow and arrows and sat down upon the chariot, his mind overcome with profound grief.",
            textHindi = "संजय ने कहा: युद्धभूमि में इस प्रकार कहकर, शोक से व्याकुल मन वाले अर्जुन ने अपने बाण सहित धनुष को त्याग दिया और रथ के पिछले भाग में बैठ गया।",
            wordMeanings = "evam = thus; uktvā = having spoken; arjunaḥ = Arjuna; saṅkhye = on the battlefield; rathopasthe = in the chariot seat; upāviśat = sat down; visṛjya = casting aside; sa-śaram = with arrows; cāpam = bow; śoka-saṁvigna-mānasaḥ = with a mind distressed by sorrow.",
            commentary = "Arjuna's collapse is the necessary precursor to illumination. Only when a person acknowledges the inadequacy of worldly logic to solve life's deepest crises does genuine spiritual surrender and openness to higher wisdom take birth.",
            practicalApplication = "When overwhelming sorrow or burnout strikes, do not suppress it. Like Arjuna, pause and turn to timeless spiritual guidance.",
            tags = listOf("Surrender", "Grief", "Humility", "Crisis")
        ),

        // Chapter 2
        Verse(
            id = "2.14",
            chapterId = 2,
            verseNumber = 14,
            textSanskrit = "मात्रास्पर्शास्तु कौन्तेय शीतोष्णसुखदुःखदाः |\nआगमापायिनोऽनित्यास्तांस्तितिक्षस्व भारत ॥",
            textTransliteration = "mātrā-sparśās tu kaunteya śītoṣṇa-sukha-duḥkha-dāḥ\nāgamāpāyino 'nityās tāṁs titikṣasva bhārata",
            textEnglish = "O son of Kunti, the contact of the senses with their objects gives rise to cold and heat, pleasure and pain. These experiences are fleeting and transient; learn to endure them patiently, O Bharata.",
            textHindi = "हे कुन्तीपुत्र! सर्दी-गर्मी और सुख-दुःख देने वाले इन्द्रिय और विषयों के संयोग तो नाशवान् और अनित्य हैं, इसलिए हे भारत! तू उनको सहन कर।",
            wordMeanings = "mātrā-sparśāḥ = contact of the senses; tu = indeed; kaunteya = O son of Kunti; śīta-uṣṇa = cold and heat; sukha-duḥkha-dāḥ = givers of pleasure and pain; āgama-apāyinaḥ = appearing and disappearing; anityāḥ = non-permanent; tān = them; titikṣasva = endure; bhārata = O descendant of Bharata.",
            commentary = "Sri Krishna introduces 'Titiksha'—the art of dignified, conscious endurance. Sensations of pleasure and pain come and go like shifting seasons. By observing them with calm detachment rather than panic or euphoria, one discovers the unchanging center of peace within.",
            practicalApplication = "When facing uncomfortable circumstances or sudden misfortune, remind yourself: 'This too shall pass.' Cultivate inner resilience against sensory fluctuations.",
            tags = listOf("Endurance", "Titiksha", "Equanimity", "Mindfulness")
        ),
        Verse(
            id = "2.20",
            chapterId = 2,
            verseNumber = 20,
            textSanskrit = "न जायते म्रियते वा कदाचि-\nन्नायं भूत्वा भविता वा न भूयः |\nअजो नित्यः शाश्वतोऽयं पुराणो\nन हन्यते हन्यमाने शरीरे ॥",
            textTransliteration = "na jāyate mriyate vā kadācin\nnāyaṁ bhūtvā bhavitā vā na bhūyaḥ\najo nityaḥ śāśvato 'yaṁ purāṇo\nna hanyate hanyamāne śarīre",
            textEnglish = "The soul is never born, nor does it ever die; nor having come into being, does it cease to be. Unborn, eternal, ever-existing, and primeval, it is not slain when the body is slain.",
            textHindi = "आत्मा किसी काल में भी न जन्मता है और न मरता है; और न ही यह होकर फिर होने वाला है। यह अजन्मा, नित्य, सनातन और पुरातन है; शरीर के मारे जाने पर भी यह नहीं मारा जाता।",
            wordMeanings = "na = never; jāyate = is born; mriyate = dies; vā = or; kadācit = at any time; na = not; ayam = this; bhūtvā = having been; bhavitā = will be; vā = or; na = not; bhūyaḥ = again; ajaḥ = unborn; nityaḥ = eternal; śāśvataḥ = timeless; purāṇaḥ = ancient; na = not; hanyate = is killed; hanyamāne = being killed; śarīre = in the body.",
            commentary = "One of the most glorious verses in world literature. Krishna unveils the immortal truth of our core identity: you are not a mortal body with a temporary soul, but an eternal spiritual being dwelling temporarily in a physical vesture.",
            practicalApplication = "Release existential dread and fear of physical death. Your true consciousness is indestructible, boundless, and free.",
            tags = listOf("Immortality", "Soul", "Atman", "Freedom from Fear")
        ),
        Verse(
            id = "2.47",
            chapterId = 2,
            verseNumber = 47,
            textSanskrit = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन |\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि ॥",
            textTransliteration = "karmaṇy-evādhikāras te mā phaleṣu kadācana\nmā karma-phala-hetur bhūr mā te saṅgo 'stv akarmaṇi",
            textEnglish = "You have a right to perform your prescribed duty, but never to the fruits of action. Never consider yourself the cause of results, nor let your attachment be to inaction.",
            textHindi = "तुम्हारा अधिकार केवल कर्म करने में ही है, उसके फलों में कभी नहीं। इसलिए तुम कर्मों के फल के हेतु मत बनो और न ही तुम्हारी आसक्ति अकर्म में हो।",
            wordMeanings = "karmaṇi = in duty/action; eva = only; adhikāraḥ = right; te = your; mā = never; phaleṣu = in the fruits; kadācana = at any time; mā = let not; karma-phala = result of action; hetuḥ = motive; bhūḥ = be; mā = never; te = your; saṅgaḥ = attachment; astu = be; akarmaṇi = in inaction.",
            commentary = "The universal anthem of Karma Yoga. Four cardinal principles are enshrined here: (1) engage enthusiastically in action, (2) release anxious obsession over the fruits, (3) do not harbor egotistical pride as the sole author of outcomes, and (4) never descend into lazy apathy or inaction.",
            practicalApplication = "Focus 100% of your energy on the excellence and integrity of the task in front of you. Let go of crippling outcome anxiety and stress dissolves instantly.",
            tags = listOf("Karma Yoga", "Duty", "Excellence", "Mental Peace", "Focus")
        ),
        Verse(
            id = "2.48",
            chapterId = 2,
            verseNumber = 48,
            textSanskrit = "योगस्थः कुरु कर्माणि सङ्गं त्यक्त्वा धनञ्जय |\nसिद्ध्यसिद्ध्योः समो भूत्वा समत्वं योग उच्यते ॥",
            textTransliteration = "yoga-sthaḥ kuru karmāṇi saṅgaṁ tyaktvā dhanañjaya\nsiddhy-asiddhyoḥ samo bhūtvā samatvaṁ yoga ucyate",
            textEnglish = "Perform your actions established in Yoga, casting off attachment, O Dhananjaya, and remaining even-minded in success and failure. Equanimity of mind is indeed called Yoga.",
            textHindi = "हे धनञ्जय! तू आसक्ति को त्यागकर तथा सिद्धि और असिद्धि में समान बुद्धि वाला होकर योग में स्थित हुआ कर्मों को कर; समत्व ही योग कहलाता है।",
            wordMeanings = "yoga-sthaḥ = steadfast in Yoga; kuru = perform; karmāṇi = actions; saṅgam = attachment; tyaktvā = abandoning; dhanañjaya = O Dhananjaya; siddhi-asiddhyoḥ = in success and failure; samaḥ = poised/equal; bhūtvā = becoming; samatvam = equanimity; yogaḥ = Yoga; ucyate = is called.",
            commentary = "Yoga is not merely physical contortion; Krishna defines it as 'Samatvam'—unwavering mental poise in the face of triumph and adversity. When internal calm is undisturbed by external winds of praise or criticism, one has attained true Yoga.",
            practicalApplication = "Treat triumph and setback as twin imposters. Maintain emotional equilibrium in victory and resilience in temporary defeat.",
            tags = listOf("Yoga", "Equanimity", "Inner Peace", "Mindset")
        ),
        Verse(
            id = "2.71",
            chapterId = 2,
            verseNumber = 71,
            textSanskrit = "विहाय कामान्यः सर्वान्पुमांश्चरति निःस्पृहः |\nनिर्ममो निरहङ्कारः स शान्तिमधिगच्छति ॥",
            textTransliteration = "vihāya kāmān yaḥ sarvān pumāṁś carati niḥspṛhaḥ\nnirmamo nirahaṅkāraḥ sa śāntim adhigacchati",
            textEnglish = "That person attains peace who, abandoning all desires, lives without longing, free from the sense of 'mine' and free from egoism.",
            textHindi = "जो मनुष्य सम्पूर्ण कामनाओं को त्यागकर स्पृहा-रहित होकर विचरण करता है, जो 'ममत्व' और 'अहंकार' से रहित है, वही परम शान्ति को प्राप्त होता है।",
            wordMeanings = "vihāya = having cast off; kāmān = desires; yaḥ = who; sarvān = all; pumān = person; carati = moves/lives; niḥspṛhaḥ = without longing; nirmamaḥ = without ownership ('mine'); nirahaṅkāraḥ = without egoism; saḥ = he; śāntim = peace; adhigacchati = attains.",
            commentary = "Lasting peace (Shanti) is not acquired by piling up external achievements, but by subtracting ego (Ahamkara) and possessiveness (Mamakara). When the fever of selfish wanting ceases, the natural serenity of consciousness shines forth.",
            practicalApplication = "Catch yourself when feeling possessive or offended. Trace the grievance back to ego and release it to regain immediate tranquility.",
            tags = listOf("Peace", "Selflessness", "Egolessness", "Serenity")
        ),

        // Chapter 3
        Verse(
            id = "3.19",
            chapterId = 3,
            verseNumber = 19,
            textSanskrit = "तस्मादसक्तः सततं कार्यं कर्म समाचर |\nअसक्तो ह्याचरन्कर्म परमाप्नोति पूरुषः ॥",
            textTransliteration = "tasmād asaktaḥ satataṁ kāryaṁ karma samācara\nasakto hy ācaran karma param āpnoti pūruṣaḥ",
            textEnglish = "Therefore, without attachment, constantly perform your obligatory work, for by doing work without attachment, man attains the Supreme.",
            textHindi = "इसलिए तू निरन्तर आसक्ति से रहित होकर कर्तव्य कर्म को भली-भाँति कर; क्योंकि अनासक्त होकर कर्म करता हुआ मनुष्य परमात्मा को प्राप्त हो जाता है।",
            wordMeanings = "tasmāt = therefore; asaktaḥ = unattached; satatam = always; kāryam = obligatory; karma = action; samācara = perform well; asaktaḥ = unattached; hi = truly; ācaran = performing; karma = work; param = the Supreme; āpnoti = attains; pūruṣaḥ = a person.",
            commentary = "Work itself is transformed into prayer and spiritual ascent when executed selflessly. One does not need to abandon active life to realize the divine; one merely needs to purify the intention behind the work.",
            practicalApplication = "Bring full craftsmanship and dedication to your daily job, seeing it as your service to humanity and the universe.",
            tags = listOf("Karma Yoga", "Selfless Service", "Dedication", "Spiritual Growth")
        ),
        Verse(
            id = "3.35",
            chapterId = 3,
            verseNumber = 35,
            textSanskrit = "श्रेयान्स्वधर्मो विगुणः परधर्मात्स्वनुष्ठितात् |\nस्वधर्मे निधनं श्रेयः परधर्मो भयावहः ॥",
            textTransliteration = "śreyān sva-dharmo viguṇaḥ para-dharmāt sv-anuṣṭhitāt\nsva-dharme nidhanaṁ śreyaḥ para-dharmo bhayāvahaḥ",
            textEnglish = "Better is one's own duty, though devoid of merit, than the duty of another well-performed. Better is death in the discharge of one's own duty; the duty of another is fraught with danger.",
            textHindi = "दूसरों के कर्तव्य को भली-भाँति करने की अपेक्षा अपना कर्तव्य (स्वधर्म) गुणरहित होने पर भी श्रेष्ठ है; स्वधर्म में मरना भी कल्याणकारी है, पराया धर्म भय देने वाला है।",
            wordMeanings = "śreyān = better; sva-dharmaḥ = one's own natural duty; viguṇaḥ = imperfect; para-dharmāt = than another's duty; su-anuṣṭhitāt = well-executed; sva-dharme = in one's own duty; nidhanam = destruction/death; śreyaḥ = better; para-dharmaḥ = another's duty; bhaya-āvahaḥ = dangerous/fraught with fear.",
            commentary = "Svadharma represents one's innate calling aligned with natural temperament and talents. Imitating another person's path out of social envy or greed leads to psychological disharmony and spiritual ruin.",
            practicalApplication = "Embrace your unique gifts, responsibilities, and authentic life path. Do not live a counterfeit version of someone else's destiny.",
            tags = listOf("Svadharma", "Authenticity", "Duty", "Purpose")
        ),

        // Chapter 4
        Verse(
            id = "4.7",
            chapterId = 4,
            verseNumber = 7,
            textSanskrit = "यदा यदा हि धर्मस्य ग्लानिर्भवति भारत |\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम् ॥",
            textTransliteration = "yadā yadā hi dharmasya glānir bhavati bhārata\nabhyutthānam adharmasya tadātmānaṁ sṛjāmy aham",
            textEnglish = "Whenever there is a decline of righteousness, O Bharata, and an uprise of unrighteousness, then I manifest Myself on earth.",
            textHindi = "हे भारत! जब-जब धर्म की हानि और अधर्म की वृद्धि होती है, तब-तब ही मैं अपने रूप को रचता हूँ अर्थात साकार रूप में प्रकट होता हूँ।",
            wordMeanings = "yadā yadā = whenever; hi = certainly; dharmasya = of righteousness; glāniḥ = decline; bhavati = takes place; bhārata = O descendant of Bharata; abhyutthānam = rise; adharmasya = of unrighteousness; tadā = then; ātmānam = Myself; sṛjāmi = manifest; aham = I.",
            commentary = "The eternal promise of divine incarnation (Avatara). The moral universe is not abandoned to chaos; when cosmic balance is threatened, the Divine power intervenes to protect light and truth.",
            practicalApplication = "Never lose hope in the ultimate victory of goodness. Stand steadfast as an instrument of truth even when falsehood seems dominant.",
            tags = listOf("Avatara", "Dharma", "Divine Promise", "Hope")
        ),
        Verse(
            id = "4.8",
            chapterId = 4,
            verseNumber = 8,
            textSanskrit = "परित्राणाय साधूनां विनाशाय च दुष्कृताम् |\nधर्मसंस्थापनार्थाय सम्भवामि युगे युगे ॥",
            textTransliteration = "paritrāṇāya sādhūnāṁ vināśāya ca duṣkṛtām\ndharma-saṁsthāpanārthāya sambhavāmi yuge yuge",
            textEnglish = "For the protection of the good, for the destruction of the wicked, and for the establishment of righteousness, I come into being from age to age.",
            textHindi = "साधु पुरुषों के उद्धार के लिए, पापियों के विनाश के लिए और धर्म की भली-भाँति स्थापना करने के लिए मैं युग-युग में प्रकट होता हूँ।",
            wordMeanings = "paritrāṇāya = for the deliverance; sādhūnām = of the righteous; vināśāya = for the destruction; ca = and; duṣkṛtām = of evildoers; dharma-saṁsthāpana-arthāya = for establishing Dharma; sambhavāmi = I appear; yuge yuge = in age after age.",
            commentary = "Krishna outlines the threefold divine purpose: uplifting the sincere and virtuous, dismantling destructive demonic forces, and reinstating the foundational harmony of Dharma across historical epochs.",
            practicalApplication = "Align your life with the forces of goodness, kindness, and justice so that divine grace works freely through your actions.",
            tags = listOf("Divine Mission", "Protection", "Righteousness", "Justice")
        ),
        Verse(
            id = "4.38",
            chapterId = 4,
            verseNumber = 38,
            textSanskrit = "न हि ज्ञानेन सदृशं पवित्रमिह विद्यते |\nतत्स्वयं योगसंसिद्धः कालेनात्मनि विन्दति ॥",
            textTransliteration = "na hi jñānena sadṛśaṁ pavitram iha vidyate\ntat svayaṁ yoga-saṁsiddhaḥ kālenātmani vindati",
            textEnglish = "In this world, there is nothing so purifying as spiritual knowledge. One who becomes perfected in Yoga finds this knowledge naturally within the self in due course of time.",
            textHindi = "इस संसार में ज्ञान के समान पवित्र करने वाला निःसंदेह कुछ भी नहीं है। उस ज्ञान को कर्मयोग में सिद्ध हुआ मनुष्य समय पाकर स्वतः ही अपने हृदय में पा लेता है।",
            wordMeanings = "na = not; hi = indeed; jñānena = with knowledge; sadṛśam = equal; pavitram = purifying; iha = in this world; vidyate = exists; tat = that; svayam = oneself; yoga-saṁsiddhaḥ = perfected in Yoga; kālena = in time; ātmani = in the self; vindati = discovers.",
            commentary = "Spiritual wisdom (Jnana) is the supreme purifier. While water cleanses the body and discipline cleanses habits, wisdom burns away deep-seated ignorance and delusion, unveiling the self-luminous reality within.",
            practicalApplication = "Commit daily time to sacred study, contemplation, and sincere introspection. Wisdom will bloom naturally as your mind becomes calm and pure.",
            tags = listOf("Knowledge", "Jnana", "Purity", "Self-Realization")
        ),

        // Chapter 6
        Verse(
            id = "6.5",
            chapterId = 6,
            verseNumber = 5,
            textSanskrit = "उद्धरेदात्मनात्मानं नात्मानमवसादयेत् |\nआत्मैव ह्यात्मनो बन्धुरात्मैव रिपुरात्मनः ॥",
            textTransliteration = "uddhared ātmanātmānaṁ nātmānam avasādayet\nātmaiva hy ātmano bandhur ātmaiva ripur ātmanaḥ",
            textEnglish = "Let a man lift himself by his own self; let him not degrade himself. For the self alone is the friend of oneself, and the self alone is the enemy of oneself.",
            textHindi = "मनुष्य को चाहिए कि वह अपने द्वारा अपना उद्धार करे, अपने को नीचे न गिराए; क्योंकि यह जीवात्मा स्वयं ही अपना मित्र है और स्वयं ही अपना शत्रु है।",
            wordMeanings = "uddharet = one must elevate; ātmanā = by the self; ātmānam = the self; na = not; ātmānam = the self; avasādayet = degrade/ruin; ātmā = self; eva = alone; hi = indeed; ātmanaḥ = of the self; bandhuḥ = friend; ātmā = self; eva = alone; ripuḥ = enemy; ātmanaḥ = of the self.",
            commentary = "A foundational declaration of spiritual self-responsibility. No external circumstance can ruin or elevate a person without the consent of their own mind. A disciplined, enlightened mind is your most faithful ally; an uncontrolled, undisciplined mind is your fiercest adversary.",
            practicalApplication = "Take radical personal responsibility for your mental habits and choices. Stop blaming others and train your mind to be your greatest supporter.",
            tags = listOf("Self-Mastery", "Mind", "Personal Growth", "Willpower")
        ),
        Verse(
            id = "6.26",
            chapterId = 6,
            verseNumber = 26,
            textSanskrit = "यतो यतो निश्चरति मनश्चञ्चलमस्थिरम् |\nततस्ततो नियम्यैतदात्मन्येव वशं नयेत् ॥",
            textTransliteration = "yato yato niścarati manaś cañcalam asthiram\ntatas tato niyamyaitad ātmany eva vaśaṁ nayet",
            textEnglish = "From whatever cause the restless and unsteady mind wanders away, from that let him restrain it and bring it back under the control of the Self alone.",
            textHindi = "यह चंचल और अस्थिर मन जिस-जिस कारण से सांसारिक पदार्थों में विचरता है, उस-उस से इसको रोककर बार-बार आत्मा में ही स्थिर करे।",
            wordMeanings = "yataḥ yataḥ = wherever; niścarati = wanders; manaḥ = mind; cañcalam = restless; asthiram = unsteady; tataḥ tataḥ = from that; niyamya = restraining; etat = this; ātmani = in the Self; eva = only; vaśam = control; nayet = bring back.",
            commentary = "The gold standard technique of meditation. Do not become angry or discouraged when the mind drifts. With patient, gentle persistence, observe the distraction, release it, and bring attention back to the anchor of presence.",
            practicalApplication = "In meditation or deep work, treat wandering thoughts not as failure, but as opportunities to practice the gentle muscle of refocusing.",
            tags = listOf("Meditation", "Focus", "Mind Training", "Patience")
        ),

        // Chapter 7
        Verse(
            id = "7.8",
            chapterId = 7,
            verseNumber = 8,
            textSanskrit = "रसोऽहमप्सु कौन्तेय प्रभास्मि शशिसूर्ययोः |\nप्रणवः सर्ववेदेषु शब्दः खे पौरुषं नृषु ॥",
            textTransliteration = "raso 'ham apsu kaunteya prabhāsmi śaśi-sūryayoḥ\npraṇavaḥ sarva-vedeṣu śabdaḥ khe pauruṣaṁ nṛṣu",
            textEnglish = "I am the taste in water, O son of Kunti; I am the radiant light of the moon and the sun; I am the sacred syllable OM in all the Vedas, the sound in ether, and the heroic ability in men.",
            textHindi = "हे कौन्तेय! मैं जलों में रस हूँ, चन्द्रमा और सूर्य में प्रकाश हूँ, सम्पूर्ण वेदों में ॐकार हूँ, आकाश में शब्द और मनुष्यों में पुरुषार्थ हूँ।",
            wordMeanings = "rasaḥ = taste; aham = I am; apsu = in water; kaunteya = O son of Kunti; prabhā = radiance; asmi = I am; śaśi-sūryayoḥ = of the moon and sun; praṇavaḥ = the sacred Om; sarva-vedeṣu = in all Vedas; śabdaḥ = sound; khe = in space; pauruṣam = ability/strength; nṛṣu = in human beings.",
            commentary = "Krishna invites us to experience divinity through everyday sensory beauty. God is not hidden in remote heavens; the refreshing quench of clean water, the warm glow of sunlight, and the courage inside human hearts are all living signatures of the Divine.",
            practicalApplication = "Practice seeing the sacred in ordinary moments: drinking water, feeling the breeze, and recognizing talent in your fellow beings.",
            tags = listOf("Divine Presence", "Nature", "Sacredness", "Om")
        ),

        // Chapter 9
        Verse(
            id = "9.22",
            chapterId = 9,
            verseNumber = 22,
            textSanskrit = "अनन्याश्चिन्तयन्तो मां ये जनाः पर्युपासते |\nतेषां नित्याभियुक्तानां योगक्षेमं वहाम्यहम् ॥",
            textTransliteration = "ananyāś cintayanto māṁ ye janāḥ paryupāsate\nteṣāṁ nityābhiyuktānāṁ yoga-kṣemaṁ vahāmy aham",
            textEnglish = "To those who worship Me with unwavering single-minded devotion, meditating upon My divine presence, I personally carry what they lack and preserve what they have.",
            textHindi = "जो अनन्य भाव से मेरा चिन्तन करते हुए भक्तजन मेरी निष्काम उपासना करते हैं, उन नित्य-युक्त पुरुषों का 'योग' (अप्राप्त की प्राप्ति) और 'क्षेम' (प्राप्त की रक्षा) मैं स्वयं वहन करता हूँ।",
            wordMeanings = "ananyāḥ = without other thoughts; cintayantaḥ = meditating; mām = on Me; ye = who; janāḥ = people; paryupāsate = worship; teṣām = of them; nitya-abhiyuktānām = constantly devoted; yoga = providing what is lacking; kṣemam = preserving what is possessed; vahāmi = carry/bear; aham = I.",
            commentary = "One of the most cherished verses of comfort in the Gita. 'Yoga' means providing what a sincere soul needs for their spiritual and physical welfare; 'Kshema' means protecting and safeguarding it. The Lord takes personal guardianship over the fully surrendered heart.",
            practicalApplication = "Surrender chronic worry about the future. When your allegiance to righteousness and love is sincere, life provides the necessary grace and protection.",
            tags = listOf("Grace", "Protection", "Surrender", "Divine Care")
        ),
        Verse(
            id = "9.26",
            chapterId = 9,
            verseNumber = 26,
            textSanskrit = "पत्रं पुष्पं फलं तोयं यो मे भक्त्या प्रयच्छति |\nतदहं भक्त्युपहृतमश्नामि प्रयतात्मनः ॥",
            textTransliteration = "patraṁ puṣpaṁ phalaṁ toyaṁ yo me bhaktyā prayacchati\ntad ahaṁ bhakty-upahṛtam aśnāmi prayatātmanaḥ",
            textEnglish = "Whoever offers Me with love and devotion a leaf, a flower, a fruit, or even water, that offering made with love by a pure heart, I accept joyfully.",
            textHindi = "जो कोई भक्त मेरे लिए प्रेमपूर्वक पत्र, पुष्प, फल, जल आदि अर्पण करता है, उस शुद्ध बुद्धि वाले निष्काम भक्त का प्रेमपूर्वक लाया हुआ वह उपहार मैं सहर्ष स्वीकार करता हूँ।",
            wordMeanings = "patram = a leaf; puṣpam = a flower; phalam = a fruit; toyam = water; yaḥ = whoever; me = unto Me; bhaktyā = with devotion; prayacchati = offers; tat = that; aham = I; bhakti-upahṛtam = offering of devotion; aśnāmi = accept/partake; prayata-ātmanaḥ = of one with purified heart.",
            commentary = "God is not enticed by lavish wealth or ostentatious rituals. The only currency of the spiritual kingdom is unadulterated love. Even a droplet of water offered with reverence outweighs a mountain of gold offered with pride.",
            practicalApplication = "Approach prayer and daily acts of kindness with humble sincerity. It is the love behind your gesture that carries real spiritual power.",
            tags = listOf("Devotion", "Simplicity", "Love", "Bhakti")
        ),

        // Chapter 10
        Verse(
            id = "10.8",
            chapterId = 10,
            verseNumber = 8,
            textSanskrit = "अहं सर्वस्य प्रभवो मत्तः सर्वं प्रवर्तते |\nइति मत्वा भजन्ते मां बुधा भावसमन्विताः ॥",
            textTransliteration = "ahaṁ sarvasya prabhavo mattaḥ sarvaṁ pravartate\niti matvā bhajante māṁ budhā bhāva-samanvitāḥ",
            textEnglish = "I am the source of all; from Me everything in the universe evolves. Realizing this truth, the wise worship Me with loving devotion.",
            textHindi = "मैं सम्पूर्ण सृष्टि की उत्पत्ति का कारण हूँ और मुझसे ही सब कुछ प्रवर्तित होता है; ऐसा मानकर बुद्धिमान जन भावयुक्त होकर मेरा भजन करते हैं।",
            wordMeanings = "aham = I; sarvasya = of all; prabhavaḥ = the source; mattaḥ = from Me; sarvam = everything; pravartate = emanates; iti = thus; matvā = understanding; bhajante = worship; mām = Me; budhāḥ = the wise; bhāva-samanvitāḥ = endowed with heartfelt devotion.",
            commentary = "The cornerstone verse of the 'Chatur-shloki Gita' (the four core verses of Chapter 10). When a seeker understands that every particle, star, thought, and breath originates from one Divine Fountainhead, love and gratitude become natural.",
            practicalApplication = "Cultivate cosmic gratitude. Everything you cherish—family, breath, intellect, sunlight—is a gift flowing from the Supreme Source.",
            tags = listOf("Origin of Universe", "Cosmic Wisdom", "Gratitude", "Bhakti")
        ),

        // Chapter 11
        Verse(
            id = "11.32",
            chapterId = 11,
            verseNumber = 32,
            textSanskrit = "कालोऽस्मि लोकक्षयकृत्प्रवृद्धो\nलोकान्समाहर्तुमिह प्रवृत्तः |\nऋतेऽपि त्वां न भविष्यन्ति सर्वे\nयेऽवस्थिताः प्रत्यनीकेषु योधाः ॥",
            textTransliteration = "kālo 'smi loka-kṣaya-kṛt pravṛddho\nlokān samāhartum iha pravṛttaḥ\nṛte 'pi tvāṁ na bhaviṣyanti sarve\nye 'vasthitāḥ pratyanīkeṣu yodhāḥ",
            textEnglish = "I am mighty Time, the source of destruction that comes forth to consume the worlds. Even without your participation, none of the warriors assembled in the opposing ranks shall survive.",
            textHindi = "मैं लोकों का नाश करने वाला बढ़ा हुआ महाकाल हूँ; इस समय इन लोकों को नष्ट करने के लिए प्रवृत्त हुआ हूँ। तुम्हारे बिना भी विपक्षी सेना में खड़े हुए ये योद्धा नहीं बचेंगे।",
            wordMeanings = "kālaḥ = Time/Death; asmi = I am; loka-kṣaya-kṛt = destroyer of worlds; pravṛddhaḥ = mighty/expanded; lokān = worlds; samāhartum = to consume; iha = here; pravṛttaḥ = engaged; ṛte = without; api = even; tvām = you; na = not; bhaviṣyanti = will survive; sarve = all; ye = who; avasthitāḥ = positioned; pratyanīkeṣu = in opposing armies; yodhāḥ = warriors.",
            commentary = "Famously uttered by J. Robert Oppenheimer at the dawn of the atomic age. In this cosmic vision, Krishna reveals Time as the unstoppable force of transformation. All temporary forms must eventually dissolve back into their timeless origin.",
            practicalApplication = "Understand that time waits for no one. Do not postpone meaningful, ethical, and compassionate action. Make every heartbeat count.",
            tags = listOf("Time", "Kala", "Cosmic Reality", "Transience")
        ),

        // Chapter 12
        Verse(
            id = "12.13",
            chapterId = 12,
            verseNumber = 13,
            textSanskrit = "अद्वेष्टा सर्वभूतानां मैत्रः करुण एव च |\nनिर्ममो निरहङ्कारः समदुःखसुखः क्षमी ॥",
            textTransliteration = "adveṣṭā sarva-bhūtānāṁ maitraḥ karuṇa eva ca\nnirmamo nirahaṅkāraḥ sama-duḥkha-sukhaḥ kṣamī",
            textEnglish = "He who has no ill will toward any being, who is friendly and compassionate, free from possessiveness and ego, equal in sorrow and joy, and forgiving...",
            textHindi = "जो पुरुष सब भूतों में द्वेष-भाव से रहित, सबका मित्र और दयालु है, ममतारहित और अहंकाररहित है, सुख-दुःख में सम और क्षमाशील है...",
            wordMeanings = "adveṣṭā = non-hating; sarva-bhūtānām = toward all beings; maitraḥ = friendly; karuṇaḥ = compassionate; eva = truly; ca = and; nirmamaḥ = free from possessiveness; nirahaṅkāraḥ = free from egoism; sama-duḥkha-sukhaḥ = equal in pain and pleasure; kṣamī = forgiving.",
            commentary = "Krishna lists the hallmark traits of the true lover of God. Spirituality is not measured by intellectual gymnastics, but by benevolence towards all creatures, absence of malice, and the capacity to forgive.",
            practicalApplication = "Test your spiritual maturity by how you treat those who cannot do anything for you, and how swiftly you forgive those who hurt you.",
            tags = listOf("Compassion", "Forgiveness", "Kindness", "Love for All")
        ),

        // Chapter 15
        Verse(
            id = "15.7",
            chapterId = 15,
            verseNumber = 7,
            textSanskrit = "ममैवांशो जीवलोके जीवभूतः सनातनः |\nमनःषष्ठानीन्द्रियाणि प्रकृतिस्थानि कर्षति ॥",
            textTransliteration = "mamaivāṁśo jīva-loke jīva-bhūtaḥ sanātanaḥ\nmanaḥ-ṣaṣṭhānīndriyāṇi prakṛti-sthāni karṣati",
            textEnglish = "An eternal portion of Myself, having become a living soul in this world of life, draws to itself the five senses and the mind, which rest in material nature.",
            textHindi = "इस देह में यह सनातन जीवात्मा मेरा ही अंश है और वही इन प्रकृति में स्थित मन और पाँचों इन्द्रियों को आकर्षित करता है।",
            wordMeanings = "mama = My; eva = indeed; aṁśaḥ = fragment/portion; jīva-loke = in the world of living beings; jīva-bhūtaḥ = becoming a conditioned soul; sanātanaḥ = eternal; manaḥ = mind; ṣaṣṭhāni = with the sixth; indriyāṇi = senses; prakṛti-sthāni = resting in nature; karṣati = draws/struggles with.",
            commentary = "Every living being holds a spark of the divine. You are not a forgotten accident of biology; you are an eternal ray of the Supreme Light. Remembering this kinship with God dissolves loneliness and low self-esteem.",
            practicalApplication = "Look at yourself and others as sacred sparks of the Divine. Treat every person you encounter with the dignity due to a temple of God.",
            tags = listOf("Divine Spark", "Soul", "Unity", "Sacredness of Life")
        ),

        // Chapter 18
        Verse(
            id = "18.65",
            chapterId = 18,
            verseNumber = 65,
            textSanskrit = "मन्मना भव मद्भक्तो मद्याजी मां नमस्कुरु |\nमामेवैष्यसि सत्यं ते प्रतिजाने प्रियोऽसि मे ॥",
            textTransliteration = "man-manā bhava mad-bhakto mad-yājī māṁ namaskuru\nmām evaiṣyasi satyaṁ te pratijāne priyo 'si me",
            textEnglish = "Fix your mind on Me, be devoted to Me, worship Me, and bow down to Me. You will certainly come to Me. I promise you this in truth, for you are dearly beloved to Me.",
            textHindi = "तू अपने मन को मुझमें लगा, मेरा भक्त बन, मेरा पूजन कर और मुझको नमस्कार कर; ऐसा करने से तू मुझको ही प्राप्त होगा, यह मैं तुझसे सत्य प्रतिज्ञा करता हूँ, क्योंकि तू मेरा अत्यन्त प्रिय है।",
            wordMeanings = "mat-manāḥ = mind fixed on Me; bhava = become; mat-bhaktaḥ = My devotee; mat-yājī = worshipping Me; mām = to Me; namaskuru = bow down; mām = to Me; eva = certainly; eṣyasi = you will come; satyam = truly; te = to you; pratijāne = I promise; priyaḥ = dear; asi = you are; me = to Me.",
            commentary = "Sri Krishna speaks with boundless affection to Arjuna and to every sincere seeker through the ages: 'You are dearly beloved to Me.' God desires our communion far more than we desire His.",
            practicalApplication = "Throughout the day, inwardly dedicate your thoughts, actions, and successes to God. Walk through life knowing you are cherished by the Divine.",
            tags = listOf("Divine Love", "Devotion", "Surrender", "Comfort")
        ),
        Verse(
            id = "18.66",
            chapterId = 18,
            verseNumber = 66,
            textSanskrit = "सर्वधर्मान्परित्यज्य मामेकं शरणं व्रज |\nअहं त्वा सर्वपापेभ्यो मोक्षयिष्यामि मा शुचः ॥",
            textTransliteration = "sarva-dharmān parityajya mām ekaṁ śaraṇaṁ vraja\nahaṁ tvā sarva-pāpebhyo mokṣayiṣyāmi mā śucaḥ",
            textEnglish = "Abandon all varieties of dharmas and surrender unto Me alone. I shall deliver you from all sinful reactions; do not grieve.",
            textHindi = "सम्पूर्ण धर्मों को अर्थात सभी कर्तव्यों और चिन्ताओं को मुझमें समर्पित करके तू केवल एक मेरी शरण में आ जा; मैं तुझे सम्पूर्ण पापों और बन्धनों से मुक्त कर दूँगा, तू शोक मत कर।",
            wordMeanings = "sarva-dharmān = all varieties of religion/duties; parityajya = relinquishing/abandoning; mām = to Me; ekam = alone; śaraṇam = refuge; vraja = take; aham = I; tvā = you; sarva-pāpebhyaḥ = from all sins/bondage; mokṣayiṣyāmi = shall liberate; mā = do not; śucaḥ = grieve.",
            commentary = "The 'Charama Shloka'—the crowning pinnacle and grand final promise of the Bhagavad Gita. When all human strategies, philosophical systems, and moral efforts reach their limit, complete loving surrender (Sharanagati) to the Divine dissolves all fear, sin, and grief.",
            practicalApplication = "When facing situations beyond your control, let go of desperate micro-management. Hand your burdens over to God and rest in fearless peace.",
            tags = listOf("Ultimate Surrender", "Liberation", "Moksha", "Fearlessness", "Grace")
        ),
        Verse(
            id = "18.78",
            chapterId = 18,
            verseNumber = 78,
            textSanskrit = "यत्र योगेश्वरः कृष्णो यत्र पार्थो धनुर्धरः |\nतत्र श्रीर्विजयो भूतिर्ध्रुवा नीतिर्मतिर्मम ॥",
            textTransliteration = "yatra yogeśvaraḥ kṛṣṇo yatra pārtho dhanur-dharaḥ\ntatra śrīr vijayo bhūtir dhruvā nītir matir mama",
            textEnglish = "Wherever there is Sri Krishna, the Lord of Yoga, and wherever there is Arjuna, the supreme archer, there will certainly be wealth, victory, prosperity, and unwavering righteousness. This is my firm conviction.",
            textHindi = "जहाँ योगेश्वर श्रीकृष्ण हैं और जहाँ गाण्डीव-धनुर्धारी अर्जुन है, वहीं पर श्री (वैभव), विजय, विभूति और अचल नीति है—ऐसा मेरा निश्चित मत है।",
            wordMeanings = "yatra = wherever; yoga-īśvaraḥ = the master of Yoga; kṛṣṇaḥ = Sri Krishna; yatra = wherever; pārthaḥ = Arjuna; dhanuḥ-dharaḥ = the wielder of the bow; tatra = there; śrīḥ = fortune/splendor; vijayaḥ = victory; bhūtiḥ = prosperity; dhruvā = unshakeable; nītiḥ = righteousness/morality; matiḥ = conviction; mama = my.",
            commentary = "The final verse of the Bhagavad Gita spoken by Sanjaya. It offers the master formula for success in life: the harmonious combination of divine grace and guidance (Krishna) with dedicated, heroic human effort and responsibility (Arjuna).",
            practicalApplication = "Do not rely on passive luck alone, nor on arrogant ego alone. Combine your sincere, disciplined efforts with prayer for divine wisdom, and victory is assured.",
            tags = listOf("Victory", "Grace and Action", "Prosperity", "Dharma", "Culmination")
        )
    )

    fun getChapter(chapterId: Int): Chapter? = chapters.find { it.id == chapterId }

    fun getVersesForChapter(chapterId: Int): List<Verse> = keyVerses.filter { it.chapterId == chapterId }

    fun getVerse(verseId: String): Verse? = keyVerses.find { it.id == verseId }

    fun searchVerses(query: String): List<Verse> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        return keyVerses.filter { verse ->
            verse.textEnglish.lowercase().contains(q) ||
            verse.textHindi.contains(q) ||
            verse.textSanskrit.contains(q) ||
            verse.textTransliteration.lowercase().contains(q) ||
            verse.id.contains(q) ||
            verse.commentary.lowercase().contains(q) ||
            verse.tags.any { it.lowercase().contains(q) }
        }
    }
}
