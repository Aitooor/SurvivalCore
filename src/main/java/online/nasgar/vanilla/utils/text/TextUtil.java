package online.nasgar.vanilla.utils.text;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class TextUtil {

    public String centerText(String text) {
        char[] chars = text.toCharArray(); // Get a list of all characters in text

        boolean bold = false;
        double length = 0;

        ChatColor chatColor;
        for (int i = 0; i < chars.length; i++) { // Loop through all characters
            // Check if the character is a ColorCode..
            if (chars[i] == '&' && chars.length != (i + 1) && (chatColor = ChatColor.getByChar(chars[i + 1])) != null) {
                if (chatColor != ChatColor.UNDERLINE && chatColor != ChatColor.ITALIC && chatColor != ChatColor.STRIKETHROUGH
                        && chatColor != ChatColor.MAGIC) {
                    bold = (chars[i + 1] == 'l'); // Setting bold to true or false, depending on if the ChatColor is
                    // Bold.
                    length--; // Removing one from the length, of the string, because we don't wanna count
                    // color codes.
                    i += bold ? 1 : 0;
                }
            } else {
                // If the character is not a color code:
                length++; // Adding a space
                length += (bold ? (chars[i] != ' ' ? 0.1555555555555556 : 0) : 0); // Adding 0.156 spaces if the
                // character is bold.
            }
        }

        double spaces = (69 - length) / 2; // Getting the spaces to add by (max line length - length) / 2

        // Adding the spaces
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < spaces; i++) {
            builder.append(' ');
        }

        String copy = builder.toString();
        builder.append(text).append(copy);

        return builder.toString();
    }
}
