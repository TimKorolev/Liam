package builder

import Logo
import logo.Square
import logo.Style

class LogoBuilder {

    companion object {
        fun getLogo(title: String, style: Style): Logo {
            return Logo(RayListBuilder.getRays(), style, Square(title))
        }
    }
}