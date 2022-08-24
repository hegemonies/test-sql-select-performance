package ru.bravo.testsqlselectperformance

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.bravo.testsqlselectperformance.service.TestPerformanceService

@SpringBootApplication
class TestSqlSelectPerformanceApplication

fun main(args: Array<String>) {
	runApplication<TestSqlSelectPerformanceApplication>(*args)
}

@Component
class TestRunner(
	private val testPerformanceService: TestPerformanceService,
) {

	@EventListener(ApplicationReadyEvent::class)
	fun run() {
		logger.info("Start test")
		testPerformanceService.deleteData()
		testPerformanceService.runTest()
		logger.info("Finish test")
	}

	companion object {
		private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
	}
}
