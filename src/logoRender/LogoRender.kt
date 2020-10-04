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
                    C${bezierApexList[1].x},${bezierApexList[1].y + ray.endPoint.r} ${bezierApexList[1].x},${bezierApexList[1].y - ray.endPoint.r} ${ray.endPoint.x},${ray.endPoint.y - ray.endPoint.r} 
                    L${ray.startPoint.x},${ray.startPoint.y - ray.endPoint.r}
                    C${bezierApexList[0].x},${bezierApexList[0].y - ray.startPoint.r} ${bezierApexList[0].x},${bezierApexList[0].y + ray.startPoint.r} ${ray.startPoint.x},${ray.startPoint.y + ray.startPoint.r} 
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

        private fun getBezierApexList(startPoint: Point, endPoint: Point): List<BezierApex> {
            val x1 = startPoint.x
            val y1 = startPoint.y
            val x2 = endPoint.x
            val y2 = endPoint.y
            val r1 = startPoint.r
            val r2 = endPoint.r
            val k = (y1 - y2) / (x1 - x2)

            val b = y1 - k * x1

            val D1 = 4 * (x1 - k * (b - y1)).pow(2) - 4 * (1 + k) * (x1.pow(2) - r1.pow(2) + (y1 - b).pow(2))
            val xR1 = (x1 - k * (b - y1)) + sqrt(D1) / (1 + k)
            val yR1 = k * xR1 + b
            val D2 = 4 * (x2 - k * (b - y2)).pow(2) - 4 * (1 + k) * (x2.pow(2) - r2.pow(2) + (y2 - b).pow(2))
            val xR2 = (x2 - k * (b - y2)) - sqrt(D2) / (1 + k)
            val yR2 = k * xR2 + b

            return listOf(BezierApex(xR1, yR1), BezierApex(xR2, yR2))
        }
    }
}