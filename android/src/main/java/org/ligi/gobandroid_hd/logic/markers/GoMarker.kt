/**
 * gobandroid
 * by Marcus -Ligi- Bueschleb
 * http://ligi.de

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation;

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see //www.gnu.org/licenses/>.

 */

package org.ligi.gobandroid_hd.logic.markers

import android.graphics.Canvas
import android.graphics.Paint
import org.ligi.gobandroid_hd.logic.Cell

/**
 * class to mark a pos on the board useful for go problems - e.g. from SGF
 */
abstract class GoMarker(val cell: Cell) : Cell by cell {

    abstract fun draw(c: Canvas, size: Float, x: Float, y: Float, paint: Paint)

    fun isInCell(cell: Cell): Boolean {
        return this.cell.equals(cell)
    }

    override fun equals(other: Any?): Boolean {
        if (other is GoMarker ) {
            return other.cell.equals(cell)
        }

        return false
    }
}
