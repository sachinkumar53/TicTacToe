package com.sachin.app.tictactoe.util

import com.sachin.app.tictactoe.model.Fillable

object GameUtil {

    fun checkWin(filledPositions: ArrayList<Fillable>): Boolean =
        getWinPoints(filledPositions).isNotEmpty()

    fun getWinPoints(filledPositions: ArrayList<Fillable>): ArrayList<Fillable> {

        val r0c0 = filledPositions.getAtPoint(0, 0)
        val r0c1 = filledPositions.getAtPoint(1, 0)
        val r0c2 = filledPositions.getAtPoint(2, 0)

        val r1c0 = filledPositions.getAtPoint(0, 1)
        val r1c1 = filledPositions.getAtPoint(1, 1)
        val r1c2 = filledPositions.getAtPoint(2, 1)

        val r2c0 = filledPositions.getAtPoint(0, 2)
        val r2c1 = filledPositions.getAtPoint(1, 2)
        val r2c2 = filledPositions.getAtPoint(2, 2)

        val points = arrayListOf<Fillable>()
        //horizontal row 1
        if (r0c0 != null && r0c1 != null && r0c2 != null && r0c0.fill == r0c1.fill && r0c1.fill == r0c2.fill) {
            points.add(r0c0)
            points.add(r0c1)
            points.add(r0c2)
            return points

        }

        //horizontal row 2
        if (r1c0 != null && r1c1 != null && r1c2 != null && r1c0.fill == r1c1.fill && r1c1.fill == r1c2.fill) {
            points.add(r1c0)
            points.add(r1c1)
            points.add(r1c2)
            return points
        }

        //horizontal row 3
        if (r2c0 != null && r2c1 != null && r2c2 != null && r2c0.fill == r2c1.fill && r2c1.fill == r2c2.fill) {
            points.add(r2c0)
            points.add(r2c1)
            points.add(r2c2)
            return points
        }
        //vertical column 1
        if (r0c0 != null && r1c0 != null && r2c0 != null && r0c0.fill == r1c0.fill && r1c0.fill == r2c0.fill) {
            points.add(r0c0)
            points.add(r1c0)
            points.add(r2c0)
            return points
        }

        //vertical column 2
        if (r0c1 != null && r1c1 != null && r2c1 != null && r0c1.fill == r1c1.fill && r1c1.fill == r2c1.fill) {
            points.add(r0c1)
            points.add(r1c1)
            points.add(r2c1)
            return points
        }

        //vertical column 3
        if (r0c2 != null && r1c2 != null && r2c2 != null && r0c2.fill == r1c2.fill && r1c2.fill == r2c2.fill) {
            points.add(r0c2)
            points.add(r1c2)
            points.add(r2c2)
            return points
        }

        //diagonal left to right
        if (r0c0 != null && r1c1 != null && r2c2 != null && r0c0.fill == r1c1.fill && r1c1.fill == r2c2.fill) {
            points.add(r0c0)
            points.add(r1c1)
            points.add(r2c2)
            return points
        }

        //diagonal right to left
        if (r0c2 != null && r1c1 != null && r2c0 != null && r0c2.fill == r1c1.fill && r1c1.fill == r2c0.fill) {
            points.add(r0c2)
            points.add(r1c1)
            points.add(r2c0)
            return points
        }

        return points
    }
}

fun ArrayList<Fillable>.getAtPoint(x: Int, y: Int): Fillable? {
    forEach { if (it.x == x && it.y == y) return it }
    return null
}

fun <E> ArrayList<E>.isAllFilled(): Boolean = size == 9
