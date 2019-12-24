public class format {
	//Returns a rounded String of a double value
	public String roundToTwoDecimals(double value)
	{
		value = value * 10;
		value = Math.round(value);
		value = value / 10;
		return String.valueOf(value);
	}
}