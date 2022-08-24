package ru.bravo.testsqlselectperformance.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.bravo.testsqlselectperformance.model.Task

interface TaskRepository : JpaRepository<Task, Long>
