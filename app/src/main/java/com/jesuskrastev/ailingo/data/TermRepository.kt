package com.jesuskrastev.ailingo.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesuskrastev.ailingo.BuildConfig
import com.jesuskrastev.ailingo.data.room.term.TermDao
import com.jesuskrastev.ailingo.models.Term
import com.jesuskrastev.ailingo.ui.features.home.TermState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.time.LocalDate
import javax.inject.Inject

class TermRepository @Inject constructor(
    private val termDao: TermDao,
) {
    suspend fun get(): Term = withContext(Dispatchers.IO) {
        val term = termDao.get().firstOrNull()
        val today = LocalDate.now()
        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-pro",
            apiKey = BuildConfig.apiKey
        )

        when {
            term == null || term?.createdAt?.isBefore(today) == true -> {
                var newTerm = Term(term = "unknown", translation = "N/A", definition = "No data")

                val response = generativeModel.generateContent(
                    content {
                        text(
                            """
                            Generate a single interesting and commonly used English word that native speakers often use in everyday conversation.
                            Provide the word, its meaning in simple terms, and an example sentence showing how it is used naturally.
                            Return ONLY the JSON object in a single line, without any Markdown formatting, without code fences, without extra text, and without line breaks.
                            Use this structure:
                            {
                                "term": "<the English word>",
                                "translation": "<the Spanish translation of the word>",
                                "definition": "<example sentence showing natural use>"
                            }
                            """.trimIndent()
                        )
                    }
                )

                newTerm = runCatching { response?.text?.let { Json.decodeFromString<Term>(it) } }
                    .getOrElse { Term(term = "unknown", translation = "N/A", definition = "No data") }
                    ?: Term(term = "unknown", translation = "N/A", definition = "No data")

                termDao.deleteAll()
                termDao.insert(newTerm.toTermEntity())

                newTerm
            }
            else -> {
                term!!.toTerm()
            }
        }
    }
}