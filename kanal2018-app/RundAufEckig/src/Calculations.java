public class Calculations {

    public static String roundToTwoDecimals(double value)
    {
        value = value * 10;
        value = Math.round(value);
        value = value / 10;
        return String.valueOf(value);
    }
}
