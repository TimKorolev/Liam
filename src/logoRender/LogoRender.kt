package logoRender

import Logo
import logo.BezierApex
import logo.Color.*
import logo.Point
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class LogoRender {
    companion object {
        fun render(logo: Logo, width: Int, height: Int) {
            val title = logo.square.title
            val rnd = Random.nextInt(9999)
            val file = File("C:\\Projects\\Liam\\logos\\${title}_logo_${rnd}.html")

            var rays: String = ""
            var defs: String = ""
            logo.rays.forEachIndexed { key, ray ->
                val bezierApexList = getBezierApexList(ray.startPoint, ray.endPoint)

                rays += """<g id="Layer_${key}">
                    <path d="
                    M${ray.startPoint.x},${ray.startPoint.y + ray.startPoint.r} 
                    L${ray.endPoint.x},${ray.endPoint.y + ray.endPoint.r}
                    C${bezierApexList[1]} ${bezierApexList[3]} ${ray.endPoint.x},${ray.endPoint.y - ray.endPoint.r} 
                    L${ray.startPoint.x},${ray.startPoint.y - ray.endPoint.r}
                    C${bezierApexList[2]} ${bezierApexList[0]} ${ray.startPoint.x},${ray.startPoint.y + ray.startPoint.r} 
                    z" 
                    fill="url(#Gradient_${key})"/>
                </g>"""
                defs += """<linearGradient id="Gradient_${key}">
                    <stop offset="0" stop-color="${ray.startPoint.color.hex}"></stop>
                    <stop offset="1" stop-color="${ray.endPoint.color.hex}"></stop>
                    </linearGradient>"""
            }

            val logoScv = """<div align="center"><svg width="${width}" height="${height}">
                $defs
                $rays
            <rect x="${width / 2.86}" y="${height / 2.86}" width="${width / 3.33}" height="${height / 3.33}" fill="${BLACK.hex}"></rect>
            <text x="${width / 2.66}" y="${height / 1.87}" fill="${WHITE.hex}" font-size="215" font-family="JetBrains Mono">${title}</text>
            <rect x="${width / 2.63}" y="${height / 1.69}" width="${width / 8.62}" height="${height / 52.63}" fill="${WHITE.hex}"></rect>
            </svg></div>"""

            var fileContent = """
            <html>
            <head></head>
            <body>
            ${logoScv}
            </body>
            </html>
            """

            file.writeText(fileContent)
        }

        private fun getBezierApexList(startPoint: Point, endPoint: Point): List<String> {

            val x01 = startPoint.x
            val y01 = startPoint.y
            val x02 = endPoint.x
            val y02 = endPoint.y

            val isLeftPoint = x01 < x02

            val r1 = startPoint.r
            val r2 = endPoint.r

            val z = sqrt((x01 - x02).pow(2) + (y01 - y02).pow(2))
            val cosA = (r2 - r1) / z
            val x = sqrt(z.pow(2) - (r2 - r1).pow(2))

            val xB11 = x01 - cosA * r2
            val yB11 = y01 + (1 - cosA.pow(2)) * r2
            val xB12 = x02 - (r1 / r2 * cosA * r2)
            val yB12 = y02 + (1 - cosA.pow(2)) * r1

            val xB21 = x01 + cosA * r2
            val yB21 = y01 - (1 - cosA.pow(2)) * r2
            val xB22 = x02 + (r1 / r2 * cosA * r2)
            val yB22 = y02 - (1 - cosA.pow(2)) * r1

            val k1 = (yB12 - yB11) / (xB12 - xB11)
            val b1 = yB12 - k1 * xB12

            val p1 = calcXY(xB11, yB11, k1, b1, r1, !isLeftPoint)
            val p2 = calcXY(xB12, yB12, k1, b1, r2, isLeftPoint)

            val k2 = (yB22 - yB21) / (xB22 - xB21)
            val b2 = yB22 - k2 * xB22

            val p3 = calcXY(xB21, yB21, k2, b2, r1, !isLeftPoint)
            val p4 = calcXY(xB22, yB22, k2, b2, r2, isLeftPoint)

            return listOf(p1, p2, p3, p4)
        }

        private fun calcXY(x: Double, y: Double, k: Double, b: Double, r: Double, isleftPoint: Boolean): String {
            val A = 1 + k.pow(2)
            val B = 2 * (-x + k * (b - y))
            val C = x.pow(2) + (b - y).pow(2) - r.pow(2)
            val D = B.pow(2) - 4 * A * C


            val xC = if(isleftPoint) {(-B + sqrt(D)) / (2 * A)} else{(-B - sqrt(D)) / (2 * A)}

            val yC = k * xC + b

            return "$xC,$yC"
        }
    }
}