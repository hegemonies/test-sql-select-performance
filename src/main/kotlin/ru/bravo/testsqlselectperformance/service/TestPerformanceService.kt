package ru.bravo.testsqlselectperformance.service

import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.bravo.testsqlselectperformance.model.Task
import ru.bravo.testsqlselectperformance.repository.TaskRepository
import kotlin.system.measureTimeMillis

@Service
class TestPerformanceService(
    private val taskRepository: TaskRepository,
) {

    private fun generateTasks(size: Int): List<Task> {
        val countTasksInDatabase = taskRepository.count().toInt()
        if (size - countTasksInDatabase <= 0) {
            return emptyList()
        }
        return (1..size - countTasksInDatabase).map { Task() }
    }

    private fun generateAndSaveTasks(size: Int) {
        val tasks = generateTasks(size)
        taskRepository.saveAll(tasks)
    }

    fun loadData(size: Long, chunkSize: Int) {
        logger.info("Start load data size=$size, chunk=$chunkSize")
        val elapsed = measureTimeMillis {
            (1..size).chunked(chunkSize).forEach { indexes ->
                generateAndSaveTasks(indexes.size)
            }
        }
        logger.info("Finish load data $size, chunk=$chunkSize by $elapsed ms")
    }

    fun deleteData() {
        logger.info("Start delete data")
        val elapsed = measureTimeMillis {
            taskRepository.deleteAll()
        }
        logger.info("Finish delete data by $elapsed ms")
    }

    fun warmUp() {
        logger.info("Start warm up")
        loadData(10_000, 1000)
        deleteData()
        logger.info("Finish warm up")
    }

    fun selectAll(pageSize: Int) {
        val elapsed = measureTimeMillis {
            var pageNumber = 0
            val sort = Sort.by(Sort.Direction.ASC, Task::id.name)

            do {
                val pageRequest = PageRequest.of(pageNumber++, pageSize, sort)
                val tasks = taskRepository.findAll(pageRequest).content
            } while (tasks.size == pageSize)
        }
    }

    fun runTestSelectAll(pageSize: Int) {
        logger.info("Start select all")
        val testTimes = 10
        var elapsedCounter = 0L
        (1..testTimes).forEach {
            val elapsed = measureTimeMillis {
                selectAll(pageSize)
            }
            elapsedCounter += elapsed
        }
        logger.info("Finish select all with pageSize=$pageSize by ${elapsedCounter / testTimes} ms")
    }

    fun runTest() {
        warmUp()

        // run test on 1_000 tasks and 100 chunk size
        // run test on 10_000 tasks and 100 chunk size
        // run test on 100_000 tasks and 100 chunk size
        // run test on 1_000_000 tasks and 100 chunk size
        // run test on 10_000_000 tasks and 100 chunk size
//        loadData(1_000, 100)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(10_000, 100)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(100_000, 100)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(1_000_000, 100)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(10_000_000, 100)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        deleteData()

        // run test on 1_000 tasks and 1000 chunk size
        // run test on 10_000 tasks and 1000 chunk size
        // run test on 100_000 tasks and 1000 chunk size
        // run test on 1_000_000 tasks and 1000 chunk size
        // run test on 10_000_000 tasks and 1000 chunk size
//        loadData(1_000, 1000)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(10_000, 1000)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(100_000, 1000)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(1_000_000, 1000)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        loadData(10_000_000, 1000)
//        runTestSelectAll(100)
//        runTestSelectAll(1000)
//        runTestSelectAll(10000)
//        deleteData()

        // run test on 1_000 tasks and 10000 chunk size
        // run test on 10_000 tasks and 10000 chunk size
        // run test on 100_000 tasks and 10000 chunk size
        // run test on 1_000_000 tasks and 10000 chunk size
        // run test on 10_000_000 tasks and 10000 chunk size
        loadData(1_000, 10000)
        runTestSelectAll(100)
        runTestSelectAll(1000)
        runTestSelectAll(10000)
        loadData(10_000, 10000)
        runTestSelectAll(100)
        runTestSelectAll(1000)
        runTestSelectAll(10000)
        loadData(100_000, 10000)
        runTestSelectAll(100)
        runTestSelectAll(1000)
        runTestSelectAll(10000)
        loadData(1_000_000, 10000)
        runTestSelectAll(100)
        runTestSelectAll(1000)
        runTestSelectAll(10000)
        loadData(10_000_000, 10000)
        runTestSelectAll(100)
        runTestSelectAll(1000)
        runTestSelectAll(10000)
        deleteData()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
