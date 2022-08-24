package ru.bravo.testsqlselectperformance.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import kotlin.random.Random

@Entity
@Table(name = "task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "device_type")
    val deviceType: Short = Random.nextInt().toShort(),

    val type: Short = Random.nextInt().toShort(),

    @Column(name = "snmp_oid")
    val snmpOid: String = UUID.randomUUID().toString(),

    @Column(name = "device_id")
    val deviceId: Long = Random.nextLong(),

    @Column(name = "metric_id")
    val metricId: Long = Random.nextLong(),
)
