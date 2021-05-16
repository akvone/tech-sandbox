package com.akvone.data_jpa.entities

import javax.persistence.*

@Entity
class CityEntity {
    @Id
    @GeneratedValue
    var id: Long? = null
    var name: String? = null

    @OneToMany(cascade = [CascadeType.ALL])
    var streets: List<StreetEntity> = emptyList()
}