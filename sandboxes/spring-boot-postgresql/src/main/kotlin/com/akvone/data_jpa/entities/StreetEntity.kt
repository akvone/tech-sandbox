package com.akvone.data_jpa.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class StreetEntity {
    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String? = null
}