import builder.LogoBuilder
import logo.Style
import logoRender.LogoRender

fun main() {
    val logo = LogoBuilder.getLogo("""JB""", Style.PY_CHARM)
    LogoRender.render(logo, 1000, 1000)
    print(logo.toString())
}