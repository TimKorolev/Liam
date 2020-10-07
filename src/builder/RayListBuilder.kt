package builder

import logo.Color
import logo.RayPoint
import logo.Ray
import kotlin.random.Random

class RayListBuilder {

    companion object {

        fun getRays(): List<Ray> {
            var rayNumber = Random.nextInt(3, 8)
            val firstRay = generateRay()
            val rayList = mutableListOf(firstRay)

            while (rayNumber > 1) {
                val previousRay = rayList[rayList.lastIndex]
                rayList.add(generateRay(previousRay))
                rayNumber--
            }

            return rayList
        }

        private fun generateRay(previousRay: Ray? = null): Ray {
            val colorPoint1 = Color.GREEN
            val colorPoint2 = Color.LEMON_YELLOW

            var xPoint1 = Random.nextDouble(200.0, 800.0)
            var yPoint1 = Random.nextDouble(200.0, 800.0)
            var rPoint1 = Random.nextDouble(60.0, 80.0)

            val xPoint2 = Random.nextDouble(200.0, 800.0)
            val yPoint2 = Random.nextDouble(200.0, 800.0)
            val rPoint2 = Random.nextDouble(60.0, 80.0)

            if (previousRay != null) {
                xPoint1 = previousRay.endPoint.x
                yPoint1 = previousRay.endPoint.y
                rPoint1 = previousRay.endPoint.r
            }

            val point1 = RayPoint(xPoint1, yPoint1, rPoint1, colorPoint1)
            val point2 = RayPoint(xPoint2, yPoint2, rPoint2, colorPoint2)


            val ray = Ray(point1, point2)

            if (previousRay != null) {

                ray.p1 = previousRay.p2
                ray.p4 = previousRay.p3
            }

            return ray
        }
    }
}