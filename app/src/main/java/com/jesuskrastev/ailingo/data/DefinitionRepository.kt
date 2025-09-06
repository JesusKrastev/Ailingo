package com.jesuskrastev.ailingo.data

import com.jesuskrastev.ailingo.data.room.definition.DefinitionDao
import com.jesuskrastev.ailingo.models.Definition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefinitionRepository @Inject constructor(
    private val definitionDao: DefinitionDao,
) {
    suspend fun get(): Flow<List<Definition>> = withContext(Dispatchers.IO) {
        definitionDao.get().map { it.map { it.toDefinition() } }
    }

    suspend fun getPending(): List<Definition> = withContext(Dispatchers.IO) {
        definitionDao.getPending().map { it.toDefinition() }
    }

    suspend fun insert(definition: Definition) = withContext(Dispatchers.IO) {
        definitionDao.insert(definition.toDefinitionEntity())
    }

    suspend fun update(definition: Definition) = withContext(Dispatchers.IO) {
        definitionDao.update(definition.toDefinitionEntity())
    }
}