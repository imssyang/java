package effective.g6enum_annotation;

import java.util.EnumSet;
import java.util.Set;

/**
 * Item 36: Use EnumSet instead of bit fields
 */
public class Eg36EnumSet {

    // Bit field enumeration constants - OBSOLETE!
    public static class Text {
        public static final int STYLE_BOLD = 1 << 0; // 1
        public static final int STYLE_ITALIC = 1 << 1; // 2
        public static final int STYLE_UNDERLINE = 1 << 2; // 4
        public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8
        // Parameter is bitwise OR of zero or more STYLE_ constants
        public void applyStyles(int styles) { }

        public static void main(String[] args) {
            Text text = new Text();
            text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
        }
    }

    // EnumSet - a modern replacement for bit fields
    public static class Text2 {
        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
        // Any Set could be passed in, but EnumSet is clearly best
        public void applyStyles(Set<Style> styles) { }

        public static void main(String[] args) {
            Text2 text = new Text2();
            text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
        }
    }

}
