package logo

import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow

data class Ray(val startPoint: RayPoint, val endPoint: RayPoint) {

    var rotX = 1.0
    var rotY = -1.0

    var p1: RenderPoint
    var p2: RenderPoint
    var p3: RenderPoint
    var p4: RenderPoint

    init {
        rotX = -1.0
        rotY = -1.0

        if (startPoint.x < endPoint.x) {
            rotX = 1.0
        }
        if (startPoint.y < endPoint.y) {
            rotY = 1.0
        }
        p1 = RenderPoint(
            startPoint.x + rotX * cos(atan(getK(startPoint, endPoint))) * startPoint.r,
            startPoint.y + rotY * cos(atan(getK(startPoint, endPoint))).pow(2) * startPoint.r + startPoint.r
        )
        p2 = RenderPoint(
            endPoint.x + rotX * cos(atan(getK(startPoint, endPoint))) * endPoint.r,
            endPoint.y - rotY * cos(atan(getK(startPoint, endPoint))).pow(2) * endPoint.r + endPoint.r
        )
        p3 = RenderPoint(
            endPoint.x - rotX * cos(atan(getK(startPoint, endPoint))) * endPoint.r,
            endPoint.y - rotY * cos(atan(getK(startPoint, endPoint))).pow(2) * endPoint.r - endPoint.r
        )
        p4 = RenderPoint(
            startPoint.x - rotX * cos(atan(getK(startPoint, endPoint))) * startPoint.r,
            startPoint.y + rotY * cos(atan(getK(startPoint, endPoint))).pow(2) * startPoint.r - startPoint.r
        )

    }

    private fun getK(startPoint: RayPoint, endPoint: RayPoint): Double {
        return (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x)
    }
}