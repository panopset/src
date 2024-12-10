package com.panopset.blackjackEngine

import com.panopset.compat.Logz
import java.util.*

class CountingSystems(val countingSystemsData: ArrayList<String>) {

    private var systemCatalog: MutableMap<String, CountingSystem> = Collections.synchronizedSortedMap(TreeMap())
    private var keys = Collections.synchronizedSortedSet(TreeSet<CountingSystem>())
    var keyNames = ArrayList<String>()
    var defaultKey = ""

    init {
        populate()
        populateKeyNames()
    }

    private fun populateKeyNames() {
        for ((_, value) in systemCatalog.entries) {
            keys.add(value)
        }
        for (cs in keys) {
            keyNames.add(cs.name)
        }
    }

    fun getTrueCount(): Int {
        return findSelected().count
    }

    fun count(blackjackCard: BlackjackCard) {
        findSelected().count(blackjackCard)
    }

    private fun populate() {
        var firstKey = ""
        systemCatalog.clear()
        var index = 0
        for (s in countingSystemsData) {
            if (s.length <= NAME_POS + 1 || s.indexOf("#") == 0) {
                continue
            }
            val isDefault = s.indexOf("*") > -1
            val key = s.substring(if (isDefault) NAME_POS + 1 else NAME_POS).trim()
            val dta = s.substring(0, NAME_POS)
            if (firstKey.isEmpty()) {
                firstKey = key
            }
            systemCatalog[key] = CountingSystem( key, dta, index++)
            if (isDefault) {
                defaultKey = key
            }
        }
        if (defaultKey.isEmpty()) {
            defaultKey = firstKey
        }
    }

    fun setSystemByName(keyName: String) {
        setCountingSystem(getKeyNamePosition(keyName))
    }

    fun setSystemByKeyNamePosition(position: Int) {
        setCountingSystem(position)
    }

    fun updateByIndex(i: Int) {
        setSystemByKeyNamePosition(i)
    }

    fun getKeyNamePosition(keyName: String): Int {
        return findSelectionNbr(keyName)
    }

    private fun findSelectionNbr(key: String): Int {
        for ((_, value) in systemCatalog) {
            if (value.name == key) {
                return value.selection
            }
        }
        return findSelectionNbr(defaultKey)
    }

    fun findSelectionKey(selection: Int): String {
        for ((_, value) in systemCatalog) {
            if (value.selection == selection) {
                return value.name
            }
        }
        return defaultKey
    }

    private var selected = systemCatalog[defaultKey]

    fun findSelected(): CountingSystem {
        if (selected == null) {
            var foundKey = ""
            for (key in systemCatalog.keys) {
                if (key == defaultKey) {
                    foundKey = key
                } else if (key == "*$defaultKey") {
                    foundKey = key
                }
            }
            if (foundKey.indexOf("*") == 0) {
                foundKey = foundKey.substring(1)
            }
            if (foundKey.isEmpty()) {
                selected = pointOfNoReturn()
            } else {
                selected = systemCatalog[foundKey] ?: pointOfNoReturn()
            }
        }
        return selected ?: pointOfNoReturn()
    }

    private fun pointOfNoReturn(): CountingSystem {
        Logz.errorMsg("Failed to load counting systems from resource cs.txt.")
        return CountingSystem( "Hi-Low", "+1 +1 +1 +1 +1  0  0  0 -1 -1 Hi-Lo", 4)
    }

    private fun setCountingSystem(position: Int) {
        selected = systemCatalog[findSelectionKey(position)]
    }


    fun resetCount() {
        findSelected().resetCount()
    }

    fun reset() {
        populate()
        selected = systemCatalog[defaultKey]
        resetCount()
    }
}

const val NAME_POS = 30
