package logoRender

import Logo
import logo.Color.*
import logo.RayPoint
import java.io.File
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class LogoRender {
    companion object {
        fun render(logo: Logo, width: Int, height: Int) {
            val title = logo.square.title
            val rnd = Random.nextInt(9999)
            val file = File("C:\\Projects\\Liam\\logos\\${title}_logo_${rnd}.html")

            var rays = ""
            var defs = ""
            logo.rays.forEachIndexed { key, ray ->

                rays += """
                    <g id="Layer_${key}">
                    <path d="
                    M${ray.p1} 
                    L${ray.p2}
                    L${ray.p3}
                    L${ray.p4}
                    z" 
                    fill="url(#Gradient_${key})"/>
                </g>
                """
                defs += """<linearGradient id="Gradient222234567891011121314_${key}">
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
    }
}