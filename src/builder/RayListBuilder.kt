package builder

import logo.Color
import logo.Point
import logo.Ray
import kotlin.random.Random

class RayListBuilder {

    companion object {

        fun getRays(): List<Ray> {
            var rayNumber = Random.nextInt(3, 6)
            val firstRay = generateRay()
            val rayList = mutableListOf(firstRay)

            while (rayNumber > 1) {
                val previousRay = rayList[rayList.lastIndex]
                rayList.add(generateRay(previousRay.endPoint))
                rayNumber--
            }

            return rayList
        }

        private fun generateRay(previousEndPoint: Point? = null): Ray {
            val colorPoint1 = Color.GREEN
            val colorPoint2 = Color.LEMON_YELLOW

            val xPoint1: Double
            val yPoint1: Double
            val rPoint1: Double

            val xPoint2 = Random.nextDouble(0.0, 1000.0)
            val yPoint2 = Random.nextDouble(0.0, 1000.0)
            val rPoint2 = Random.nextDouble(30.0, 100.0)

            if (previousEndPoint == null) {
                xPoint1 = Random.nextDouble(0.0, 1000.0)
                yPoint1 = Random.nextDouble(0.0, 1000.0)
                rPoint1 = Random.nextDouble(30.0, 100.0)
            } else {
                xPoint1 = previousEndPoint.x
                yPoint1 = previousEndPoint.y
                rPoint1 = previousEndPoint.r
            }

            val point1 = Point(xPoint1, yPoint1, rPoint1, colorPoint1)
            val point2 = Point(xPoint2, yPoint2, rPoint2, colorPoint2)

            return Ray(point1, point2)
        }
    }
}