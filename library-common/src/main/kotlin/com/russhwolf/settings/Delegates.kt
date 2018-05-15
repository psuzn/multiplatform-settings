package com.russhwolf.settings

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Settings.int(key: String, defaultValue: Int = 0): ReadWriteProperty<Any?, Int> =
    IntDelegate(this, key, defaultValue)

fun Settings.long(key: String, defaultValue: Long = 0): ReadWriteProperty<Any?, Long> =
    LongDelegate(this, key, defaultValue)

fun Settings.string(key: String, defaultValue: String = ""): ReadWriteProperty<Any?, String> =
    StringDelegate(this, key, defaultValue)

fun Settings.float(key: String, defaultValue: Float = 0f): ReadWriteProperty<Any?, Float> =
    FloatDelegate(this, key, defaultValue)

fun Settings.double(key: String, defaultValue: Double = 0.0): ReadWriteProperty<Any?, Double> =
    DoubleDelegate(this, key, defaultValue)

fun Settings.boolean(key: String, defaultValue: Boolean = false): ReadWriteProperty<Any?, Boolean> =
    BooleanDelegate(this, key, defaultValue)

fun Settings.nullableInt(key: String): ReadWriteProperty<Any?, Int?> = NullableIntDelegate(this, key)

fun Settings.nullableLong(key: String): ReadWriteProperty<Any?, Long?> = NullableLongDelegate(this, key)

fun Settings.nullableString(key: String): ReadWriteProperty<Any?, String?> = NullableStringDelegate(this, key)

fun Settings.nullableFloat(key: String): ReadWriteProperty<Any?, Float?> = NullableFloatDelegate(this, key)

fun Settings.nullableDouble(key: String): ReadWriteProperty<Any?, Double?> = NullableDoubleDelegate(this, key)

fun Settings.nullableBoolean(key: String): ReadWriteProperty<Any?, Boolean?> = NullableBooleanDelegate(this, key)

private abstract class Delegate<T : Any>(
    private val settings: Settings,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = settings[key, defaultValue]

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        settings[key] = value
    }

    abstract operator fun Settings.get(key: String, defaultValue: T): T

    abstract operator fun Settings.set(key: String, value: T)
}

private abstract class NullableDelegate<T>(
    private val settings: Settings,
    private val key: String
) : ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return if (settings.contains(key)) settings[key] else null
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value != null) settings[key] = value else settings.remove(key)
    }

    abstract operator fun Settings.get(key: String): T

    abstract operator fun Settings.set(key: String, value: T)
}

private class IntDelegate(settings: Settings, key: String, defaultValue: Int = 0) :
    Delegate<Int>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: Int): Int = getInt(key, defaultValue)
    override fun Settings.set(key: String, value: Int) = putInt(key, value)
}

private class LongDelegate(settings: Settings, key: String, defaultValue: Long = 0) :
    Delegate<Long>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: Long): Long = getLong(key, defaultValue)
    override fun Settings.set(key: String, value: Long) = putLong(key, value)
}

private class StringDelegate(settings: Settings, key: String, defaultValue: String = "") :
    Delegate<String>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: String): String =
        getString(key, defaultValue)

    override fun Settings.set(key: String, value: String) = putString(key, value)
}

private class FloatDelegate(settings: Settings, key: String, defaultValue: Float = 0f) :
    Delegate<Float>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: Float): Float = getFloat(key, defaultValue)
    override fun Settings.set(key: String, value: Float) = putFloat(key, value)
}

private class DoubleDelegate(settings: Settings, key: String, defaultValue: Double = 0.0) :
    Delegate<Double>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: Double): Double =
        getDouble(key, defaultValue)

    override fun Settings.set(key: String, value: Double) = putDouble(key, value)
}

private class BooleanDelegate(settings: Settings, key: String, defaultValue: Boolean = false) :
    Delegate<Boolean>(settings, key, defaultValue) {
    override fun Settings.get(key: String, defaultValue: Boolean): Boolean =
        getBoolean(key, defaultValue)

    override fun Settings.set(key: String, value: Boolean) = putBoolean(key, value)
}

private class NullableIntDelegate(settings: Settings, key: String) :
    NullableDelegate<Int>(settings, key) {
    override fun Settings.get(key: String): Int = getInt(key)
    override fun Settings.set(key: String, value: Int) = putInt(key, value)
}

private class NullableLongDelegate(settings: Settings, key: String) :
    NullableDelegate<Long>(settings, key) {
    override fun Settings.get(key: String): Long = getLong(key)
    override fun Settings.set(key: String, value: Long) = putLong(key, value)
}

private class NullableStringDelegate(settings: Settings, key: String) :
    NullableDelegate<String>(settings, key) {
    override fun Settings.get(key: String): String = getString(key)
    override fun Settings.set(key: String, value: String) = putString(key, value)
}

private class NullableFloatDelegate(settings: Settings, key: String) :
    NullableDelegate<Float>(settings, key) {
    override fun Settings.get(key: String): Float = getFloat(key)
    override fun Settings.set(key: String, value: Float) = putFloat(key, value)
}

private class NullableDoubleDelegate(settings: Settings, key: String) :
    NullableDelegate<Double>(settings, key) {
    override fun Settings.get(key: String): Double = getDouble(key)
    override fun Settings.set(key: String, value: Double) = putDouble(key, value)
}

private class NullableBooleanDelegate(settings: Settings, key: String) :
    NullableDelegate<Boolean>(settings, key) {
    override fun Settings.get(key: String): Boolean = getBoolean(key)
    override fun Settings.set(key: String, value: Boolean) = putBoolean(key, value)
}